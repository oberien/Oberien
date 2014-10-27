package controller.multiplayer.client;

public class User {
	private String username;
	private int permissions;

	public User(String username, int permissions) {
		this.username = username;
		this.permissions = permissions;
	}

	public String getUsername() {
		return username;
	}

	public int getPermissions() {
		return permissions;
	}
}
