package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class CreateDrinkAction implements Action {
	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Drink drink = Drink.createDrink();
		drink.setName("testijuoma");
		drink.setDescription("moi");
		drink.setUrl("testurl");
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Jallu", 20);
		map.put("Kossu", 20);
		drink.setIngredients(map);
		
		Enumeration e = request.getSession().getAttributeNames();
		while(e.hasMoreElements()) {
			System.out.println("e:" + e.nextElement());
		}
		
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			System.out.println("User is null");
			return "createDrink.jsp";
		}
		
		boolean ret = Drink.addDrinkToDatabase(ds.getConnection(), drink, user.getId());
		System.out.println("add drink: " + ret + " drink id: " + drink.getId());
		
		ret = drink.saveDrink(ds.getConnection());
		System.out.println("save drink: " + ret);
		
		return "createDrink.jsp";
	}

	@Override
	public boolean secure() {
		return true;
	}

}
