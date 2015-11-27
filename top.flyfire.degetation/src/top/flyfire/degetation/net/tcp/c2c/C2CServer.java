package top.flyfire.degetation.net.tcp.c2c;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import top.flyfire.degetation.Const;
import top.flyfire.degetation.UUID;
import top.flyfire.degetation.buffer.BufferEngine;
import top.flyfire.degetation.net.IServer;
import top.flyfire.degetation.net.ServerConfig;
import top.flyfire.degetation.thread.RunTask;
import top.flyfire.degetation.thread.ThreadMgr;
import top.flyfire.degetation.thread.ThreadPool;

public class C2CServer implements IServer,Const {
	
	private static String NEWCLIENT_POOL = "C2CServer.NewClientPool.";
	
	private static String SWAPCLIENT_BUFFER = "C2CServer.SwapClientBufferPool.";
	
	private ServerSocket socket;
	
	private ServerConfig config;
	
	private boolean status = OFF;
	
	private ThreadPool newClientPool;
	
	private ThreadPool swapClientPool;

	@Override
	public synchronized void startup(ServerConfig config) {
		// TODO Auto-generated method stub
		if(status==OFF){
			this.config = config;
			try {
				this.socket = new ServerSocket(config.getPort());
				this.newClientPool = ThreadMgr.live(NEWCLIENT_POOL+config.getIdx());
				this.swapClientPool = ThreadMgr.live(SWAPCLIENT_BUFFER+config.getIdx());
				this.run();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			this.status = ON;
		}
	}
	
	protected void run() throws IOException {
		for(int i = 0;i<20;i++){
			SwapClientBuffer swap = new SwapClientBuffer();
			this.swapClientPool.execute(swap);
		}
		Socket clientSocket = this.socket.accept();
		do{
			this.newClientPool.execute(new NewClient(clientSocket));
		}while((clientSocket=this.socket.accept())!=null);
	}

	@Override
	public synchronized void shutdown() {
		// TODO Auto-generated method stub
		if(status==ON){
			try {
				this.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			this.socket = null;
			this.config = null;
			ThreadMgr.die(NEWCLIENT_POOL+config.getIdx());
			ThreadMgr.die(SWAPCLIENT_BUFFER+config.getIdx());
			this.newClientPool = null;
			this.swapClientPool = null;
			this.status = OFF;
		}
	}
	
	
	public class NewClient extends RunTask<Object>{
		
		private Socket socket;
		
		public NewClient(Socket socket){
			this.socket = socket;
		}

		@Override
		protected void exec() {
			// TODO Auto-generated method stub
			/*
			 * 安全起见
			 * 输入id和输出id采取两套；
			 * 所有的客户端只需要知道输出id即可
			 * 防止客户端伪造输入id
			 */
			String inputId = UUID.$.createGUID();
			String outputId = UUID.$.createGUID();
			BufferEngine<byte[][]> inputEngine = new BufferEngine<byte[][]>();
			BufferEngine<byte[][]> outputEngine = new BufferEngine<byte[][]>();
			
			IdToBuffer buffer0 = new IdToBuffer(inputId, inputEngine);
			IdToBuffer.Store.putInput(buffer0);
			
			IdToBuffer buffer1 = new IdToBuffer(outputId, outputEngine);
			IdToBuffer.Store.putOutput(buffer1);
			
			new ClientSocket(this.socket, inputEngine, outputEngine);
			this.socket = null;
		}
		
	}
	
	public class SwapClientBuffer extends RunTask<Object>{

		@Override
		protected void exec() {
			// TODO Auto-generated method stub
			List<IdToBuffer> bufferList = IdToBuffer.Store.allInput();
			StreamBuffer buffer = new StreamBuffer();
			for(IdToBuffer idToBuffer : bufferList){
				idToBuffer.val().read(buffer);
				if(!buffer.isEmpty()){
					try {
						String id = new String(buffer.unLoad(StreamBuffer.HEAD),"UTF-8");
						byte[] head = idToBuffer.key().getBytes("UTF-8");
						buffer.replace(head, StreamBuffer.HEAD);
						IdToBuffer.Store.findOutput(id).val().write(buffer);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						Const.CONSOLE.error(e);
					}
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
