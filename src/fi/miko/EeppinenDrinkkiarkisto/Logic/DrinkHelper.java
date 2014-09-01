package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkHelper {
	public static Drink getDrink(RequestData rd) {
		int id = DatabaseHelper.parseId(rd.getParameter("drinkId"));

		if (id == 0) {
			rd.setPageError("Invalid query string, drinkId is invalid!");
			return null;
		}

		Drink drink = null;
		try {
			drink = DrinkDAO.getDrinkWithId(new QueryRunner(rd.getDataSource()), id);
		} catch (SQLException e) {
			rd.setPageError("Failed to query the database for the drink: " + e.getMessage());
			return null;
		}

		if (drink == null) {
			rd.setPageError("Failed to find a drink from the database with a drinkId: " + id);
			return null;
		}

		return drink;
	}

	public static String insertOrUpdateDrink(Drink drink, RequestData rd) throws IOException {
		int id = drink.getId();

		QueryRunner runner = new QueryRunner(rd.getDataSource());

		try {
			if (id == 0) {
				DrinkDAO.addDrinkToDatabase(runner, drink);
			} else {
				DrinkDAO.updateDrink(runner, drink);
			}
		} catch (SQLException e) {
			// Unique constraint.
			if (DatabaseHelper.constraintViolation(e)) {
				rd.setPageError("The drink name is already in use!");
				rd.setAttribute("drink", drink);
				return (id == 0 ? "createDrink.jsp" : "modifyDrink.jsp");
			} else {
				rd.setPageError("Error while updating drink in database: " + e.getMessage());
				return rd.getErrorPage();
			}
		}

		rd.redirect("drink?drinkId=" + drink.getId());
		return null;
	}
}
