package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.UserDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class ModifyPageHelper {
	public static final int MAX_INGREDIENTS = 10;

	public static Drink parseDrinkFormParameters(HttpServletRequest request) {
		int id = DatabaseHelper.parseId(request.getParameter("drinkId"));

		Drink drink = new Drink(id, request.getParameter("name"));
		drink.setDescription(request.getParameter("description"));
		drink.setImageUrl(request.getParameter("imageUrl"));

		List<String> ingredients = new ArrayList<String>();
		for (int i = 0; i < MAX_INGREDIENTS; ++i) {
			String ingredient = request.getParameter("ingredients" + i);

			if (ingredient == null || ingredient.isEmpty()) {
				// The ingredient is null or empty, skip.
				continue;
			}

			ingredients.add(ingredient);
		}

		drink.setIngredients(ingredients);

		return drink;
	}

	public static User parseUserFormParameters(HttpServletRequest request) {
		int id = DatabaseHelper.parseId(request.getParameter("userId"));
		
		User user = new User(id, request.getParameter("username"));
		user.setAdmin(request.getParameter("admin") != null);
		
		if(id == 0 || request.getParameter("changePassword") != null) {
			String salt = UserDAO.generateSalt();
			String password = request.getParameter("password");
			
			if(password == null) {
				password = ""; 
			}

			String hash = UserDAO.getPasswordHash(password, salt);
			
			user.setSalt(salt);
			user.setPassword(hash);
		}
		
		
		return user;
	}
}
