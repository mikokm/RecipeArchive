package fi.miko.EeppinenDrinkkiarkisto.Logic;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Database.DrinkDAO;
import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class UpdateDrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		Drink drink = ModifyPageHelper.parseFormParameters(rd.getRequest());

		if (drink.getId() == 0) {
			System.out.println("drink id not what it's supposed to be: " + drink.getId());
		}

		QueryRunner runner = new QueryRunner(rd.getDataSource());
		DrinkDAO.updateDrink(runner, drink);

		rd.redirect("drink?drinkId=" + drink.getId());
		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}

}
