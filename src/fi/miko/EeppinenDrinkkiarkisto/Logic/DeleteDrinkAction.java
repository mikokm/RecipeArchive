package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class DeleteDrinkAction implements Action {

	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Drink drink = (Drink) request.getSession().getAttribute("activeDrink");
		User user = (User) request.getSession().getAttribute("user");
		String previous = (String) request.getAttribute("previousPage");
		
		if(drink == null || user == null) {
			return previous;
		}
		
		if(drink.getOwnerId() != user.getId()) {
			return previous;
		}
		
		drink.deleteDrink(ds.getConnection());
		request.getSession().removeAttribute("activeDrink");
		
		return previous;
	}

	@Override
	public boolean secure() {
		return true;
	}

}
