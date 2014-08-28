package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.joda.time.DateTime;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkDAO {
	public static Drink createFromResultSet(ResultSet rs) throws SQLException {
		ColumnChecker c = new ColumnChecker(rs);
		
		Drink drink = new Drink(c.getInt("drink_id"), c.getString("name"));
		drink.setDescription(c.getString("description"));
		drink.setImageUrl(c.getString("image_url"));
		drink.setOwner(c.getString("username"));
		drink.setOwnerId(c.getInt("owner"));
		
		if(c.contains("date")) {
			Timestamp ts = rs.getTimestamp("date");
			String date = new DateTime(ts.getTime()).toString("hh:mm dd.MM.yyy");
			
			drink.setDate(date);
		}

		return drink;
	}

	private static List<String> getIngredientsFromDatabase(QueryRunner runner, int id) throws SQLException {
		String sql = "SELECT name FROM Ingredients WHERE drink_id = ?";
		return runner.query(sql, new ColumnListHandler<String>("name"), id);
	}

	public static Drink getDrinkWithId(QueryRunner runner, int id) throws SQLException {
		String sql = "SELECT drink_id, name, description, image_url, date, owner, username "
				+ "FROM Drinks INNER JOIN Users ON Drinks.owner = Users.user_id WHERE drink_id = ?";

		ResultSetHandler<Drink> rhs = new ResultSetHandler<Drink>() {
			@Override
			public Drink handle(ResultSet rs) throws SQLException {
				return rs.next() ? createFromResultSet(rs) : null;
			}
		};

		Drink drink = runner.query(sql, rhs, id);

		if (drink != null) {
			List<String> ingredients = getIngredientsFromDatabase(runner, id);
			drink.setIngredients(ingredients);
		}

		return drink;
	}

	public static List<Drink> getDrinkList(QueryRunner runner) throws SQLException {
		return runner.query("SELECT drink_id, name, description FROM Drinks ORDER BY name", new DrinkListResultSetHandler());
	}

	public static boolean addDrinkToDatabase(QueryRunner runner, Drink drink) throws SQLException {
		if (drink.getId() != 0) {
			return false;
		}

		String sql = "INSERT INTO Drinks(name, description, image_url, owner, date) VALUES(?, ?, ?, ?, now()) RETURNING drink_id";
		int id = runner.query(sql, new ScalarHandler<Integer>("drink_id"), drink.getName(), drink.getDescription(), drink.getImageUrl(),
				drink.getOwnerId());

		drink.setId(id);

		if (id != 0) {
			saveIngredients(runner, drink);
		}

		return drink.getId() != 0;
	}

	public static void deleteDrink(QueryRunner runner, int id) throws SQLException {
		runner.update("DELETE FROM Drinks WHERE drink_id = ?", id);
	}

	public static void updateDrink(QueryRunner runner, Drink drink) throws SQLException {
		String sql = "UPDATE Drinks SET name = ?, description = ?, image_url = ? " + "WHERE drink_id = ?";
		runner.update(sql, drink.getName(), drink.getDescription(), drink.getImageUrl(), drink.getId());

		saveIngredients(runner, drink);
	}

	private static void saveIngredients(QueryRunner runner, Drink drink) throws SQLException {
		runner.update("DELETE FROM Ingredients WHERE drink_id = ?", drink.getId());

		for (String ingredient : drink.getIngredients()) {
			runner.update("INSERT INTO Ingredients(drink_id, name) VALUES (?, ?)", drink.getId(), ingredient);
		}
	}
}
