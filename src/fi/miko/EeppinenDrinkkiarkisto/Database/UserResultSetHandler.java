package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

import fi.miko.EeppinenDrinkkiarkisto.Model.User;

public class UserResultSetHandler implements ResultSetHandler<User> {
	@Override
	public User handle(ResultSet rs) throws SQLException {
		return rs.next() ? UserDAO.createFromResult(rs) : null;
	}
}
