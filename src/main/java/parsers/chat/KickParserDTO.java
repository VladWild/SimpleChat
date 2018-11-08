package parsers.chat;

import dto.chat.KickUserData;
import parsers.ParserDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class KickParserDTO implements ParserDTO {
    private static final String KICK_USERNAME = "kickUserName";

    @Override
    public Object parse(HttpServletRequest req) throws UnsupportedEncodingException {
        KickUserData kickUserData = new KickUserData();

        String kickUserName = req.getParameter(KICK_USERNAME);
        kickUserData.setKickUserName(kickUserName);

        return kickUserData;
    }
}
