package scc.data;

public class Login {
	
	private String username;
	private String password;
	private String cookie;
	
	public Login (String username, String password, String cookie) {
		super();
		this.username = username;
		this.password = password;
		this.cookie = cookie;
	}
	
	public Login () {
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie (String cookie) {
		this.cookie = cookie;
	}

}
