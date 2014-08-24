package fi.miko.EeppinenDrinkkiarkisto.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.joda.time.DateTime;

public class Drink {
	private int id;
	private String name;
	private String description;
	private String url;
	private DateTime date;
	private String owner;
	private Map<String, Integer> ingredients;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public DateTime getDate() {
		return date;
	}

	public String getOwner() {
		return owner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private Drink(int id, String name, String description, String url, DateTime date, String owner) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.url = url;
		this.date = date;
		this.owner = owner;
	}

	private Drink(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	private static Map<String, Integer> getIngredientsFromDatabase(Connection conn, int id) {
		Map<String, Integer> ingredients = new HashMap<String, Integer>();

		String sql = ("SELECT name, amount FROM Ingredients WHERE drink_id = ?");
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);

			rs = st.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				int amount = rs.getInt("amount");

				if (ingredients.containsKey(name)) {
					amount += ingredients.get(name);
				}

				ingredients.put(name, amount);
			}

			DbUtils.closeQuietly(conn, st, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, st, rs);
		}

		return ingredients;
	}

	public static Drink getDrinkWithId(Connection conn, int id) {
		Drink drink = null;

		String sql = ("SELECT drink_id, name, description, picture_url, date, username "
				+ "FROM Drinks INNER JOIN Users ON Drinks.owner = Users.user_id " + "WHERE drink_id = ? " + "ORDER BY name");

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				DateTime date = new DateTime(rs.getTimestamp("date").getTime());

				drink = new Drink(rs.getInt("drink_id"), rs.getString("name"), rs.getString("description"), rs.getString("picture_url"), date,
						rs.getString("username"));
			}

			DbUtils.close(st);
			DbUtils.close(rs);

			drink.setIngredients(getIngredientsFromDatabase(conn, id));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, st, rs);
		}

		return drink;
	}

	public static List<Drink> getDrinkList(Connection conn) {
		List<Drink> drinks = new ArrayList<Drink>();

		String sql = ("SELECT drink_id, name, description FROM Drinks");
		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {
				Drink drink = new Drink(rs.getInt("drink_id"), rs.getString("name"), rs.getString("description"));
				drinks.add(drink);
			}

			DbUtils.closeQuietly(conn, st, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, st, rs);
		}

		return drinks;
	}

	public Map<String, Integer> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Map<String, Integer> ingredients) {
		this.ingredients = ingredients;
	}
}
