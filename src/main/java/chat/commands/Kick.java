package chat.commands;

import chat.Command;
import chat.parsers.KickParserDTO;
import chat.parsers.ParserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import dto.input.KickUserData;
import dto.output.DynamicData;
import datalayer.data.message.Message;
import datalayer.data.message.TypeMessage;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class Kick implements Command {
    private static final String KICK_MESSAGE = "User \"%s\" is kick user \"%s\"";

    private static ParserDTO parserDTO = new KickParserDTO();

    private static final Logger logger = Logger.getLogger(Kick.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        // get dto
        KickUserData kickUserData = (KickUserData) parserDTO.parse(req);
        logger.debug("Get \"MassageData\" DTO from \"SendMessageParserDTO\": " + kickUserData.toString());
        User user = (User) req.getSession().getAttribute(USERNAME);
        logger.debug("Get user from current session: " + user.toString());


        //exec command
        userDAO.removeUserByName(kickUserData.getKickUserName());
        logger.debug("User remove from DAO: " + kickUserData.toString());

        Message message = new Message(String.format(KICK_MESSAGE, user.getName(), kickUserData.getKickUserName()),
                TypeMessage.KICK, user.getName(), new Date());
        messageDAO.addMessage(message);
        logger.debug("Add message in DAO: " + message.toString());


        // set response
        String[] users = userDAO.getAllUserNamesExceptIn(user.getName());
        logger.debug("Get all users for \"" + user.getName() + "\": " + Arrays.stream(users).reduce("", (log, currentUser) -> log + currentUser));

        Message[] messages = messageDAO.getAllMessages();
        logger.debug("Get messages from DAO: " + Arrays.stream(messages).map(Message::toString).reduce("", (log, currentMessage) -> log + currentMessage));

        DynamicData dynamicData = new DynamicData(messages, users);
        logger.debug("Create \"DynamicData\" DTO: " + dynamicData.toString());

        String dynamicDataJson = mapper.writeValueAsString(dynamicData);
        logger.debug("Messages data in JSON format for output: " + dynamicDataJson);
        resp.getWriter().write(dynamicDataJson);
    }
}
