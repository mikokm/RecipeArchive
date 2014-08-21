package fi.miko.EeppinenDrinkkiarkisto.Logic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class Controller {
	private final DataSource datasource;
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private static final String jspPath = "/WEB-INF/";

	private Map<String, Action> actions = new HashMap<String, Action>();;

	public Controller(DataSource ds, HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
		this.datasource = ds;

		initializeActions();
	}

	private Action getAction(HttpServletRequest request) {
		return actions.get(request.getMethod() + request.getPathInfo());
	}

	private void initializeActions() {
		actions.put("POST/login", new LoginAction());
		actions.put("GET/logout", new LogoutAction());
		actions.put("GET/drinks", new NullAction("drinks.jsp", true));
		actions.put("GET/landing", new NullAction("landing.jsp", true));
		actions.put("GET/", new NullAction("index.jsp", false));
	}

	private void dispatch(String url) {
		try {
			request.getRequestDispatcher(jspPath + url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processRequest() throws Exception {
		response.setContentType("text/html;charset=UTF-8");

		// Redirect the logged in users to the landing page.
		if(request.getPathInfo().equals("/") && isLoggedIn()) {
			dispatch("landing.jsp");
			return;
		}

		Action action = getAction(request);
		if(action == null) {
			return;
		}

		// Require logged in status for the secure sites.
		if(!action.secure() || (action.secure() && isLoggedIn())) {
			String url = action.execute(datasource, request, response);
			dispatch(url);
		}
	}

	private boolean isLoggedIn() {
		return request.getSession().getAttribute("loggedIn") != null;
	}
}
