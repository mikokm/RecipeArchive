package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class DrinkAction implements Action {
	private final static String DRINK_PAGE = "drink.jsp";

	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parameter = request.getParameter("id");
		int id;

		try {
			id = Integer.parseInt(parameter);
		} catch (NumberFormatException e) {
			id = 0;
		}

		if (id == 0) {
			request.setAttribute("pageError", "Invalid query string.");
			return DRINK_PAGE;
		}

		Drink drink = Drink.getDrinkWithId(ds.getConnection(), id);
		if (drink == null) {
			request.setAttribute("pageError", "Failed to find a drink from the database with an id: " + id);
			return DRINK_PAGE;
		}
		
		User user = (User) request.getSession().getAttribute("user");
		System.out.println(drink.getOwnerId() + " & " + user.getId());

		request.setAttribute("drink", drink);
		request.setAttribute("previousPage", "drinks");
		
		request.getSession().setAttribute("activeDrink", drink);
		
		return DRINK_PAGE;
	}

	@Override
	public boolean secure() {
		return true;
	}

}
