package fi.miko.EeppinenDrinkkiarkisto.Servlets;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import fi.miko.EeppinenDrinkkiarkisto.Logic.Controller;

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DataSource datasource = null;

	public ControllerServlet() {
		super();
		initializeDatasource();
	}

	private void initializeDatasource() {
		try {
			datasource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/postgres");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Controller controller = new Controller(datasource, request, response);
		try {
			controller.processRequest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
