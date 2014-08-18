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
import fi.miko.EeppinenDrinkkiarkisto.Model.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PostgresDatabase database = null;
	private String databaseError;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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

		if(database == null) {
			request.setAttribute("pageError", "Cannot connect to the database!\n Technical stuff:\n" + databaseError);
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();
		Object loggedIn = session.getAttribute("loggedIn");

		if(loggedIn != null || handleLogin(username, password, session)) {
			request.getRequestDispatcher("/WEB-INF/landing.jsp").forward(request, response);
		} else {
			request.setAttribute("username", username);
			
			if(username != null && password != null) {
				request.setAttribute("pageError", "Invalid username or password!");
			}
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}
	
	private boolean isValidLogin(String username, String password) {
		return !(username == null || password == null || username.isEmpty() || password.isEmpty());
	}

	private boolean handleLogin(String username, String password, HttpSession session) {
		System.out.println("Login request: " + username + "/" + password);
		
		if(!isValidLogin(username, password)) {
			return false;
		}
		
		User user = User.getUser(database, username, password);
		if(user == null) {
			return false;
		}
		
		if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
			session.setAttribute("loggedIn", true);
			session.setAttribute("username", user.getUsername());
			session.setAttribute("admin", user.isAdmin());
			return true;
		}
		
		return false;
	}
}
