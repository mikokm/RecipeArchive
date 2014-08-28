package fi.miko.EeppinenDrinkkiarkisto.Logic;

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

		User user = User.getUser(rd.getDataSource().getConnection(), username, password);

		if (user == null) {
			rd.setAttribute("pageError", "Invalid username or password!");
			return rd.getIndexPage();
		}

		rd.getSession().setAttribute("user", user);
		return rd.getDefaultPage();
	}

	@Override
	public boolean secure() {
		return false;
	}
}
