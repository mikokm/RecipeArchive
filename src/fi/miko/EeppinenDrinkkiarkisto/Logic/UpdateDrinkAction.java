package fi.miko.EeppinenDrinkkiarkisto.Logic;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class UpdateDrinkAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		User user = (User) rd.getSession().getAttribute("user");
		Drink drink = ModifyPageHelper.parseFormParameters(rd.getRequest());

		// Set the drink owner.
		if(drink.getId() == 0) {
			drink.setOwnerId(user.getId());
		}
		
		if(drink.getOwnerId() != user.getId()) {
			rd.setPageError("You don't have permission to modify this drink!");
			return rd.getErrorPage();
		}
		
		return DrinkHelper.insertOrUpdateDrink(drink, rd);
	}

	@Override
	public boolean secure() {
		return true;
	}

}
