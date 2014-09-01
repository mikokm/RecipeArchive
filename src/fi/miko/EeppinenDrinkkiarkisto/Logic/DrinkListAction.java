package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class DrinkListAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		User user = (User) rd.getSession().getAttribute("user");

		try {
			List<Drink> drinks = DrinkDAO.getDrinkList(new QueryRunner(rd.getDataSource()), user.getId());
			rd.setAttribute("drinks", drinks);
			return "drinklist.jsp";
		} catch (SQLException e) {
			rd.setError("Failed to query the database: " + e.getMessage());
			return rd.getErrorPage();
		}
	}

	@Override
	public boolean secure() {
		return true;
	}

}
