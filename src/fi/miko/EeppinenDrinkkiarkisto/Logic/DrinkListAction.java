package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkListAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		try {
			List<Drink> drinks = DrinkDAO.getDrinkList(new QueryRunner(rd.getDataSource()));
			rd.setAttribute("drinks", drinks);
			return "drinklist.jsp";
		} catch (SQLException e) {
			rd.setPageError("Failed to query the database!");
			return rd.getErrorPage();
		}
	}

	@Override
	public boolean secure() {
		return true;
	}

}
