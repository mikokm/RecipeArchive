package fi.miko.EeppinenDrinkkiarkisto.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

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

	public static User getUser(Connection conn, String name, String password) {
		User user = null;

		if(conn == null) {
			System.out.println("Connection is null!");
			return null;
		}

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT username, password, admin FROM Users " +
					"WHERE username = ? AND password = ?");

			st.setString(1, name);
			st.setString(2, password);

			rs = st.executeQuery();

			if(rs.next()) {
				user = new User(rs.getString("username"), rs.getString("password"), rs.getBoolean("admin"));
				System.out.println("Found user: " + user.getUsername() + "/" + user.getPassword());
			}

			DbUtils.closeQuietly(conn, st, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, st, rs);
		}

		return user;
	}
}
