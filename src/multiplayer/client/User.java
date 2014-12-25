package multiplayer.client;

public class User {
	private String mail;
	private String username;
	private int permissions;

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		return mail;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	public int getPermissions() {
		return permissions;
	}
}
