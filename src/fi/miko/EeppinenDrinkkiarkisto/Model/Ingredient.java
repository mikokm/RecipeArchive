package fi.miko.EeppinenDrinkkiarkisto.Model;

public class Ingredient {
	private int amount;
	private String name;
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ingredient(int amount, String name) {
		super();
		this.amount = amount;
		this.name = name;
	}
}
