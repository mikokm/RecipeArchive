package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class UpdateDrinkAction implements Action {
	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Drink drink = ModifyPageHelper.parseFormParameters(request);

		if (drink.getId() != 0) {
			QueryRunner runner = new QueryRunner(ds);
			DrinkDAO.updateDrink(runner, drink);
		} else {
			System.out.println("drink id not what it's supposed to be: " + drink.getId());
		}

		response.sendRedirect(response.encodeRedirectURL("drink?drinkId=" + drink.getId()));
		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}

}
