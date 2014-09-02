package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import fi.miko.EeppinenDrinkkiarkisto.Database.UserDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class LoginAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		String username = rd.getParameter("username");
		String password = rd.getParameter("password");

		// First page view.
		if (username == null && password == null) {
			return rd.getIndexPage();
		}

		rd.setAttribute("username", username);

		User user = null;
		UserDAO dao = new UserDAO(rd.getDataSource());

		try {
			user = dao.getUser(username);
		} catch (SQLException e) {
			rd.setError("Failed to query user from the database: " + e.getMessage());
			return rd.getIndexPage();
		}

		if (user == null || !checkPassword(user, password)) {
			rd.setAttribute("pageError", "Invalid username or password!");
			return rd.getIndexPage();
		}

		dao.updateLastLogin(user);
		rd.getSession().setAttribute("user", user);

		return rd.getDefaultPage();
	}

	private boolean checkPassword(User user, String password) {
		String hash = UserDAO.getPasswordHash(password, user.getSalt());
		return user.getPassword().equals(hash);
	}

	@Override
	public boolean secure() {
		return false;
	}
}
