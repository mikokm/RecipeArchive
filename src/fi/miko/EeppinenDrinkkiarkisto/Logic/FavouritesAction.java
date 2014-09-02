package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;
import java.util.List;

import fi.miko.EeppinenDrinkkiarkisto.Database.DatabaseHelper;
import fi.miko.EeppinenDrinkkiarkisto.Database.FavouritesDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class FavouritesAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		User user = (User) rd.getSession().getAttribute("user");
		FavouritesDAO dao = new FavouritesDAO(rd.getDataSource());

		// If this action was reached with GET method, show the favourites list.
		if (rd.getRequest().getMethod() == "GET") {
			try {
				List<Drink> drinks = dao.getFavouritesWithUserId(user.getId());
				rd.setAttribute("drinks", drinks);
				return "favourites.jsp";
			} catch (SQLException e) {
				rd.setError("Favourites query failed: " + e.getMessage());
				return rd.getErrorPage();
			}
		}

		int addId = DatabaseHelper.parseId(rd.getParameter("addFavourite"));
		int removeId = DatabaseHelper.parseId(rd.getParameter("removeFavourite"));
		
		if (addId == 0 && removeId == 0) {
			rd.setError("FavouritesAction received invalid drinkId!");
			return rd.getErrorPage();
		}
		
		String error = "";
		try {
			if (addId != 0) {
				error = "Adding favourite";
				dao.addFavourite(user.getId(), addId);
				rd.redirect("drinklist");
			} else if (removeId != 0) {
				error = "Removing favourite";
				dao.removeFavourite(user.getId(), removeId);
				rd.redirect("favourites");
			} else {
				rd.setError("Unknown error in FavouritesAction!");
				return rd.getErrorPage();
			}
		} catch (SQLException e) {
			rd.setError(error + " query failed: " + e.getMessage());
			return rd.getErrorPage();
		}

		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}
}