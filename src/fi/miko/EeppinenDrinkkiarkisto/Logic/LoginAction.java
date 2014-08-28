package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

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
		
		try {
			user = UserDAO.getUserWithUsername(new QueryRunner(rd.getDataSource()), username);
		} catch(SQLException e) {
			rd.setPageError("Failed to query user from the database: " + e.getMessage());
			return rd.getIndexPage();
		}

		if (user == null || !checkPassword(user, password)) {
			rd.setAttribute("pageError", "Invalid username or password!");
			return rd.getIndexPage();
		}

		rd.getSession().setAttribute("user", user);
		return rd.getDefaultPage();
	}

	private boolean checkPassword(User user, String password) {
		String hash = UserDAO.hashPassword(password, user.getSalt());
		return user.getPassword().equals(hash);
	}

	@Override
	public boolean secure() {
		return false;
	}
}
