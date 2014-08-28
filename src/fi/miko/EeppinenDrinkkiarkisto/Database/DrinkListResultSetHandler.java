package fi.miko.EeppinenDrinkkiarkisto.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import fi.miko.EeppinenDrinkkiarkisto.Model.Drink;

public class DrinkListResultSetHandler implements ResultSetHandler<List<Drink>> {
	@Override
	public List<Drink> handle(ResultSet rs) throws SQLException {
		List<Drink> drinks = new ArrayList<Drink>();

		while (rs.next()) {
			Drink drink = DrinkDAO.createFromResultSet(rs);
			drinks.add(drink);
		}

		return drinks;
	}
}