package fi.miko.EeppinenDrinkkiarkisto.Logic;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkAction implements Action {
	private final String page;
	
	public DrinkAction(String page) {
		this.page = page;
	}
	
	@Override
	public String execute(RequestData rd) throws Exception {
		Drink drink = DrinkHelper.getDrink(new DrinkDAO(rd.getDataSource()), rd);

		if (drink == null) {
			return rd.getErrorPage();
		}

		rd.setAttribute("drink", drink);
		return page;
	}

	@Override
	public boolean secure() {
		return true;
	}

}
