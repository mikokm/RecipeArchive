package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class ModifyDrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		User user = (User) rd.getSession().getAttribute("user");
		
		DrinkDAO dao = new DrinkDAO(rd.getDataSource());
		Drink drink = DrinkHelper.getDrink(dao, rd);

		// The drink was not found.
		if (drink == null) {
			return rd.getErrorPage();
		}

		if (drink.getOwnerId() != user.getId() && !user.getAdmin()) {
			rd.setError("You don't have permission to modify this drink!");
			return rd.getErrorPage();
		}

		// Try to delete the drink and return to the drinklist-page.
		if (rd.getParameter("deleteButton") != null) {
			try {
				dao.deleteDrink(drink.getId());
			} catch (SQLException e) {
				rd.setError("Failed to delete the drink from database: " + e.getMessage());
				return rd.getErrorPage();
			}

			rd.redirect("drinklist");
			return null;
		}

		// Show the modify page with the drink details.
		if (rd.getParameter("modifyButton") != null) {
			rd.setAttribute("drink", drink);
			return "modifyDrink.jsp";
		}

		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}
}
