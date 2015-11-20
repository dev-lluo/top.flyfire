package top.flyfire.degetation.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import top.flyfire.degetation.Const;
import top.flyfire.degetation.net.Net;
import top.flyfire.degetation.net.NetProtocol;
import top.flyfire.degetation.stream.Stream;
import top.flyfire.degetation.thread.RunTask;
import top.flyfire.degetation.thread.ThreadMgr;

public class ServerTask extends RunTask<Object> implements Const {
	
	protected static NetProtocol protocol = new NetProtocol();
	
	private final Socket socket ;
	private InputStream inputStream;
	private OutputStream outputStream;
	private byte[] inBytes = new byte[0];
	
	public ServerTask(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	protected void exec() {
		// TODO Auto-generated method stub
		try {
			this.inputStream = socket.getInputStream();
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
			this.deal();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			CONSOLE.error(e);
		}
	}
	
	
	
	@Override
	protected void doAfter() {
		// TODO Auto-generated method stub
		super.doAfter();
		this.inBytes = new byte[0];
		ThreadMgr.live(Net.THREADPOOL).execute(this);
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
	
	protected void deal() throws IOException{
		byte[] head = protocol.protocol(inBytes);
		byte[] body = protocol.content(inBytes);
		
		byte ctype = protocol.protocol(head, NetProtocol.CTYPE);
		if(ctype==Net.TXT){
			this.dealTXT(head, body);
		}else if(ctype==Net.IMG){
			this.dealIMG(head, body);
		}else if(ctype==Net.VOI){
			this.dealVOI(head, body);
		}else if(ctype==Net.VID){
			this.dealVID(head, body);
		}else if(ctype==Net.BIN){
			this.dealBIN(head, body);
		}else{
			CONSOLE.error(inBytes);
		}
	}
	
	protected void recall(byte[] bytes) {
		try {
			this.outputStream.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			CONSOLE.error(e);
		}
	}
	
	protected void dealTXT(byte[] head,byte[] body) throws IOException{
		CONSOLE.info(new String(body,"UTF-8"));
	}
	protected void dealIMG(byte[] head,byte[] body){
		
	}
	protected void dealVOI(byte[] head,byte[] body){
		
	}
	protected void dealVID(byte[] head,byte[] body){
		
	}
	protected void dealBIN(byte[] head,byte[] body){
		
	}
}
