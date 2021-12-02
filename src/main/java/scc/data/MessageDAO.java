package scc.data;


public class MessageDAO {
	private String _rid;
	private String _ts;
	private String id;
	//Optional
	private String replyTo;
	private String channel;
	private String user;
	private String text;
	//Optional
	private String imageId;

	public MessageDAO() {
		
	}
	
	public MessageDAO (String id, String replyTo, String channel, String user, String text, String imageId) {
		this.id = id;
		this.replyTo = replyTo;
		this.channel = channel;
		this.user = user;
		this.text = text;
		this.imageId = imageId;
	}

	public String get_rid() {
		return _rid;
	}

	public void set_rid(String _rid) {
		this._rid = _rid;
	}

	public String get_ts() {
		return _ts;
	}

	public void set_ts(String _ts) {
		this._ts = _ts;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	@Override
	public String toString() {
		return "MessageDAO [_rid=" + _rid + ", _ts=" + _ts + ", id=" + id + ", replyTo=" + replyTo + ", channel="
				+ channel + ", user=" + user + ", text=" + text + ", imageId=" + imageId + "]";
	}
	
	
}



