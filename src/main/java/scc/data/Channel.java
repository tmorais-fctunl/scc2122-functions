package scc.data;

import java.util.Arrays;

public class Channel {
	private String id;
	private String name;
	private String owner;
	private Boolean publicChannel;
	private String[] members;
	
	public Channel(String id, String name, String owner, Boolean publicChannel, String[] members) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.publicChannel = publicChannel;
		this.members = members;
	}
	
	//Default Channel constructor
	public Channel() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Boolean getPublicChannel() {
		return publicChannel;
	}

	public void setPublicChannel(Boolean publicChannel) {
		this.publicChannel = publicChannel;
	}

	public String[] getMembers() {
		return members;
	}

	public void setMembers(String[] members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "Channel [id=" + id + ", name=" + name + ", owner=" + owner + ", publicChannel=" + publicChannel
				+ ", members=" + Arrays.toString(members) + "]";
	}
	
	public ChannelDAO toChannelDAO() {
		return new ChannelDAO(id,name,owner,publicChannel,members);
	}
	
	/*public boolean isMember (String user) {
		for (String u : members) {
			if (user.equals(u))
				return true;
		}
		return false;
	}
	
	public boolean isOwner (String user) {
		return user.equals(owner);
	}
	
	public void addMember(String user) {
		if (!isMember(user))
			members[members.length] = user;
	}
	
	public void remMember(String user) {
		for (String u : members) {
			if (u.equals(user))
				
		}
	}*/

}
