package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.SQLException;

public class DatabaseHelper {
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

	public static boolean constraintViolation(SQLException exception) {
		return exception.getSQLState().contains("23505");
	}
}
