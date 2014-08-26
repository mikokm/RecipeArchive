package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class ModifyPageHelper {
	public static int parseId(String idString) {
		int id;

		if(idString == null || idString.isEmpty()) {
			return 0;
		}

		try {
			id = Integer.parseInt(idString);
		} catch(NumberFormatException e) {
			id = 0;
		}

		return id;
	}
	
	public static Drink parseFormParameters(HttpServletRequest request) {
		int id = ModifyPageHelper.parseId(request.getParameter("drinkId"));
		
		Drink drink = new Drink(id, request.getParameter("name"));
		drink.setDescription(request.getParameter("description"));
		drink.setImageUrl(request.getParameter("imageUrl"));
		
		String [] ingredients = request.getParameter("ingredients").split("\\|");
		drink.setIngredients(Arrays.asList(ingredients));
		
		return drink;
	}
}
