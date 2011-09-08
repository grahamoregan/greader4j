package greader4j;

public class Account {

	private String username;

	private String password;

	public static final Account create(String username, String password) {

		Account c = new Account();

		c.username = username;
		c.password = password;

		return c;

	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
