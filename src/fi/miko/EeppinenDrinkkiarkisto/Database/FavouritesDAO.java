package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class FavouritesDAO {
	public static List<Drink> getFavouritesWithUserId(QueryRunner runner, int userId) throws SQLException {
		String sql = "SELECT Favourites.drink_id, Drinks.name, Drinks.description "
				+ "FROM Favourites INNER JOIN Drinks ON Favourites.drink_id = Drinks.drink_id WHERE Favourites.user_id = ?";
		return runner.query(sql, new DrinkListResultSetHandler(), userId);
	}

	public static void addFavourite(QueryRunner runner, int userId, int drinkId) throws SQLException {
		runner.update("INSERT INTO Favourites(user_id, drink_id) VALUES(?, ?)", userId, drinkId);
	}

	public static void removeFavourite(QueryRunner runner, int userId, int drinkId) throws SQLException {
		runner.update("DELETE FROM Favourites WHERE user_id = ? AND drink_id = ?", userId, drinkId);
	}
}
