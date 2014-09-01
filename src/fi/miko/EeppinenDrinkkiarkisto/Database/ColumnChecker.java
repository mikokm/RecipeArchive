package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

// Wrapper that allows querying ResultSet for fields that are not available.
// This class is stupid, please use a real ORM solution such as Hibernate.
public class ColumnChecker {
	private final Set<String> columns;
	private final ResultSet rs;

	public ColumnChecker(ResultSet rs) throws SQLException {
		columns = getColumns(rs);
		this.rs = rs;
	}

	// Read the columns from ResultSetMetaData into a HashSet.
	private Set<String> getColumns(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		Set<String> set = new HashSet<String>();

		for (int i = 1; i <= md.getColumnCount(); ++i) {
			set.add(md.getColumnName(i));
		}

		return set;
	}

	boolean contains(String column) {
		return columns.contains(column);
	}

	public int getInt(String column) throws SQLException {
		return columns.contains(column) ? rs.getInt(column) : 0;
	}

	public String getString(String column) throws SQLException {
		return columns.contains(column) ? rs.getString(column) : null;
	}
}
