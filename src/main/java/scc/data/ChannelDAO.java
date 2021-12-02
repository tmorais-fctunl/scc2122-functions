package scc.data;

import java.util.Arrays;

public class ChannelDAO {
	private String _rid;
	private String _ts;
	private String id;
	private String name;
	private String owner;
	private Boolean publicChannel;
	private String[] members;
	
	
	public ChannelDAO(String id, String name, String owner, Boolean publicChannel, String[] members) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.publicChannel = publicChannel;
		this.members = members;
	}
	
	//Default ChannelDAO Constructor
	public ChannelDAO() {
		
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
		return "ChannelDAO [_rid=" + _rid + ", _ts=" + _ts + ", id=" + id + ", name=" + name + ", owner=" + owner
				+ ", publicChannel=" + publicChannel + ", members=" + Arrays.toString(members) + "]";
	}
	
	/**
	 * Replaces the values of this object with the corresponding not null values of the replacement 
	 * @param replacement
	 * @return
	 */
	public ChannelDAO replace(ChannelDAO replacement) {
		if (replacement.getId() != null)
			this.id = replacement.getId();
		if (replacement.getName()!=null)
			this.name = replacement.getName();
		if (replacement.getOwner()!=null)
			this.owner = replacement.getOwner();
		//if (replacement.get)
		return null;
	}
	
	public Channel toChannel() {
		return new Channel(id,name,owner,publicChannel,members);
	}
	
}
