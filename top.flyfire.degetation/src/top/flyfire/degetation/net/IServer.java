package top.flyfire.degetation.net;

public interface IServer {
	public enum ServerMode{
		C2C,C2S,SPIDER
	}
	
	<T extends Throwable> void start() throws T;
	
	<T extends Throwable> void stop() throws T;
	
	String PROPERTIES_NAME = "server.properties";
	
	String SERVER_PORT = "server.port";
}
