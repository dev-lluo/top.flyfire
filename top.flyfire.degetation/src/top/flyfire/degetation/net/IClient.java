package top.flyfire.degetation.net;

import top.flyfire.degetation.net.tcp.c2c.C2CBuffer;

public interface IClient {
	void startup(ClientConfig config);
	void shutdown();
	void send(byte[] by,String cId,String contentType);
	void recv(C2CBuffer buffer);
}
