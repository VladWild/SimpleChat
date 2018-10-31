package chat.parser;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

public class SimpleParserData implements ParserData {
    private static final String DELIMITER_PARAMETERS = "&";
    private static final String DELIMITER_KEY_VALUE = "=";

    private static final Logger logger = Logger.getLogger(SimpleParserData.class);

    @Override
    public void parse(HttpServletRequest req) throws UnsupportedEncodingException {
        String data = req.getQueryString();

        String decodingData = URLDecoder.decode(data, "UTF-8");
        logger.debug("Get data from request: " + decodingData);

        Arrays.stream(decodingData.split(DELIMITER_PARAMETERS)).forEach(keyValue -> {
            String values[] = keyValue.split(DELIMITER_KEY_VALUE);

            logger.debug("Set in request attributes: key =\"" + values[0] + "\"; values=\"" + values[1] + "\"");
            req.setAttribute(values[0], values[1]);
        });
    }
}
