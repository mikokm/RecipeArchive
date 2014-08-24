package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class ModifyDrinkAction implements Action {
	private ACTION_TYPE action;
	
	public enum ACTION_TYPE {
		 DELETE, MODIFY
	}
	
	public ModifyDrinkAction(ACTION_TYPE action) {
		this.action = action;
	}
	
	@Override
	public String execute(DataSource ds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Drink drink = (Drink) request.getSession().getAttribute("activeDrink");
		User user = (User) request.getSession().getAttribute("user");
		String previous = request.getParameter("previousPage");

		if (drink == null || user == null || drink.getOwnerId() != user.getId()) {
			return "landing.jsp";
		}
		
		if(action == ACTION_TYPE.DELETE) {
			drink.deleteDrink(ds.getConnection());
			request.getSession().removeAttribute("activeDrink");
			
			if (previous != null) {
				response.sendRedirect(response.encodeRedirectURL(previous));
			}
		}

		if(action == ACTION_TYPE.MODIFY) {
			request.setAttribute("drink", drink);
			return "modifyDrink.jsp";
		}
		
		return null;
	}

	@Override
	public boolean secure() {
		return true;
	}
}
