package fi.miko.EeppinenDrinkkiarkisto.Logic;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class Controller {
	private final HttpServletRequest request;
	private final HttpServletResponse response;

	private static final Actions actions = new Actions();
	private static final DataSource dataSource = getDataSource();
	private static final String JSP_PATH = "/WEB-INF/";

	private static DataSource getDataSource() {
		DataSource ds = null;
		try {
			ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/postgres");
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return ds;
	}

	static {
		actions.add("GET/", new RedirectAction("index.jsp", false));
		actions.add("POST/login", new LoginAction());
		actions.add("GET/landing", new RedirectAction("landing.jsp", true));
		actions.add("GET/logout", new LogoutAction());

		actions.add("GET/createDrink", new RedirectAction("modifyDrink.jsp", true));
		actions.add("POST/createDrink", new CreateDrinkAction());

		actions.add("GET/drink", new DrinkAction());
		actions.add("GET/drinklist", new DrinkListAction());

		actions.add("POST/modifyDrink", new ModifyDrinkAction());
		actions.add("POST/updateDrink", new UpdateDrinkAction());

		// TODO:
		actions.add("GET/addDrinkList", new RedirectAction("addDrinkList.jsp", true));
		actions.add("GET/admin", new RedirectAction("admin.jsp", true));
		actions.add("GET/createUser", new RedirectAction("createUser.jsp", true));
		actions.add("GET/favourites", new RedirectAction("favourites.jsp", true));
	}

	public Controller(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	private void dispatch(String url) {
		try {
			request.getRequestDispatcher(JSP_PATH + url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processRequest() throws Exception {
		response.setContentType("text/html;charset=UTF-8");

		System.out.println(request.getPathInfo() + " / " + request.getRequestURI());

		// Redirect the logged in users to the landing page.
		if (request.getPathInfo().equals("/") && isLoggedIn()) {
			dispatch("landing.jsp");
			return;
		}

		Action action = actions.get(request);
		if (action == null) {
			return;
		}

		// Require logged in status for the secure sites.
		if (!action.secure() || isLoggedIn()) {
			String url = action.execute(dataSource, request, response);

			if (url == null || url.isEmpty()) {
				return;
			}

			dispatch(url);
		} else {
			dispatch("index.jsp");
		}
	}

	private boolean isLoggedIn() {
		return request.getSession().getAttribute("user") != null;
	}
}
