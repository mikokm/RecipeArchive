package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class FavouritesDAO {
	private final QueryRunner runner;

	public FavouritesDAO(DataSource dataSource) {
		this.runner = new QueryRunner(dataSource);
	}

	public List<Drink> getFavouritesWithUserId(int userId) throws SQLException {
		String sql = "SELECT Favourites.drink_id, Drinks.name, Drinks.description "
				+ "FROM Favourites INNER JOIN Drinks ON Favourites.drink_id = Drinks.drink_id WHERE Favourites.user_id = ?";
		return runner.query(sql, new DrinkListResultSetHandler(), userId);
	}

	public void addFavourite(int userId, int drinkId) throws SQLException {
		runner.update("INSERT INTO Favourites(user_id, drink_id) VALUES(?, ?)", userId, drinkId);
	}

	public void removeFavourite(int userId, int drinkId) throws SQLException {
		runner.update("DELETE FROM Favourites WHERE user_id = ? AND drink_id = ?", userId, drinkId);
	}
}
