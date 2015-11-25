package top.flyfire.degetation.net;

public class ServerConfig {
	
	private static int index = 0;
	
	public enum Mode{
		C2C
	}
	private int port;
	private int threadPoolSize;
	private Mode mode;
	private int idx;
	
	public int getPort() {
		return port;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public Mode getMode() {
		return mode;
	}
	
	public int getIdx(){
		return idx;
	}

	public ServerConfig(int port, int threadPoolSize, Mode mode) {
		super();
		this.port = port;
		this.threadPoolSize = threadPoolSize;
		this.mode = mode;
		synchronized (ServerConfig.class) {
			this.idx = index++;
		}
	}
	
	
}
