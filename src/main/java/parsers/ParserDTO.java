package parsers;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface ParserDTO {
    Object parse(HttpServletRequest req) throws UnsupportedEncodingException;
}


