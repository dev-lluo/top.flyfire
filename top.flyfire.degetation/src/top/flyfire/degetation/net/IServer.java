package top.flyfire.degetation.net;

public interface IServer {
	int DEFAULT_PORT = 10001;
	byte[] FROM_SERVER = {0,0,0,0,0,0};
	void startup(ServerConfig config);
	void shutdown();
}
