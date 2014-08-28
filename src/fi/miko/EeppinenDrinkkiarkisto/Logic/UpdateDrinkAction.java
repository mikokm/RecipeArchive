package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class UpdateDrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		Drink drink = ModifyPageHelper.parseFormParameters(rd.getRequest());

		if (drink.getId() == 0) {
			rd.setPageError("Cannot updateDrink with drinkId 0!");
			return rd.getErrorPage();
		}

		try {
			QueryRunner runner = new QueryRunner(rd.getDataSource());
			DrinkDAO.updateDrink(runner, drink);
		} catch (SQLException e) {
			// Unique constraint.
			if (DatabaseHelper.constraintViolation(e)) {
				rd.setPageError("Drink name is already in use!");
				rd.setAttribute("drink", drink);
				return "modifyDrink.jsp";
			} else {
				rd.setPageError("Error while updating drink in database:" + e.getMessage());
				return rd.getErrorPage();
			}
		}

		rd.redirect("drink?drinkId=" + drink.getId());
		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}

}
