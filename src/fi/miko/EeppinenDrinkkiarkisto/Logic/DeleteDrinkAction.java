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
		String previous = request.getParameter("previousPage");

		if (drink == null || user == null || drink.getOwnerId() != user.getId()) {
			return "landing.jsp";
		}

		drink.deleteDrink(ds.getConnection());
		request.getSession().removeAttribute("activeDrink");

		if (previous != null) {
			response.sendRedirect(response.encodeRedirectURL(previous));
		}

		return "landing.jsp";
	}

	@Override
	public boolean secure() {
		return true;
	}

}
