package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.UserDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class ModifyUserAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		User user = (User) rd.getSession().getAttribute("user");

		if (!user.getAdmin()) {
			rd.setError("You are not an admin!");
			return rd.getErrorPage();
		}

		int removeId = DatabaseHelper.parseId(rd.getParameter("removeButton"));
		int modifyId = DatabaseHelper.parseId(rd.getParameter("modifyButton"));

		if (modifyId == 0 && removeId == 0) {
			rd.setError("ModifyUserAction received incorrect parameters!");
			return rd.getErrorPage();
		}

		UserDAO dao = new UserDAO(rd.getDataSource());

		if (removeId != 0) {
			try {
				dao.removeUser(removeId);
				rd.redirect("admin");
				return null;
			} catch (SQLException e) {
				rd.setError("Failed to remove user. Database error: " + e.getMessage());
				return rd.getErrorPage();
			}
		}

		if (modifyId != 0) {
			user = dao.getUser(modifyId);

			if (user == null || user.getId() != modifyId) {
				rd.setError("Invalid userId for the modify page!");
				return rd.getErrorPage();
			}

			rd.setAttribute("usr", user);
			return "modifyUser.jsp";
		}

		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}

}
