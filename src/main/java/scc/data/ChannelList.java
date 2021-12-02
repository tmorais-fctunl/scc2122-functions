package scc.data;

import java.util.List;

public class ChannelList {
	
	private List<Channel> channels;
	private int offset;
	private int nextOffset;
	
	public ChannelList(List<Channel> channels, int offset) {
		this.channels = channels;
		this.offset = offset;
		this.setNextOffset(offset+1);
		
	}
	
	public ChannelList() {
		
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getNextOffset() {
		return nextOffset;
	}

	public void setNextOffset(int nextOffset) {
		this.nextOffset = nextOffset;
	}
	

}
