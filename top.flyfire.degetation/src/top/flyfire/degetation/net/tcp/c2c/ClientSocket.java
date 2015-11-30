package top.flyfire.degetation.net.tcp.c2c;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import top.flyfire.degetation.Const;
import top.flyfire.degetation.buffer.BufferEngine;
import top.flyfire.degetation.stream.Stream;
import top.flyfire.degetation.stream.Stream.Task;
import top.flyfire.degetation.thread.RunTask;
import top.flyfire.degetation.thread.ThreadMgr;
import top.flyfire.degetation.thread.ThreadPool;

public class ClientSocket  {
	
	private final Socket socket;
	
	private final BufferEngine<byte[]> inputEngine;
	
	private final BufferEngine<byte[]> outputEngine;
	
	private final OutputStream outputStream;
	
	private final InputStream inputStream;
	
	private final InetAddress inetAddress;
	
	private final String clientAddr;
	
	private final int clientPort;
	
	private final String clientName;
	
	private final ThreadPool pool;
	
	public ClientSocket(Socket socket, BufferEngine<byte[]> inputEngine, BufferEngine<byte[]> outputEngine) {
		super();
		this.socket = socket;
		this.inputEngine = inputEngine;
		this.outputEngine = outputEngine;
		try {
			this.inputStream = this.socket.getInputStream();
			this.outputStream = this.socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		this.inetAddress = socket.getInetAddress();
		this.clientAddr = this.inetAddress.getHostAddress();
		this.clientPort = this.socket.getPort();
		this.clientName = this.inetAddress.getHostName();
		this.pool = ThreadMgr.live(this.clientName+"["+this.clientAddr+":"+this.clientPort+"]",2);
		Const.CONSOLE.info(this.pool.getPoolInfo());
		this.pool.execute(new DealInput(),new DealOutput());
		
	}
	

	
	public class DealInput extends RunTask<byte[][]>{
		C2CStrmFct fct = new C2CStrmFct(ClientSocket.this.inputEngine);
		@Override
		protected void exec() {
			// TODO Auto-generated method stub
			Stream.run(ClientSocket.this.inputStream, new Task() {
				
				@Override
				public boolean exec(byte[] by, int len) throws IOException {
					// TODO Auto-generated method stub
					CONSOLE.info("read 2");
					fct.build(by);
					CONSOLE.info("read 3");
					return false;
				}
			});
		}
		
	}
	
	public class DealOutput extends RunTask<byte[]>{

		@Override
		protected void exec() {
			// TODO Auto-generated method stub
			C2CBuffer buffer = new C2CBuffer();
			ClientSocket.this.outputEngine.read(buffer);
			if(!buffer.isEmpty()){
				try {
					ClientSocket.this.outputStream.write(buffer.head());
					ClientSocket.this.outputStream.flush();
					ClientSocket.this.outputStream.write(C2CStrmFct.HD_BD_SEP);
					ClientSocket.this.outputStream.flush();
					ClientSocket.this.outputStream.write(buffer.unLoad());
					ClientSocket.this.outputStream.flush();
					ClientSocket.this.outputStream.write(C2CStrmFct.END);
					ClientSocket.this.outputStream.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Const.CONSOLE.error(e);
				}
			}
		}

		@Override
		public boolean needRepeat() {
			// TODO Auto-generated method stub
			return true;
		}
		
		
	
	}
	
}
