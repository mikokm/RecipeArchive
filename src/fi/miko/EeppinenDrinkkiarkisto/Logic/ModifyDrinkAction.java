package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class ModifyDrinkAction implements Action {
	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		String previous = request.getParameter("previousPage");
		int drinkId = DrinkDAO.parseId(request.getParameter("drinkId"));
		
		if(drinkId == 0) {
			request.setAttribute("pageError", "modifyDrink failed because of an invalid drinkId!");
			return "landing.jsp";
		}
		
		QueryRunner runner = new QueryRunner(ds);
		Drink drink = DrinkDAO.getDrinkWithId(runner, drinkId);

		if(drink == null || drink.getOwnerId() != user.getId()) {
			response.sendRedirect(response.encodeRedirectURL(previous));
			return null;
		}
		
		if(request.getParameter("deleteButton") != null) {
			DrinkDAO.deleteDrink(runner, drink.getId());
			response.sendRedirect(response.encodeRedirectURL("drinks"));
		}

		if (request.getParameter("modifyButton") != null) {
			request.setAttribute("drink", drink);
			return "modifyDrink.jsp";
		}

		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}
}
