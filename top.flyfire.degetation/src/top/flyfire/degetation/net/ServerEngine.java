package top.flyfire.degetation.net;

import top.flyfire.degetation.net.tcp.c2c.C2CServer;
import top.flyfire.degetation.thread.RunTask;
import top.flyfire.degetation.thread.ThreadMgr;
import top.flyfire.degetation.thread.ThreadPool;

public abstract class ServerEngine  extends RunTask<Object> implements IServer {
	
	private static String NAME = "ServerEngine";
	
	private static String PREFIX = "Server.";
	
	private static int index = 0;
	
	protected static ThreadPool enginePool ;
	
	protected ThreadPool pool ;
	
	static{
		enginePool = ThreadMgr.live(NAME, 20);
	}
	
	public synchronized static IServer On(IServer.ServerMode mode){
		String name = PREFIX+mode.name()+"."+(index++);
		if(IServer.ServerMode.C2C == mode){
			ServerEngine server = new C2CServer(name);
			enginePool.execute(server);
			return server;
		}else if(IServer.ServerMode.C2S == mode){
			return null;
		}else if(IServer.ServerMode.SPIDER == mode){
			return null;
		}else{
			throw new RuntimeException("不支持的ServerMode["+mode+"]");
		}
	}
	
	public static void Off(IServer server){
		try {
			server.stop();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	protected final String name;
	
	protected int port;
	
	protected ServerEngine(String name){
		this.name = name;
		this.pool = ThreadMgr.live(name, 20);
	}
}
