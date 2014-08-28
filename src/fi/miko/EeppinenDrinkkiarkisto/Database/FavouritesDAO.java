package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class FavouritesDAO {
	public static List<Drink> getFavouritesWithUserId(QueryRunner runner, int userId) throws SQLException {
		String sql = "SELECT Favourites.drink_id, name, description "
				+ "FROM Favourites INNER JOIN Drinks ON Favourites.drink_id = Drinks.drink_id WHERE user_id = ?";
		return runner.query(sql, new DrinkListResultSetHandler(), userId);
	}

	public static void addFavouriteForUserId(QueryRunner runner, int userId, int drinkId) {

	}
}
