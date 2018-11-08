package parsers.chat;

import dto.chat.MassageData;
import parsers.ParserDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class SendMessageParserDTO implements ParserDTO {
    private static final String MESSAGE = "text";

    @Override
    public Object parse(HttpServletRequest req) throws UnsupportedEncodingException {
        MassageData massageData = new MassageData();

        String text = req.getParameter(MESSAGE);
        massageData.setText(text);

        return massageData;
    }
}


