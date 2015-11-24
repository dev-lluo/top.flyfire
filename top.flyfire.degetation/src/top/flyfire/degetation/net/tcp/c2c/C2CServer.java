package top.flyfire.degetation.net.tcp.c2c;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import top.flyfire.degetation.Const;
import top.flyfire.degetation.UUID;
import top.flyfire.degetation.buffer.BufferEngine;
import top.flyfire.degetation.buffer.IBuffer;
import top.flyfire.degetation.net.ServerEngine;
import top.flyfire.degetation.stream.Stream;
import top.flyfire.degetation.thread.RunTask;

public class C2CServer extends ServerEngine {

	private Properties properties = new Properties();

	private ServerSocket serverSocket;
	
	/**
	 * 读取配置文件
	 * @param name 服务名
	 */
	public C2CServer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		InputStream inputStream = this.getClass().getResourceAsStream(PROPERTIES_NAME);
		if (inputStream == null) {
			inputStream = C2CServer.class.getResourceAsStream(PROPERTIES_NAME);
		}
		try {
			properties.load(inputStream);
			this.port = Integer.valueOf(properties.getProperty(SERVER_PORT));
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		

	}

	/**
	 * 启动服务监听
	 * 得到套接字后；创建客户端输入 输出任务
	 * 进行广播服务
	 * @throws IOException
	 */
	@Override
	public void start() throws IOException {
		// TODO Auto-generated method stub
		Socket socket = this.serverSocket.accept();
		String guid = UUID.$.createGUID();
		this.pool.execute(new ClientInput(socket, guid),new ClientOutput(socket, guid));
		this.radiate();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void exec() {
		// TODO Auto-generated method stub
		try {
			this.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void radiate() {
		
	}

	@Override
	protected void doAfter() {
		// TODO Auto-generated method stub
		super.doAfter();
		ServerEngine.enginePool.execute(this);
	}


	public class ClientBuffer implements IBuffer<byte[]> {

		private byte[] by;
		
		public ClientBuffer(byte[] by) {
			// TODO Auto-generated constructor stub
			this.by = by;
		}
		
		@Override
		public byte[] buffer() {
			// TODO Auto-generated method stub
			return this.by;
		}
		
	}
	
	public class ClientOutput extends RunTask<Object> implements Const{

		private BufferEngine outputEngine = new BufferEngine();
		@SuppressWarnings("unused")
		private Socket socket;
		private OutputStream outputStream;
		private String guid;
		
		
		public ClientOutput(Socket socket, String guid) {
			this.socket = socket;
			this.guid = guid;
			try {
				this.outputStream =socket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		}
		
		public String guid(){
			return guid;
		}
		
		public void write(IBuffer<byte[]> buffer){
			this.outputEngine.write(buffer);
		}

		
		@Override
		protected void exec() {
			// TODO Auto-generated method stub
			if(!this.outputEngine.isEmpty()){
				@SuppressWarnings("unchecked")
				IBuffer<byte[]> buffer = (IBuffer<byte[]>) outputEngine.read();
				try {
					this.outputStream.write(buffer.buffer());
					this.outputStream.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException(e);
				}
			}
		}
		
		@Override
		protected void doAfter() {
			// TODO Auto-generated method stub
			super.doAfter();
			C2CServer.this.pool.execute(this);
		}
		
	}

	public class ClientInput extends RunTask<Object> implements Const {
		private BufferEngine inputEngine = new BufferEngine();
		@SuppressWarnings("unused")
		private Socket socket;
		private InputStream inputStream;
		private String guid;

		public ClientInput(Socket socket, String guid) {
			this.socket = socket;
			this.guid = guid;
			try {
				this.inputStream =socket.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		}
		
		public String guid(){
			return guid;
		}

		@Override
		protected void exec() {
			// TODO Auto-generated method stub
			Stream.run(this.inputStream, new Stream.Task() {
				
				@Override
				public boolean exec(byte[] by, int len) throws IOException {
					// TODO Auto-generated method stub
					inputEngine.write(new ClientBuffer(by));
					return true;
				}
			});
		}

		@Override
		protected void doAfter() {
			// TODO Auto-generated method stub
			super.doAfter();
			C2CServer.this.pool.execute(this);
		}
		
		
	}

}
