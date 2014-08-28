package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class ModifyDrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		int drinkId = DatabaseHelper.parseId(rd.getParameter("drinkId"));

		if (drinkId == 0) {
			rd.setPageError("Invalid query parameters, invalid drinkId!");
			return rd.getErrorPage();
		}

		QueryRunner runner = new QueryRunner(rd.getDataSource());
		Drink drink = null;

		try {
			drink = DrinkDAO.getDrinkWithId(runner, drinkId);
		} catch (SQLException e) {
			rd.setPageError("Failed to access the database: " + e.getMessage());
			return rd.getErrorPage();
		}

		if (drink == null) {
			rd.setPageError("Cannot find the drink from database!");
			return rd.getErrorPage();
		}

		if (rd.getParameter("deleteButton") != null) {
			try {
				DrinkDAO.deleteDrink(runner, drink.getId());
			} catch (SQLException e) {
				// Not much to do here.
			}

			rd.redirect("drinklist");
			return null;
		}

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
