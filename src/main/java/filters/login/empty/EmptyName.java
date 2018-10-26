package filters.login.empty;

import org.apache.log4j.Logger;


import javax.servlet.*;
import java.io.IOException;

public class EmptyName implements Filter {
    private static final String EMPTY_NAME_ERROR = "empty username";

    private static final Logger logger = Logger.getLogger(EmptyName.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("init of filter \"Empty Name\"");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String userName =  req.getParameter("username");

        logger.debug("The length userName = \"" + userName + "\": " + userName.length());
        if (userName.length() == 0) {
            logger.debug("Save in request attribute \"userNameError\"");
            req.setAttribute("userNameError", EMPTY_NAME_ERROR);

            logger.debug("Output error of empty empty in view: " + EMPTY_NAME_ERROR);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            logger.debug("Save in request attribute \"userName\"");
            req.setAttribute("userName", userName);

            logger.debug("Change to empty password filter");
            filterChain.doFilter(req, resp);
        }
    }
}
