package filters.chat;

import datalayer.DAOFactory;
import datalayer.StorageType;
import datalayer.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*@WebFilter(filterName = "userIsInSession", servletNames = "chat")*/
public class UserIsInSession implements Filter {
    private static final String USERNAME = "username";

    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance(StorageType.JVM);
        userDAO = daoFactory.getUserDAO();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String user = (String) request.getSession().getAttribute(USERNAME);

        if (user == null) {
            filterChain.doFilter(req, resp);
        } else {
            response.sendRedirect("/index.jsp");
        }
    }
}
