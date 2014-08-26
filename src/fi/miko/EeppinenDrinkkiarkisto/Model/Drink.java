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
	
	public Drink() {
		this.id = 0;
	}
	
	public Drink(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}
	
	public int getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setImageUrl(String url) {
		this.imageUrl = url;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
}
