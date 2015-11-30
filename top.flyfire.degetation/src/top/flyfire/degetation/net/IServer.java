package top.flyfire.degetation.net;

public interface IServer {
	int DEFAULT_PORT = 10001;
	void startup(ServerConfig config);
	void shutdown();
}
