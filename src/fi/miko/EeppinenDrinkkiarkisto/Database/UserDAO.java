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
	public static String hashPassword(String password, String salt) {
		return DigestUtils.sha1Hex(password + salt);
	}
	
	private static User createFromResult(ResultSet rs) throws SQLException {
		User user = new User(rs.getInt("user_id"), rs.getString("username"));
		user.setSalt(rs.getString("salt"));
		user.setPassword(rs.getString("password"));
		user.setAdmin(rs.getBoolean("admin"));
		
		String date = new DateTime(rs.getTimestamp("last_login").getTime()).toString("hh:mm dd.MM.yyy");
		user.setLastLogin(date);
		
		return user;
	}

	public static List<User> getUserList() {
		return null;
	}

	public static User getUserWithUsername(QueryRunner runner, String username) throws SQLException {
		ResultSetHandler<User> rsh = new ResultSetHandler<User>() {
			@Override
			public User handle(ResultSet rs) throws SQLException {
				if(!rs.next()) {
					return null;
				}
				
				return createFromResult(rs);
			}
			
		};
		
		return runner.query("SELECT user_id, username, password, salt, admin, last_login FROM Users " + "WHERE username = ?", rsh, username);
	}

	public static void addUser(User user) {

	}

	public static void removeUserWithId(int id) {

	}
}
