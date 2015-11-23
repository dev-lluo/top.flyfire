package top.flyfire.degetation.net;

public interface Net {
	String LOCAL_ADDR = "127.0.0.1";
	int DEFAULT_S_PORT = 10001;
	int DEFAULT_C_PORT = 10002;
	byte[] DEFAULT_FLOW_END = {-1,-1,-1,-1,-1,-1};
	String THREADPOOL_SERVER = "Net.Server.Pool";
	String THREADPOOL_CLIENT = "Net.Client.Pool";
	String CHARSET = "UTF-8";
	byte TXT = 0;
	byte IMG = 1;
	byte VOI = 2;
	byte VID = 3;
	byte BIN = 4;
}
