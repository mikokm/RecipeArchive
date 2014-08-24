package fi.miko.EeppinenDrinkkiarkisto.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

public class User {
	private int id;
	private String username;
	private String password;
	private String lastLogin;
	private boolean admin;

	private User(int id, String username, String password, boolean admin) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.admin = admin;
	}

	public static User createUser(Connection conn) {
		return null;
	}

	public static User getUser(Connection conn, String name, String password) {
		User user = null;

		if (conn == null) {
			System.out.println("Connection is null!");
			return null;
		}

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT user_id, username, password, admin, last_login FROM Users " + "WHERE username = ? AND password = ?");

			st.setString(1, name);
			st.setString(2, password);

			rs = st.executeQuery();

			if (rs.next()) {
				user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getBoolean("admin"));

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

	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	private void setId(int id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
