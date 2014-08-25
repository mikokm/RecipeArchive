package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.joda.time.DateTime;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkDAO {
	private static Drink createFromFullResult(ResultSet rs) throws SQLException {
		Timestamp ts = rs.getTimestamp("date");
		String date = null;

		if (ts != null) {
			date = new DateTime(ts.getTime()).toString("hh:mm dd.MM.yyy");
		}

		return new Drink(rs.getInt("drink_id"), rs.getString("name"), rs.getString("description"), rs.getString("picture_url"), date,
				rs.getString("username"), rs.getInt("owner"));
	}

	private static Drink createFromWrapperResult(ResultSet rs) throws SQLException {
		return new Drink(rs.getInt("drink_id"), rs.getString("name"), rs.getString("description"));
	}

	private static List<String> getIngredientsFromDatabase(QueryRunner runner, int id) throws SQLException {
		String sql = "SELECT name FROM Ingredients WHERE drink_id = ?";
		return runner.query(sql, new ColumnListHandler<String>("name"), id);
	}

	public static Drink getDrinkWithId(QueryRunner runner, int id) throws SQLException {
		String sql = "SELECT drink_id, name, description, picture_url, date, owner, username "
				+ "FROM Drinks INNER JOIN Users ON Drinks.owner = Users.user_id " + "WHERE drink_id = ? " + "ORDER BY name";

		ResultSetHandler<Drink> rhs = new ResultSetHandler<Drink>() {
			@Override
			public Drink handle(ResultSet rs) throws SQLException {
				return rs.next() ? createFromFullResult(rs) : null;
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
		ResultSetHandler<List<Drink>> rsh = new ResultSetHandler<List<Drink>>() {
			@Override
			public List<Drink> handle(ResultSet rs) throws SQLException {
				List<Drink> drinks = new ArrayList<Drink>();

				while (rs.next()) {
					drinks.add(createFromWrapperResult(rs));
				}

				return drinks;
			}
		};

		return runner.query("SELECT drink_id, name, description FROM Drinks", rsh);
	}

	public static boolean addDrinkToDatabase(QueryRunner runner, Drink drink) throws SQLException {
		if (drink.getId() != 0) {
			return false;
		}

		String sql = "INSERT INTO Drinks(name, date, owner) VALUES(?, now(), ?) RETURNING drink_id";

		int id = runner.query(sql, new ScalarHandler<Integer>("drink_id"), drink.getName(), drink.getOwnerId());
		drink.setId(id);

		return drink.getId() != 0;
	}

	public static void deleteDrink(QueryRunner runner, int id) throws SQLException {
		runner.update("DELETE FROM Drinks WHERE drink_id = ?", id);
	}

	public static boolean saveDrink(QueryRunner runner, Drink drink) throws SQLException {
		if (drink.getId() == 0) {
			return false;
		}

		String sql = "UPDATE Drinks SET name = ?, description = ?, picture_url = ? " + "WHERE drink_id = ?";
		runner.update(sql, drink.getName(), drink.getDescription(), drink.getImageUrl(), drink.getId());

		saveIngredients(runner, drink);

		return true;
	}

	private static void saveIngredients(QueryRunner runner, Drink drink) throws SQLException {
		runner.update("DELETE FROM Ingredients WHERE drink_id = ?", drink.getId());

		for (String ingredient : drink.getIngredients()) {
			runner.update("INSERT INTO Ingredients(drink_id, name) VALUES (?, ?)", drink.getId(), ingredient);
		}
	}
}
