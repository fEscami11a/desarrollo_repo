package mx.com.interware.spira.message;


public class TPacketCacheHolder {
	
	private String account;
	private long creation;
	private TPacket packet;
	
	public TPacketCacheHolder(String account,TPacket packet) {
		creation=System.currentTimeMillis();
		this.account=account;
		this.packet=packet;
	}

	public String getAccount() {
		return account;
	}

	public long getCreation() {
		return creation;
	}

	public TPacket getPacket() {
		return packet;
	}
	
	public void refreshCreation() {
		creation=System.currentTimeMillis();
	}

}
