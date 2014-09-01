package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.SQLException;

public class DatabaseHelper {
	// Returns a serial parsed from the supplied String if possible or 0 otherwise.
	public static int parseId(String idString) {
		int id;

		if (idString == null || idString.isEmpty()) {
			return 0;
		}

		try {
			id = Integer.parseInt(idString);
		} catch (NumberFormatException e) {
			id = 0;
		}

		return id;
	}

	// Returns true if this exception is caused by a unique constraint violation.
	public static boolean constraintViolation(SQLException exception) {
		return exception.getSQLState().contains("23505");
	}
}
