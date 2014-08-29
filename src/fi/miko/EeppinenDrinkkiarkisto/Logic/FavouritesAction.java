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
		
		if(rd.getRequest().getMethod() == "GET") {
			return showFavourites(rd, runner, user);
		}
		
		int drinkId = DatabaseHelper.parseId(rd.getParameter("drinkId"));
		
		if(drinkId == 0) {
			rd.setPageError("Invalid query string!");
			return rd.getErrorPage();
		}
		
		if(rd.getParameter("addFavourite") != null) {
			try {
				FavouritesDAO.addFavouriteForUserId(runner, user.getId(), drinkId);
			} catch (SQLException e) {
				
			}
		}
		
		if(rd.getParameter("deleteFavourite") != null) {
			return null;
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