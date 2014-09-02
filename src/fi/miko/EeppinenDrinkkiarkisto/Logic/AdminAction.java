package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;
import java.util.List;

import fi.miko.EeppinenDrinkkiarkisto.Database.UserDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class AdminAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		List<User> users;
		
		try {
			users = UserDAO.getUserList(rd.getQueryRunner());
		} catch(SQLException e) {
			rd.setError("Failed to retrieve user list, database error: " + e.getMessage());
			return rd.getErrorPage();
		}
		
		rd.setAttribute("users", users);
		return "admin.jsp";
	}

	@Override
	public boolean secure() {
		return true;
	}

}
