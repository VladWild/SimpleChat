package chat.commands;

import chat.Command;
import chat.parsers.ParserDTO;
import chat.parsers.SendMessageParserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import datalayer.data.message.Message;
import datalayer.data.message.TypeMessage;
import dto.input.MassageData;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class SendMessage implements Command {
    private static ParserDTO parserDTO = new SendMessageParserDTO();

    private static final Logger logger = Logger.getLogger(SendMessage.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        MassageData massageData = (MassageData) parserDTO.parse(req);
        logger.debug("Get \"MassageData\" DTO from \"SendMessageParserDTO\": " + massageData.toString());
        User user = (User) req.getSession().getAttribute(USERNAME);
        logger.debug("Get user from current session: " + user.toString());

        Message message = new Message(massageData.getText(),
                TypeMessage.MASSAGE, user.getName(), new Date());
        messageDAO.addMessage(message);
        logger.debug("Add message in DAO: " + message.toString());

        Message[] messages = messageDAO.getAllMessages();
        logger.debug("Get messages from DAO: " + Arrays.stream(messages).map(Message::toString).reduce("", (log, currentMessage) -> log + currentMessage));

        String messagesJson = mapper.writeValueAsString(messages);
        logger.debug("Messages data in JSON format for output: " + messagesJson);
        resp.getWriter().write(messagesJson);
    }
}


