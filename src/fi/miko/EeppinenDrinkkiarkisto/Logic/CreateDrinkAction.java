package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class CreateDrinkAction implements Action {
	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("GET")) {
			return "modifyDrink.jsp";
		}

		if(!request.getMethod().equals("POST")) {
			return "modifyDrink.jsp";
		}

		Drink drink = Drink.createDrink();
		drink.setName(request.getParameter("name"));
		drink.setDescription(request.getParameter("description"));
		drink.setUrl(request.getParameter("image"));

		String [] ingredients = request.getParameter("ingredients").split("\\|");
		drink.setIngredients(Arrays.asList(ingredients));
		System.out.println("ingredients:" + Arrays.toString(ingredients));

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "landing.jsp";
		}

		boolean ret = Drink.addDrinkToDatabase(ds.getConnection(), drink, user.getId());
		System.out.println("add drink: " + ret + " drink id: " + drink.getId());

		ret = drink.saveDrink(ds.getConnection());
		System.out.println("save drink: " + ret);

		return "drinks.jsp";
	}

	@Override
	public boolean secure() {
		return true;
	}
}
