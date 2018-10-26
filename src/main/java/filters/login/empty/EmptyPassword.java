package filters.login.empty;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EmptyPassword implements Filter {
    private static final String EMPTY_PASSWORD_ERROR = "empty password";

    private static final Logger logger = Logger.getLogger(EmptyPassword.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("init of filter \"Empty Password\"");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String password =  req.getParameter("password");

        logger.debug("The length password = \"" + password + "\": " + password.length());
        if (password.length() == 0) {
            logger.debug("Save in request attribute error of empty password");
            req.setAttribute("passwordError", EMPTY_PASSWORD_ERROR);

            logger.debug("Output error of empty empty in view: " + EMPTY_PASSWORD_ERROR);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            logger.debug("Change to empty password filter");
            filterChain.doFilter(req, resp);
        }
    }
}
