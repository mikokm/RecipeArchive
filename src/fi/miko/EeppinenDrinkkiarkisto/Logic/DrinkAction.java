package fi.miko.EeppinenDrinkkiarkisto.Logic;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		Drink drink = DrinkHelper.getDrink(rd);
		
		if(drink != null) {
			rd.setAttribute("drink", drink);
			return "drink.jsp";
		} else {
			return rd.getErrorPage();
		}
	}

	@Override
	public boolean secure() {
		return true;
	}

}
