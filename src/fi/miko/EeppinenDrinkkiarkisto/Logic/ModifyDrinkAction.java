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
		
		// Check if the request was to show modification page.
		if (rd.getParameter("modifyButton") != null) {
			Drink drink = DrinkHelper.getDrink(dao, rd);
			
			if(drink == null) {
				return rd.getErrorPage();
			}
			
			rd.setAttribute("drink", drink);
			return "modifyDrink.jsp";
		}
		
		Drink drink = ModifyPageHelper.parseDrinkFormParameters(rd.getRequest());
		
		int ownerId = 0;
		
		// If the drinkId is 0, it is not yet in the database and doens't have an owner.
		if (drink.getId() == 0) {
			ownerId = user.getId();
			drink.setOwnerId(ownerId);
		} else {
			try {
				// Fetch the drink owner from the database.
				ownerId = dao.getDrinkOwnerId(drink.getId());
			} catch (SQLException e) {
				rd.setError("Failed to get drink owner!");
				return rd.getErrorPage();
			}
		}

		// Check if the user can modify this drink.
		if (ownerId != user.getId() && !user.getAdmin()) {
			rd.setError("You don't have permission to modify this drink!");
			return rd.getErrorPage();
		}
		
		// Check if the request was to delete the drink.
		if (rd.getParameter("deleteButton") != null) {
			// Check if the drinkId is invalid.
			if(drink.getId() == 0) {
				rd.setError("Cannot delete a drink with id 0!");
				return rd.getErrorPage();
			}
			
			try {
				dao.deleteDrink(drink.getId());
			} catch (SQLException e) {
				rd.setError("Failed to delete the drink from database: " + e.getMessage());
				return rd.getErrorPage();
			}

			rd.redirect("drinklist");
			return null;
		}
		
		// If the control reaches here, the request was to update or insert the drink.
		return DrinkHelper.insertOrUpdateDrink(dao, drink, rd);
	}

	@Override
	public boolean secure() {
		return true;
	}

}
