package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.UserDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class ModifyUserAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		User user = (User) rd.getSession().getAttribute("user");
		
		if(!user.getAdmin()) {
			rd.setError("You are not an admin!");
			return rd.getErrorPage();
		}

		int userId = DatabaseHelper.parseId(rd.getParameter("userId"));
		
		if(userId == 0) {
			rd.setError("Invalid userId parameter!");
			return rd.getErrorPage();
		}
		
		QueryRunner runner = new QueryRunner(rd.getDataSource());
		
		if(rd.getParameter("removeButton") != null) {
			try {
				UserDAO.removeUser(runner, userId);
			} catch(SQLException e) {
				rd.setError("Failed to remove user. Database error: " + e.getMessage());
				return rd.getErrorPage();
			}
		}
		
		
		
		if(rd.getParameter("modifyButton") != null) {
			rd.setAttribute("user", user);
		}
		
		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}

}
