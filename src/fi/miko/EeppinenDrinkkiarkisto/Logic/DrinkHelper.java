package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.io.IOException;
import java.sql.SQLException;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkHelper {
	public static Drink getDrink(DrinkDAO dao, RequestData rd) {
		int id = DatabaseHelper.parseId(rd.getParameter("drinkId"));

		if (id == 0) {
			rd.setError("Invalid query string, drinkId is invalid!");
			return null;
		}

		Drink drink = null;
		try {
			drink = dao.getDrink(id);
		} catch (SQLException e) {
			rd.setError("Failed to query the database for the drink: " + e.getMessage());
			return null;
		}

		if (drink == null) {
			rd.setError("Failed to find a drink from the database with a drinkId: " + id);
			return null;
		}

		return drink;
	}

	public static String insertOrUpdateDrink(DrinkDAO dao, Drink drink, RequestData rd) throws IOException {
		int id = drink.getId();

		try {
			// If the drink id is 0, the drink is not in the database.
			if (id == 0) {
				dao.addDrink(drink);
			} else {
				dao.updateDrink(drink);
			}
		} catch (SQLException e) {
			// Unique constraint.
			if (DatabaseHelper.constraintViolation(e)) {
				rd.setError("The drink name is already in use!");
				rd.setAttribute("drink", drink);
				return (id == 0 ? "createDrink.jsp" : "modifyDrink.jsp");
			} else {
				rd.setError("Error while updating drink in database: " + e.getMessage());
				return rd.getErrorPage();
			}
		}

		if (drink.getId() == 0) {
			rd.setError("Unknown error while updating drink: drinkId is 0.");
			return rd.getErrorPage();
		}

		// Return to the drink page of the drink that got added or modified.
		rd.redirect("drink?drinkId=" + drink.getId());
		return null;
	}
}
