package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.joda.time.DateTime;

import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class UserDAO {
	private final QueryRunner runner;

	public UserDAO(DataSource ds) {
		runner = new QueryRunner(ds);
	}

	public void addUser(User user) throws SQLException {
		String sql = "INSERT INTO Users(username, password, salt, admin, last_login VALUES(?, ?, ?, ?, NULL) RETURNING user_id";
		int id = runner.query(sql, new ScalarHandler<Integer>("user_id"), user.getUsername(), user.getPassword(),
				user.getSalt(), user.getAdmin());

		user.setId(id);
	}

	public static User createFromResult(ResultSet rs) throws SQLException {
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

	public static String generateSalt() {
		return DigestUtils.sha1Hex(DateTime.now().toString());
	}

	public static String getPasswordHash(String password, String salt) {
		return DigestUtils.sha1Hex(password + salt);
	}

	public List<User> getUserList() throws SQLException {
		ResultSetHandler<List<User>> rsh = new ResultSetHandler<List<User>>() {
			@Override
			public List<User> handle(ResultSet rs) throws SQLException {
				List<User> users = new ArrayList<User>();

				while (rs.next()) {
					User user = createFromResult(rs);
					users.add(user);
				}

				return users;
			}
		};

		return runner.query("SELECT user_id, username, admin, last_login FROM Users ORDER BY username", rsh);
	}

	public User getUser(String username) throws SQLException {
		return runner.query(
				"SELECT user_id, username, password, salt, admin, last_login FROM Users WHERE username = ?",
				new UserResultSetHandler(), username);
	}

	public User getUser(int id) throws SQLException {
		return runner.query("SELECT user_id, username, password, salt, admin, last_login FROM Users WHERE user_id = ?",
				new UserResultSetHandler(), id);
	}

	public void removeUser(int id) throws SQLException {
		runner.update("DELETE FROM User WHERE user_id = ?", id);
	}

	public void updateLastLogin(User user) {
		try {
			runner.update("UPDATE Users SET last_login = now() WHERE user_id = ?", user.getId());
		} catch (SQLException e) {
			System.out.println("Failed to update the last_login field: " + e.getMessage());
		}
	}

	public void updateUser(User user) throws SQLException {
		runner.update("UPDATE Users SET password = ?, salt = ?, admin = ? WHERE user_id = ?", user.getPassword(),
				user.getSalt(), user.getAdmin(), user.getId());
	}
}
