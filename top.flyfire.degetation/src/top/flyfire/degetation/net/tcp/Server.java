package top.flyfire.degetation.net.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import top.flyfire.degetation.net.Net;
import top.flyfire.degetation.thread.ThreadMgr;
import top.flyfire.degetation.thread.ThreadPool;

public class Server implements Net {
	private final ServerSocket server ;
	private final int port;
	private final ThreadPool pool;
	
	public Server(){
		this(DEFAULT_S_PORT);
	}
	
	public Server(int port){
		this.port = port;
		try {
			this.server = new ServerSocket(this.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		this.pool = ThreadMgr.live(THREADPOOL);
	}
	
	public void start(){
		Socket socket = null;
		try {
			while(socket==null){
				socket = this.server.accept();
				pool.execute(new ServerTask(socket));
				socket = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}