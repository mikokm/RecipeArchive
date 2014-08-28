package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class ModifyDrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		int drinkId = DrinkDAO.parseId(rd.getParameter("drinkId"));

		if (drinkId == 0) {
			rd.setPageError("Invalid query parameters!");
			return rd.getErrorPage();
		}

		QueryRunner runner = new QueryRunner(rd.getDataSource());
		Drink drink = null;

		try {
			drink = DrinkDAO.getDrinkWithId(runner, drinkId);
		} catch (SQLException e) {
			rd.setAttribute("pageError", "modifyDrink: Failed to access the database!");
			return rd.getErrorPage();
		}

		if (drink == null) {
			rd.setAttribute("pageError", "modifyDrink: Cannot find the drink from database!");
			return rd.getErrorPage();
		}

		if (rd.getParameter("deleteButton") != null) {
			DrinkDAO.deleteDrink(runner, drink.getId());
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
