package scc.data;
/*Messages: channels’ message. Each message must include the sender, the channel
it belongs to, the text, (optionally) an associated image/video and (optionally) the
message it replies to.*/

public class Message {	
	private String id;
	//Optional
	private String replyTo;
	private String channel;
	private String user;
	private String text;
	//Optional
	private String imageId;

	
	public Message (String id, String replyTo, String channel, String user, String text, String imageId) {
		this.id = id;
		this.replyTo = replyTo;
		this.channel = channel;
		this.user = user;
		this.text = text;
		this.imageId = imageId;
	}
	
	public Message() {
		
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
		return "Message [id=" + id + ", replyTo=" + replyTo + ", channel=" + channel + ", user=" + user + ", text="
				+ text + ", imageId=" + imageId + "]";
	}
	
}

