package top.flyfire.degetation.net.tcp.c2c;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import top.flyfire.degetation.Const;
import top.flyfire.degetation.buffer.BufferEngine;
import top.flyfire.degetation.net.ClientConfig;
import top.flyfire.degetation.net.IClient;

public class C2Client implements IClient ,Const{
	
	private Socket socket;
	
	@SuppressWarnings("unused")
	private ClientConfig config;
	
	private boolean status = OFF;
	
	@SuppressWarnings("unused")
	private ClientSocket clientSocket;
	
	private BufferEngine<byte[]> inputEngine = new BufferEngine<byte[]>();
	private BufferEngine<byte[]> outputEngine = new BufferEngine<byte[]>();
	
	@Override
	public void startup(ClientConfig config) {
		// TODO Auto-generated method stub
		if(status==OFF){
			this.config = config;
			try {
				this.socket = new Socket(config.getServerAddr(), config.getPort());
				this.clientSocket = new ClientSocket(this.socket, inputEngine, outputEngine);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			this.status = ON;
		}
	}
	

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		if(this.status==ON){
			try {
				this.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			this.socket = null;
			this.config = null;
			this.status = OFF;
		}
	}

	@Override
	public void send(byte[] by, String cId, String contentType) {
		// TODO Auto-generated method stub
			C2CHeader header = new C2CHeader(cId, new Date().toString(), contentType);
			byte[] body = by;
			C2CBuffer buffer = new C2CBuffer();
			buffer.head(header.serialize());
			buffer.load(body);
			outputEngine.write(buffer);

		
	}
	
	

	@Override
	public void recv(C2CBuffer buffer) {
		// TODO Auto-generated method stub
		inputEngine.read(buffer);
	}




}
