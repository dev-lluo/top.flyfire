package top.flyfire.degetation.net;

public class ClientConfig {
	
	private static int index = 0;
	
	public enum Mode{
		C2C
	}
	
	private int port;
	private String serverAddr;
	private Mode mode;
	private int idx;
	public int getIdx() {
		return idx;
	}

	public Mode getMode() {
		return mode;
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public int getPort() {
		return port;
	}

	public ClientConfig(int port, String serverAddr, Mode mode) {
		super();
		this.port = port;
		this.serverAddr = serverAddr;
		this.mode = mode;
		synchronized (ClientConfig.class) {
			this.idx = index++;
		}
		
	}
	
	

}
