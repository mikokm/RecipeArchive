package fi.miko.EeppinenDrinkkiarkisto.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private	String username;
	private String password;
	private boolean admin;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return admin;
	}
	
	private User(String username, String password, boolean admin) {
		this.username = username;
		this.password = password;
		this.admin = admin;
	}

	public static User getUser(Connection con, String name, String password) {
		User user = null;

		if(con == null) {
			System.out.println("Connection is null!");
			return null;
		}
		
		try {
			PreparedStatement st = con.prepareStatement("SELECT name, password, admin FROM users WHERE name = ? AND password = ?");
			st.setString(1, name);
			st.setString(2, password);

			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				user = new User(rs.getString("name"), rs.getString("password"), rs.getBoolean("admin"));
				System.out.println("Found user: " + user.getUsername() + "/" + user.getPassword());
			}
			
			try { st.close(); st = null; } catch (SQLException e) { ; }
			try { rs.close(); rs = null; } catch (SQLException e) { ; }
			try { con.close(); con = null; } catch (SQLException e) { ; }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if(con != null) con.close(); } catch (SQLException e) { ; }
		}

		return user;
	}
}
