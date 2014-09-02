package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class UpdateDrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		User user = (User) rd.getSession().getAttribute("user");
		Drink drink = ModifyPageHelper.parseDrinkFormParameters(rd.getRequest());

		int ownerId = 0;

		DrinkDAO dao = new DrinkDAO(rd.getDataSource());
		
		if (drink.getId() == 0) {
			// Set the owner of the new drink to the current user.
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

		// Check if the user can change this drink.
		if (ownerId != user.getId() && !user.getAdmin()) {
			rd.setError("You don't have permission to modify this drink!");
			return rd.getErrorPage();
		}

		return DrinkHelper.insertOrUpdateDrink(dao, drink, rd);
	}

	@Override
	public boolean secure() {
		return true;
	}

}
