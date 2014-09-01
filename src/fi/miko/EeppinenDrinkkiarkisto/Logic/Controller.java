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
		actions.add("GET/landing", new RedirectAction("landing.jsp", true));

		actions.add("POST/login", new LoginAction());
		actions.add("GET/logout", new LogoutAction());

		actions.add("GET/drink", new DrinkAction());
		actions.add("GET/drinklist", new DrinkListAction());

		actions.add("GET/favourites", new FavouritesAction());
		actions.add("POST/favourites", new FavouritesAction());

		actions.add("GET/createDrink", new RedirectAction("createDrink.jsp", true));
		actions.add("POST/modifyDrink", new ModifyDrinkAction());
		actions.add("POST/updateDrink", new UpdateDrinkAction());

		// TODO:
		actions.add("GET/addDrinkList", new RedirectAction("addDrinkList.jsp", true));
		actions.add("GET/admin", new RedirectAction("admin.jsp", true));
		actions.add("GET/createUser", new RedirectAction("createUser.jsp", true));
	}

	public Controller(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void processRequest() throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		RequestData rd = new RequestData(request, response, dataSource, "/WEB-INF/");
		rd.setIndexPage("index.jsp");
		rd.setErrorPage("error.jsp");
		rd.setDefaultPage("landing.jsp");

		System.out.println(request.getPathInfo() + " / " + request.getRequestURI());

		// Redirect the logged in users to the landing page.
		if (request.getPathInfo().equals("/") && isLoggedIn()) {
			rd.dispatch(rd.getDefaultPage());
			return;
		}

		Action action = actions.get(request);
		if (action == null) {
			rd.setPageError("Cannot process the request: " + request.getPathInfo());
			rd.dispatch(rd.getErrorPage());
			return;
		}

		// Require logged in status for the secure sites.
		if (action.secure() && !isLoggedIn()) {
			rd.dispatch(rd.getIndexPage());
			return;
		}

		String url = action.execute(rd);
		if (url != null) {
			rd.dispatch(url);
		}
	}

	private boolean isLoggedIn() {
		return request.getSession().getAttribute("user") != null;
	}
}
