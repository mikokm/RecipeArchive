package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		int id = DatabaseHelper.parseId(rd.getParameter("drinkId"));

		if (id == 0) {
			rd.setPageError("Invalid query string, drinkId is invalid!");
			return rd.getErrorPage();
		}

		Drink drink = null;
		try {
			drink = DrinkDAO.getDrinkWithId(new QueryRunner(rd.getDataSource()), id);
		} catch (SQLException e) {
			rd.setPageError("Failed to query the database for the drink: " + e.getMessage());
			return rd.getErrorPage();
		}

		if (drink == null) {
			rd.setPageError("Failed to find a drink from the database with a drinkId: " + id);
			return rd.getErrorPage();
		}

		rd.setAttribute("drink", drink);
		return "drink.jsp";
	}

	@Override
	public boolean secure() {
		return true;
	}

}
