package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.FavouritesDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class FavouritesAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		User user = (User) rd.getSession().getAttribute("user");
		QueryRunner runner = new QueryRunner(rd.getDataSource());

		if (rd.getRequest().getMethod() == "GET") {
			return showFavourites(rd, runner, user);
		}

		int drinkId = DatabaseHelper.parseId(rd.getParameter("drinkId"));

		if (drinkId == 0) {
			rd.setPageError("Invalid query string!");
			return rd.getErrorPage();
		}

		String error = "";
		try {
			if (rd.getParameter("addFavourite") != null) {
				error = "adding favourite";
				FavouritesDAO.addFavourite(runner, user.getId(), drinkId);
				rd.redirect("drinklist");
			} else if (rd.getParameter("removeFavourite") != null) {
				error = "removing favourite";
				FavouritesDAO.removeFavourite(runner, user.getId(), drinkId);
				rd.redirect("favourites");
			} else {
				rd.setPageError("Invalid query parameters!");
				return rd.getErrorPage();
			}
		} catch (SQLException e) {
			rd.setPageError("Error while " + error + ":\n" + e.getMessage());
			return rd.getErrorPage();
		}

		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}

	private String showFavourites(RequestData rd, QueryRunner runner, User user) {
		try {
			List<Drink> drinks = FavouritesDAO.getFavouritesWithUserId(runner, user.getId());
			rd.setAttribute("drinks", drinks);
			return "favourites.jsp";
		} catch (SQLException e) {
			rd.setPageError("Failed to query the database: " + e.getMessage());
			return rd.getErrorPage();
		}
	}
}