package test;

import top.flyfire.degetation.net.ServerConfig;
import top.flyfire.degetation.net.tcp.c2c.C2CServer;

public class Main{
	public static void main(String[] args)  {
		new C2CServer().startup(new ServerConfig(10001, 10, ServerConfig.Mode.C2C));
	}
}

