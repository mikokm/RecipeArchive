package fi.miko.EeppinenDrinkkiarkisto.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.joda.time.Instant;

public class Drink {
	private int id;
	private String name;
	private String description;
	private String url;
	private Instant date;
	private String owner;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getUrl() {
		return url;
	}
	public Instant getDate() {
		return date;
	}
	public String getOwner() {
		return owner;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	private Drink(int id, String name, String description, String url, Instant date, String owner) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.url = url;
		this.date = date;
		this.owner = owner;
	}
	
	/*
	public static Drink getUser(Connection conn, String name, String password) {
		Drink user = null;

		if (conn == null) {
			System.out.println("Connection is null!");
			return null;
		}

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT user_id, username, password, admin FROM Users " + "WHERE username = ? AND password = ?");

			st.setString(1, name);
			st.setString(2, password);

			rs = st.executeQuery();

			if (rs.next()) {
				user = new Drink(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getBoolean("admin"));

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
*/
}
