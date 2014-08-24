package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinksAction implements Action {
	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Drink> drinks = Drink.getDrinkList(ds.getConnection());
		request.setAttribute("drinks", drinks);

		return "drinks.jsp";
	}

	@Override
	public boolean secure() {
		return true;
	}

}
