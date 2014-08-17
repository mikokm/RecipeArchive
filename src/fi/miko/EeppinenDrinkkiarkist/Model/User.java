package fi.miko.EeppinenDrinkkiarkist.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import fi.miko.EeppinenDrinkkiarkisto.Database.PostgresDatabase;

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

	public static User getUser(PostgresDatabase database, String name, String password) {
		User user = null;

		try {
			Connection con = database.connect();
			PreparedStatement st = con.prepareStatement("SELECT name, password, admin FROM users WHERE name = ? AND password = ?");
			st.setString(1, name);
			st.setString(2, password);

			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				user = new User(rs.getString("name"), rs.getString("password"), rs.getBoolean("admin"));
				System.out.println("Found user: " + user.getUsername() + "/" + user.getPassword());
			}
			
			st.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}
}
