package fi.miko.EeppinenDrinkkiarkisto.Model;

import java.util.List;

public class Drink {
	private int id;
	private String name;
	private String description;
	private String imageUrl;
	private String date;
	private String owner;
	private int ownerId;
	private List<String> ingredients;

	public Drink(int id, String name, String description, String url, String date, String owner, int ownerId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageUrl = url;
		this.date = date;
		this.owner = owner;
		this.ownerId = ownerId;
	}

	public Drink(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Drink(int ownerId, String name) {
		this.id = 0;
		this.ownerId = ownerId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getImageUrl() {
		return imageUrl;
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

	public void setImageUrl(String url) {
		this.imageUrl = url;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
}
