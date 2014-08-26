package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class CreateDrinkAction implements Action {
	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");

		Drink drink = ModifyPageHelper.parseFormParameters(request);
		drink.setOwnerId(user.getId());
	
		QueryRunner runner = new QueryRunner(ds);
		
		if(drink.getId() == 0) {
			boolean ret = DrinkDAO.addDrinkToDatabase(runner, drink);
			System.out.println("add drink: " + ret + " drink id: " + drink.getId());
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
