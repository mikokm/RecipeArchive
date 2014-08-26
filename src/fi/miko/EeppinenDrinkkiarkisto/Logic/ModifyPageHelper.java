package fi.miko.EeppinenDrinkkiarkisto.Logic;

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
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String url = request.getParameter("imageUrl");
		String [] ingredients = request.getParameter("ingredients").split("\\|");
		
		Drink drink = new Drink(id, name);
		//drink.setIngredients();
		//System.out.println("ingredients:" + Arrays.toString(ingredients));
		
		return drink;
	}
}
