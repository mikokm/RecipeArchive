package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class LoginAction implements Action {
	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// First page view.
		if (username == null && password == null) {
			return "index.jsp";
		}

		request.setAttribute("username", username);

		User user = User.getUser(ds.getConnection(), username, password);

		if (user == null) {
			request.setAttribute("pageError", "Invalid username or password!");
			return "index.jsp";
		}

		HttpSession session = request.getSession();
		session.setAttribute("loggedIn", true);
		session.setAttribute("user", user);

		return "landing.jsp";
	}

	@Override
	public boolean secure() {
		return false;
	}
}
