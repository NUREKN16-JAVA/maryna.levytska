package ua.nure.kn16.levitskaya.usermanagement.web;

import ua.nure.kn16.levitskaya.usermanagement.User;
import ua.nure.kn16.levitskaya.usermanagement.db.DAOFactory;
import ua.nure.kn16.levitskaya.usermanagement.db.DAOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends EditServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ADD_PAGE = "/add.jsp";

	@Override
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(ADD_PAGE).forward(req, resp);
		return;
	}

	@Override
	protected void processUser(User user) throws DAOException {
		DAOFactory.getInstance().getUserDAO().create(user);
	}
}
