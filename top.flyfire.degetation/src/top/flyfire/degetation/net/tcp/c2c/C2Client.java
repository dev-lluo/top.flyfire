package top.flyfire.degetation.net.tcp.c2c;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

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
	
	private BufferEngine<byte[][]> inputEngine = new BufferEngine<byte[][]>();
	private BufferEngine<byte[][]> outputEngine = new BufferEngine<byte[][]>();
	
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
	public void send(byte[] by, String cId) {
		// TODO Auto-generated method stub
		try {
			byte[] head = cId.getBytes("UTF-8");
			byte[] body = by;
			byte[] foot = {-1,-1,-1,-1,-1,-1};
			StreamBuffer buffer = new StreamBuffer();
			buffer.load(head, StreamBuffer.HEAD);
			buffer.load(body,StreamBuffer.BODY);
			buffer.load(foot,StreamBuffer.FOOT);
			outputEngine.write(buffer);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Const.CONSOLE.error(e);
		}
		
	}
	
	

	@Override
	public void recv(StreamBuffer buffer) {
		// TODO Auto-generated method stub
		inputEngine.read(buffer);
	}




}
