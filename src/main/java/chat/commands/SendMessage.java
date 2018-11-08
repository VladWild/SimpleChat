package chat.commands;

import chat.Command;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import datalayer.data.massage.Message;
import datalayer.data.massage.TypeMessage;
import dto.chat.MassageData;
import org.apache.log4j.Logger;
import parsers.ParserDTO;
import parsers.chat.SendMessageParserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

public class SendMessage implements Command {
    private static ParserDTO parserDTO = new SendMessageParserDTO();

    private static final Logger logger = Logger.getLogger(SendMessage.class);

    private MassageData getMessageDataDTO(HttpServletRequest req) throws UnsupportedEncodingException {
        MassageData massageData = (MassageData) parserDTO.parse(req);
        logger.debug("Get \"MassageData\" DTO from \"parsers.chat.SendMessageParserDTO\": " + massageData.toString());
        return massageData;
    }

    private void executeSendMessage(MassageData massageData, User user, MessageDAO messageDAO){
        Message message = new Message(massageData.getText(),
                TypeMessage.MASSAGE, user.getName(), new Date());
        messageDAO.addMessage(message);
        logger.debug("Add message in DAO: " + message.toString());
    }

    private void setResponseSendMessage(HttpServletResponse resp, MessageDAO messageDAO) throws IOException {
        Message[] messages = messageDAO.getAllMessages();
        logger.debug("Get messages from DAO: " + Arrays.stream(messages).map(Message::toString).reduce("", (log, currentMessage) -> log + currentMessage));

        sendData(resp, messages);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO) throws IOException {
        MassageData massageData = getMessageDataDTO(req);
        User user = getUserFromSession(req);

        executeSendMessage(massageData, user, messageDAO);

        setResponseSendMessage(resp, messageDAO);
    }
}


