package fi.miko.EeppinenDrinkkiarkisto.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class DrinkListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DrinkListServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void loadDrinks(PrintWriter out) {
		InitialContext ctx;
		DataSource ds;
		
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup( "java:/comp/env/jdbc/postgres" );
		} catch (NamingException e) {
			e.printStackTrace();
			return;
		}
		
		if(ctx == null || ds == null) {
			return;
		}
		
		try {
			Connection con = ds.getConnection();
			String sql = "SELECT name, description, picture_url FROM Drinks";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			out.println("<table>");
			out.println("<tr>\n");
			out.println("<th>Name</th>");
			out.println("<th>Description</th>");
			out.println("<th>picture url</th>");
			out.println("</tr>");

			while (rs.next()) {
				String name = rs.getString("name");
				String description = rs.getString("description");
				String url = rs.getString("picture_url");

				out.println("<tr>");
				out.println("<td>" + name + "</td>");
				out.println("<td>" + description + "</td>");
				out.println("<td>" + url + "</td>");
				out.println("</tr>");
			}

			out.println("</table>");

		} catch (Exception e) {
			e.printStackTrace();
			out.write(e.toString());
		}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		try {
			out.println("<html><head>"); 
			out.println("<title>List of drinks</title>");
			out.println("<style> table,th,td { border:1px solid black; } </style>");
			out.println("</head><body>");
			out.println("<h1>List of the drinks in the database:</h1>");
			loadDrinks(out);
			out.println("</body>");
			out.println("</html>");
		} finally {            
			out.close();
		}
	}
}
