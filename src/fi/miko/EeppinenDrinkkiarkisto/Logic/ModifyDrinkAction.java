package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class ModifyDrinkAction implements Action {
	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int drinkId = DrinkDAO.parseId(request.getParameter("drinkId"));

		if (drinkId == 0) {
			request.setAttribute("pageError", "modifyDrink: Invalid drinkId!");
			return "landing.jsp";
		}

		QueryRunner runner = new QueryRunner(ds);
		Drink drink = null;
		
		try {
			drink = DrinkDAO.getDrinkWithId(runner, drinkId);
		} catch (SQLException e) {
			request.setAttribute("pageError", "modifyDrink: Failed to access the database!");
			return "landing.jsp";
		}

		if (drink == null) {
			request.setAttribute("pageError", "modifyDrink: Cannot find the drink from database!");
			return "landing.jsp";
		}

		if (request.getParameter("deleteButton") != null) {
			DrinkDAO.deleteDrink(runner, drink.getId());
			response.sendRedirect(response.encodeRedirectURL("drinklist"));
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
