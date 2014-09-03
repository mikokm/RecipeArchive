package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.UserDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class UpdateUserAction implements Action {

	@Override
	public String execute(RequestData rd) throws Exception {
		User current = (User) rd.getSession().getAttribute("user");

		if (!current.getAdmin()) {
			rd.setError("You are not an admin!");
			return rd.getErrorPage();
		}

		int userId = DatabaseHelper.parseId(rd.getParameter("userId"));
		
		User user = new User(userId, rd.getParameter("username"));
		user.setAdmin(rd.getParameter("admin") != null);
		

		// Generate salt and hash the password + salt.
		String salt = UserDAO.generateSalt();
		String password = rd.getParameter("password");
			
		if(password == null) {
			password = ""; 
		}

		String hash = UserDAO.getPasswordHash(password, salt);
		user.setSalt(salt);
		user.setPassword(hash);
		
		UserDAO dao = new UserDAO(rd.getDataSource());

		try {
			// If the drink id is 0, the drink is not in the database.
			if (user.getId() == 0) {
				dao.addUser(user);
				
				if(user.getId() == 0) {
					rd.setError("Failed to add the user, unknown error!");
					return rd.getErrorPage();
				}
			} else {
				dao.updateUser(user);
			}
			
			// Change password only if the user was just created or
			// if the change password checkbox is checked.
			if(userId == 0 || rd.getParameter("changePassword") != null) {
				dao.updateUserPassword(user);
			}
		} catch (SQLException e) {
			// Unique constraint.
			if (DatabaseHelper.constraintViolation(e)) {
				rd.setError("The user name is already in use!");
				rd.setAttribute("usr", user);
				return (user.getId() == 0 ? "createUser.jsp" : "modifyUser.jsp");
			} else {
				rd.setError("Error while updating user in database: " + e.getMessage());
				return rd.getErrorPage();
			}
		}
		
		rd.redirect("admin");
		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}

}
