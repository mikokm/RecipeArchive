package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.Arrays;

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

		String name = request.getParameter("name");
		Drink drink = new Drink(user.getId(), name);
		
		String description = request.getParameter("description");
		drink.setDescription(description);
		String url = request.getParameter("image");
		drink.setImageUrl(url);

		String [] ingredients = request.getParameter("ingredients").split("\\|");
		drink.setIngredients(Arrays.asList(ingredients));
		System.out.println("ingredients:" + Arrays.toString(ingredients));
		
		QueryRunner runner = new QueryRunner(ds);
		boolean ret = DrinkDAO.addDrinkToDatabase(runner, drink);
		System.out.println("add drink: " + ret + " drink id: " + drink.getId());

		ret = DrinkDAO.saveDrink(runner, drink);
		System.out.println("save drink: " + ret);

		response.sendRedirect(response.encodeRedirectURL("drinks"));
		
		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}
}
