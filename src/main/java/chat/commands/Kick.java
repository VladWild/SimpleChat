package chat.commands;

import chat.Command;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import datalayer.data.massage.Message;
import datalayer.data.massage.TypeMessage;
import dto.chat.DynamicData;
import dto.chat.KickUserData;
import org.apache.log4j.Logger;
import parsers.ParserDTO;
import parsers.chat.KickParserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

public class Kick implements Command {
    private static final String KICK_MESSAGE = "User \"%s\" is kick user \"%s\"";

    private static ParserDTO parserDTO = new KickParserDTO();

    private static final Logger logger = Logger.getLogger(Kick.class);

    private KickUserData getKickUserDataDTO(HttpServletRequest req) throws UnsupportedEncodingException {
        KickUserData kickUserData = (KickUserData) parserDTO.parse(req);
        logger.debug("Get \"MassageData\" DTO from \"parsers.chat.SendMessageParserDTO\": " + kickUserData.toString());
        return kickUserData;
    }

    private void executeKick(KickUserData kickUserData, User user, UserDAO userDAO, MessageDAO messageDAO){
        userDAO.removeUserByName(kickUserData.getKickUserName());
        logger.debug("User remove from DAO: " + kickUserData.toString());

        Message message = new Message(String.format(KICK_MESSAGE, user.getName(), kickUserData.getKickUserName()),
                TypeMessage.KICK, user.getName(), new Date());
        messageDAO.addMessage(message);
        logger.debug("Add message in DAO: " + message.toString());
    }

    private void setResponseKick(HttpServletResponse resp, User user, UserDAO userDAO, MessageDAO messageDAO) throws IOException {
        String[] users = userDAO.getAllUserNamesExceptIn(user.getName());
        logger.debug("Get all users for \"" + user.getName() + "\": " + Arrays.stream(users).reduce("", (log, currentUser) -> log + currentUser));

        Message[] messages = messageDAO.getAllMessages();
        logger.debug("Get messages from DAO: " + Arrays.stream(messages).map(Message::toString).reduce("", (log, currentMessage) -> log + currentMessage));

        DynamicData dynamicData = new DynamicData(messages, users);
        logger.debug("Create \"DynamicData\" DTO: " + dynamicData.toString());

        sendData(resp, dynamicData);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO) throws IOException {
        KickUserData kickUserData = getKickUserDataDTO(req);
        User user = getUserFromSession(req);

        executeKick(kickUserData, user, userDAO, messageDAO);

        setResponseKick(resp, user, userDAO, messageDAO);
    }
}
