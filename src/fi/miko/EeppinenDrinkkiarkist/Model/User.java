package fi.miko.EeppinenDrinkkiarkist.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import fi.miko.EeppinenDrinkkiarkisto.Database.PostgresDatabase;

public class User {
	private	String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	private User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public static User getUserByName(PostgresDatabase database, String name) {
		User user = null;

		try {
			Connection con = database.connect();
			PreparedStatement st = con.prepareStatement("SELECT name, password FROM users WHERE name = ?");
			st.setString(1, name);

			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				user = new User(rs.getString("name"), rs.getString("password"));
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
