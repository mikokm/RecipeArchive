package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class ModifyPageHelper {
	public static final int MAX_INGREDIENTS = 10;

	public static int parseId(String idString) {
		int id;

		if (idString == null || idString.isEmpty()) {
			return 0;
		}

		try {
			id = Integer.parseInt(idString);
		} catch (NumberFormatException e) {
			id = 0;
		}

		return id;
	}

	public static Drink parseFormParameters(HttpServletRequest request) {
		int id = ModifyPageHelper.parseId(request.getParameter("drinkId"));

		Drink drink = new Drink(id, request.getParameter("name"));
		drink.setDescription(request.getParameter("description"));
		drink.setImageUrl(request.getParameter("imageUrl"));

		List<String> ingredients = new ArrayList<String>();
		for (int i = 0; i < MAX_INGREDIENTS; ++i) {
			String ingredient = request.getParameter("ingredients" + i);

			if (ingredient == null || ingredient.isEmpty()) {
				// Ingredient null or empty, skip.
				continue;
			}

			ingredients.add(ingredient);
		}

		drink.setIngredients(ingredients);

		return drink;
	}
}
