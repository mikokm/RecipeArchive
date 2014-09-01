package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.joda.time.DateTime;

import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class UserDAO {
	public static String generateHash() {
		return DigestUtils.sha1Hex(DateTime.now().toString());
	}
	
	public static String hashPassword(String password, String salt) {
		return DigestUtils.sha1Hex(password + salt);
	}
	

	private static User createFromResult(ResultSet rs) throws SQLException {
		ColumnChecker c = new ColumnChecker(rs);
		User user = new User(c.getInt("user_id"), c.getString("username"));
		user.setSalt(c.getString("salt"));
		user.setPassword(c.getString("password"));

		if (c.contains("admin")) {
			user.setAdmin(rs.getBoolean("admin"));
		}

		if (c.contains("last_login")) {
			String date = new DateTime(rs.getTimestamp("last_login").getTime()).toString("HH:mm dd.MM.yyy");
			user.setLastLogin(date);
		}

		return user;
	}

	public static List<User> getUserList() {
		return null;
	}

	public static User getUserWithUsername(QueryRunner runner, String username) throws SQLException {
		ResultSetHandler<User> rsh = new ResultSetHandler<User>() {
			@Override
			public User handle(ResultSet rs) throws SQLException {
				return rs.next() ? createFromResult(rs) : null;
			}
		};

		return runner.query("SELECT user_id, username, password, salt, admin, last_login FROM Users WHERE username = ?", rsh, username);
	}

	public static void addUser(User user) {

	}

	public static void removeUserWithId(QueryRunner runner, int id) throws SQLException {
		runner.update("DELETE FROM User WHERE user_id = ?", id);
	}

	public static void updateLastLogin(QueryRunner runner, User user) {
		try {
			runner.update("UPDATE Users SET last_login = now() WHERE user_id = ?", user.getId());
		} catch (SQLException e) {
			System.out.println("Failed to update the last_login field: " + e.getMessage());
		}
	}

	public static void updateUser(QueryRunner runner, User user) throws SQLException {
		runner.update("UPDATE Users SET password = ?, salt = ? WHERE user_id = ?", user.getPassword(), user.getSalt(), user.getId());
	}
}
