package scc.data;

public class Session {
	
	private String uid;
	private User user;
	
	public Session (String uid, User user) {
		this.uid = uid;
		this.user = user;
	}
	
	public Session () {
		
	}
	
	public String getUID() {
		return uid;
	}
	public void setUID(String UID) {
		this.uid = UID;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
