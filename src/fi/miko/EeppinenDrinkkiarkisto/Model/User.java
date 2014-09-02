package fi.miko.EeppinenDrinkkiarkisto.Model;

public class User {
	private int id;
	private String username;
	private String password;
	private String salt;
	private String lastLogin;
	private boolean admin;

	public User(int id, String username) {
		this.id = id;
		this.username = username;
	}

	public boolean getAdmin() {
		return admin;
	}

	public int getId() {
		return id;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}

	public String getUsername() {
		return username;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
