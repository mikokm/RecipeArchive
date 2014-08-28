package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.servlet.http.HttpSession;

public class LogoutAction implements Action {
	@Override
	public String execute(RequestData rd) throws Exception {
		HttpSession session = rd.getSession();
		session.removeAttribute("user");
		rd.getRequest().removeAttribute("username");

		return rd.getIndexPage();
	}

	@Override
	public boolean secure() {
		return false;
	}
}
