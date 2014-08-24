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
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;
import org.joda.time.DateTime;

public class Drink {
	private int id;
	private String name;
	private String description;
	private String url;
	private String date;
	private String owner;
	private int ownerId;
	private Map<String, Integer> ingredients;

	private Drink(int id, String name, String description, String url, String date, String owner, int ownerId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.url = url;
		this.date = date;
		this.owner = owner;
		this.ownerId = ownerId;
	}

	private Drink(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	private Drink() {
		this.id = 0;
	}

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

	public String getDate() {
		return date;
	}

	public String getOwner() {
		return owner;
	}

	public int getOwnerId() {
		return ownerId;
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

	public Map<String, Integer> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Map<String, Integer> ingredients) {
		this.ingredients = ingredients;
	}

	public static Drink createDrink() {
		return new Drink();
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

		String sql = ("SELECT drink_id, name, description, picture_url, date, owner, username "
				+ "FROM Drinks INNER JOIN Users ON Drinks.owner = Users.user_id " + "WHERE drink_id = ? " + "ORDER BY name");

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				DateTime date = new DateTime(rs.getTimestamp("date").getTime());

				drink = new Drink(rs.getInt("drink_id"), rs.getString("name"), rs.getString("description"), rs.getString("picture_url"),
						date.toString("hh:mm dd.MM.yyy"), rs.getString("username"), rs.getInt("owner"));
			}

			DbUtils.closeQuietly(null, st, rs);
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

	public static boolean addDrinkToDatabase(Connection conn, Drink drink, int ownerId) {
		if (drink.getId() != 0) {
			return false;
		}

		String sql = "INSERT INTO Drinks(name, date, owner) VALUES(?, now(), ?) RETURNING drink_id";
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(sql);
			st.setString(1, drink.getName());
			st.setInt(2, ownerId);

			rs = st.executeQuery();
			if (rs.next()) {
				drink.id = rs.getInt("drink_id");
			}

			DbUtils.closeQuietly(conn, st, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, st, rs);
		}

		return drink.getId() != 0;
	}

	public void deleteDrink(Connection conn) {
		String sql = "DELETE FROM Drinks WHERE drink_id = ?";
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			int rs = st.executeUpdate();

			DbUtils.closeQuietly(conn, st, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, st, null);
		}
	}

	public boolean saveDrink(Connection conn) {
		if (id == 0) {
			return false;
		}

		String sql;

		sql = "UPDATE Drinks SET name = ?, description = ?, picture_url = ? " + "WHERE drink_id = ?";

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(sql);
			st.setString(1, name);
			st.setString(2, description);
			st.setString(3, url);
			st.setInt(4, id);

			int rs = st.executeUpdate();

			DbUtils.closeQuietly(st);
			saveIngredients(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, st, null);
		}

		return true;
	}

	private void saveIngredients(Connection conn) throws SQLException {
		PreparedStatement st = conn.prepareStatement("DELETE FROM Ingredients WHERE drink_id = ?");
		st.setInt(1, id);
		st.executeQuery();
		DbUtils.closeQuietly(st);

		st = conn.prepareStatement("INSERT INTO Ingredients(amount, name) VALUES (?, ?)");
		for (Entry<String, Integer> entry : ingredients.entrySet()) {
			st.setInt(1, entry.getValue());
			st.setString(2, entry.getKey());
			st.executeQuery();
		}

		DbUtils.closeQuietly(st);
	}
}
