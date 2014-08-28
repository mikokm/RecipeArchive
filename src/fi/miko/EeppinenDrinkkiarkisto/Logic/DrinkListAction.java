package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkListAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		List<Drink> drinks = DrinkDAO.getDrinkList(new QueryRunner(rd.getDataSource()));
		rd.setAttribute("drinks", drinks);
		return "drinklist.jsp";
	}

	@Override
	public boolean secure() {
		return true;
	}

}
