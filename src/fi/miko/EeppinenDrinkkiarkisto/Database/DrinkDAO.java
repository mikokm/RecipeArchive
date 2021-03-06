package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.joda.time.DateTime;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkDAO {
	private final QueryRunner runner;

	public DrinkDAO(DataSource dataSource) {
		runner = new QueryRunner(dataSource);
	}

	public void addDrink(Drink drink) throws SQLException {
		String sql = "INSERT INTO Drinks(name, description, instructions, image_url, owner_id, date) "
				+ "VALUES(?, ?, ?, ?, ?, now()) RETURNING drink_id";
		int id = runner.query(sql, new ScalarHandler<Integer>("drink_id"),
				drink.getName(), drink.getDescription(), drink.getInstructions(), drink.getImageUrl(), drink.getOwnerId());

		drink.setId(id);

		// If the drink id is 0, the query failed.
		if (id != 0) {
			saveDrinkIngredients(drink);
		}
	}

	public static Drink createFromResultSet(ResultSet rs) throws SQLException {
		ColumnChecker c = new ColumnChecker(rs);

		// Return a new Drink object with all the available fields in the
		// ResultSet.
		Drink drink = new Drink(c.getInt("drink_id"), c.getString("name"));
		drink.setDescription(c.getString("description"));
		drink.setInstructions(c.getString("instructions"));
		drink.setImageUrl(c.getString("image_url"));
		drink.setOwner(c.getString("owner_name"));
		drink.setOwnerId(c.getInt("owner_id"));

		if (c.contains("favourite")) {
			drink.setFavourite(rs.getBoolean("favourite"));
		}

		if (c.contains("date")) {
			Timestamp ts = rs.getTimestamp("date");

			String date = "Unknown";
			if (ts != null) {
				date = new DateTime(ts.getTime()).toString("HH:mm dd.MM.yyy");
			}

			drink.setDate(date);
		}

		return drink;
	}

	public void deleteDrink(int id) throws SQLException {
		runner.update("DELETE FROM Drinks WHERE drink_id = ?", id);
	}

	public Drink getDrink(int id) throws SQLException {
		String sql = "SELECT Drinks.drink_id, Drinks.name, Drinks.description, Drinks.instructions, Drinks.image_url, "
				+ "Drinks.date, Drinks.owner_id, Users.username AS owner_name FROM Drinks "
				+ "LEFT OUTER JOIN Users ON Drinks.owner_id = Users.user_id WHERE Drinks.drink_id = ?";

		ResultSetHandler<Drink> rhs = new ResultSetHandler<Drink>() {
			@Override
			public Drink handle(ResultSet rs) throws SQLException {
				return rs.next() ? createFromResultSet(rs) : null;
			}
		};

		Drink drink = runner.query(sql, rhs, id);

		if (drink != null) {
			List<String> ingredients = getIngredients(id);
			drink.setIngredients(ingredients);
		}

		return drink;
	}

	public List<Drink> getDrinkList(int userId) throws SQLException {
		String sql = "SELECT Drinks.drink_id, Drinks.name, Drinks.description, (Favourites.user_id IS NOT NULL) AS favourite FROM Drinks "
				+ "LEFT OUTER JOIN (SELECT * From Favourites WHERE Favourites.user_id = ?) Favourites ON Drinks.drink_id = Favourites.drink_id "
				+ "ORDER BY Drinks.name";

		return runner.query(sql, new DrinkListResultSetHandler(), userId);
	}

	public int getDrinkOwnerId(int id) throws SQLException {
		Integer ownerId = runner.query("SELECT owner_id FROM Drinks WHERE drink_id = ?", new ScalarHandler<Integer>("owner_id"), id);
		// If the ownerId is null, the drink doesn't exists.
		return ownerId != null ? ownerId : 0;
	}

	private List<String> getIngredients(int id) throws SQLException {
		String sql = "SELECT name FROM Ingredients WHERE drink_id = ?";
		return runner.query(sql, new ColumnListHandler<String>("name"), id);
	}

	private void saveDrinkIngredients(Drink drink) throws SQLException {
		runner.update("DELETE FROM Ingredients WHERE drink_id = ?", drink.getId());

		for (String ingredient : drink.getIngredients()) {
			runner.update("INSERT INTO Ingredients(drink_id, name) VALUES (?, ?)", drink.getId(), ingredient);
		}
	}

	public void updateDrink(Drink drink) throws SQLException {
		String sql = "UPDATE Drinks SET name = ?, description = ?, instructions = ?, image_url = ? WHERE drink_id = ?";
		runner.update(sql, drink.getName(), drink.getDescription(), drink.getInstructions(), drink.getImageUrl(),
				drink.getId());

		saveDrinkIngredients(drink);
	}
}
