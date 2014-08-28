package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.FavouritesDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class FavouritesAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		User user = (User) rd.getSession().getAttribute("user");

		try {
			List<Drink> drinks = FavouritesDAO.getFavouritesWithUserId(new QueryRunner(rd.getDataSource()), user.getId());
			rd.setAttribute("drinks", drinks);
			return "favourites.jsp";
		} catch (SQLException e) {
			rd.setPageError("Failed to query the database: " + e.getMessage());
			return rd.getErrorPage();
		}
	}

	@Override
	public boolean secure() {
		return true;
	}

}