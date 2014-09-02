package fi.miko.EeppinenDrinkkiarkisto.Logic;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		Drink drink = DrinkHelper.getDrink(new DrinkDAO(rd.getDataSource()), rd);

		if (drink == null) {
			return rd.getErrorPage();
		}

		rd.setAttribute("drink", drink);
		return "drink.jsp";
	}

	@Override
	public boolean secure() {
		return true;
	}

}
