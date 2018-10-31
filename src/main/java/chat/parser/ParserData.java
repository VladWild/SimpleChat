package chat.parser;

import chat.CommandType;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface ParserData {
    void parse(HttpServletRequest req) throws UnsupportedEncodingException;
}
