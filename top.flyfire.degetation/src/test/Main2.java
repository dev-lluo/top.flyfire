package test;

import java.io.UnsupportedEncodingException;

import top.flyfire.degetation.Const;
import top.flyfire.degetation.net.ClientConfig;
import top.flyfire.degetation.net.IClient;
import top.flyfire.degetation.net.tcp.c2c.C2Client;
import top.flyfire.degetation.net.tcp.c2c.StreamBuffer;

public class Main2 {
	public static void main(String[] args) throws InterruptedException  {
		IClient client = new C2Client();
		client.startup(new ClientConfig(10001, "127.0.0.1", ClientConfig.Mode.C2C));
		StreamBuffer buffer = new StreamBuffer();
		while (true) {
			client.recv(buffer);
			if(!buffer.isEmpty()){
				Const.CONSOLE.info(buffer.unLoad(StreamBuffer.HEAD));
				try {
					Const.CONSOLE.info(new String(buffer.unLoad(StreamBuffer.BODY),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					Const.CONSOLE.error(e);
				}
				Const.CONSOLE.info(buffer.unLoad(StreamBuffer.FOOT));
			}
			Thread.sleep(10);
		}
	}
}
