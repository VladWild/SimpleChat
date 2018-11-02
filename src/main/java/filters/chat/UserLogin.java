package filters.chat;

import datalayer.DAOFactory;
import datalayer.StorageType;
import datalayer.UserDAO;
import datalayer.data.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "userLogin", servletNames = "chat")
public class UserLogin implements Filter {
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

        Object user = request.getSession().getAttribute(USERNAME);

        if (Objects.isNull(user)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            String name = ((User) user).getName();

            if (userDAO.isLoginByName(name)){
                filterChain.doFilter(req, resp);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
