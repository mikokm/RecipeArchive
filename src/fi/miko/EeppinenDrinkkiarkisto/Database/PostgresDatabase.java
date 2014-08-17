package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

class DatabaseException extends Exception {
	private static final long serialVersionUID = 1L;

	public DatabaseException(String message) {
		super(message);
	}
}

public class PostgresDatabase {
	private final Logger logger = Logger.getLogger("global");
	private Properties databaseProperties;
	private String databaseUrl;

	public PostgresDatabase(Properties databaseProperties) throws DatabaseException {
		loadProperties(databaseProperties);
		loadDriver();
	}

	public Connection connect() throws DatabaseException {
		Connection connection;

		try {
			logger.info("Creating new database connection.");
			connection = DriverManager.getConnection(databaseUrl, databaseProperties);
		} catch (SQLException e) {
			throw new DatabaseException("Failed to connect to the database!\n" + e.getMessage());
		}

		return connection;
	}

	private void loadDriver() throws DatabaseException {
		try {
			Class.forName("org.postgresql.Driver");
			logger.info("Loaded PostgreSQL driver.");
		} catch (ClassNotFoundException e) {
			throw new DatabaseException("Failed to load PostgreSQL JDBC driver!");
		}
	}

	private void loadProperties(Properties properties) throws DatabaseException {
		if (!properties.containsKey("user") ||
				!properties.containsKey("password") ||
				!properties.containsKey("database")) {
			throw new DatabaseException("Invalid database settings!");
		}

		databaseProperties = properties;
		databaseUrl = properties.getProperty("database");
	}
}