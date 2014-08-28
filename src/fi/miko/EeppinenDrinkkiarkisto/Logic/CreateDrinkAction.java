package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class CreateDrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		User user = (User) rd.getSession().getAttribute("user");

		Drink drink = ModifyPageHelper.parseFormParameters(rd.getRequest());
		drink.setOwnerId(user.getId());

		QueryRunner runner = new QueryRunner(rd.getDataSource());

		if (drink.getId() != 0) {
			rd.setPageError("Invalid drinkId for the createDrink: " + drink.getId());
			return rd.getErrorPage();
		}

		try {
			DrinkDAO.addDrinkToDatabase(runner, drink);
		} catch (SQLException e) {
			// Unique constraint.
			if (DatabaseHelper.constraintViolation(e)) {
				rd.setPageError("Drink name is already in use!");
				rd.setAttribute("drink", drink);
				return "createDrink.jsp";
			} else {
				rd.setPageError("Error while adding drink to database:" + e.getMessage());
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
