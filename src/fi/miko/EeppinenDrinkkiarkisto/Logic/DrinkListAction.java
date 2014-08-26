package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkListAction implements Action {
	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Drink> drinks = DrinkDAO.getDrinkList(new QueryRunner(ds));
		request.setAttribute("drinks", drinks);
		return "drinklist.jsp";
	}

	@Override
	public boolean secure() {
		return true;
	}

}
