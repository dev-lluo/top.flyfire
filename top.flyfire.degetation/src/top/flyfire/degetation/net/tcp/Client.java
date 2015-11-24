package top.flyfire.degetation.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import top.flyfire.degetation.net.Net;
import top.flyfire.degetation.stream.Stream;
import top.flyfire.degetation.thread.ThreadMgr;
import top.flyfire.degetation.thread.ThreadPool;

public class Client implements Net{
	private final String host;
	private final int port;
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private ThreadPool pool;
	private byte[] inBytes = new byte[0];
	public Client(String host,int port){
		this.host = host;
		this.port = port;
		
	}
	
	public void start(){
		try {
			this.socket = new Socket(this.host, this.port);
			this.inputStream = this.socket.getInputStream();
			this.outputStream = this.socket.getOutputStream();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		this.pool = ThreadMgr.live(THREADPOOL_CLIENT);
		this.accept();
	}
	
	protected synchronized void accept(){
		Stream.run(this.inputStream, new Stream.Task() {
			
			@Override
			public boolean exec(byte[] by, int len) throws IOException {
				// TODO Auto-generated method stub
				if(flowEnd(by, len))return true;
				byte[] temp = new byte[inBytes.length+len];
				System.arraycopy(inBytes, 0, temp, 0, inBytes.length);
				System.arraycopy(by, 0, temp, inBytes.length, len);
				inBytes = temp;
				return false;
			}
		});
		
	}
	
	public synchronized void send(byte[] by){
		
	}
	
	
	protected boolean flowEnd(byte[] by,int len) {
		return len==Net.DEFAULT_FLOW_END.length
				&&by[len-6]==Net.DEFAULT_FLOW_END[5]
				&&by[len-5]==Net.DEFAULT_FLOW_END[4]
				&&by[len-4]==Net.DEFAULT_FLOW_END[3]
				&&by[len-3]==Net.DEFAULT_FLOW_END[2]
				&&by[len-2]==Net.DEFAULT_FLOW_END[1]
				&&by[len-1]==Net.DEFAULT_FLOW_END[0];
	}
	
}
