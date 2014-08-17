package fi.miko.EeppinenDrinkkiarkisto.Servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.miko.EeppinenDrinkkiarkisto.Database.PostgresDatabase;

/**
 * Servlet implementation class LoginServlet
 */
public class DrinksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PostgresDatabase database = null;
	private String databaseError;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DrinksServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			Properties props = new Properties();
			InputStream is = config.getServletContext().getResourceAsStream("/WEB-INF/database.xml");
			props.loadFromXML(is);
			database = new PostgresDatabase(props);
		} catch (Exception e) {
			e.printStackTrace();
			databaseError = e.toString();
		}	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		if(session.getAttribute("loggedIn") == null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		if(database == null) {
			request.setAttribute("pageError", "Cannot connect to the database!\n Technical stuff:\n" + databaseError);
		}

		request.getRequestDispatcher("/WEB-INF/drinks.jsp").forward(request, response);
	}
}
