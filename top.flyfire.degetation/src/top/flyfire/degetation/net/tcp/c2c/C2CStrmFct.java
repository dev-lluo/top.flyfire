package top.flyfire.degetation.net.tcp.c2c;

import top.flyfire.degetation.Const;
import top.flyfire.degetation.buffer.BufferEngine;
import top.flyfire.degetation.net.StreamFactory;

public class C2CStrmFct implements StreamFactory {
	
	public static final byte[] END = {-1,-1,-1,-1,-1,-1};
	
	public static final byte END_CELL = -1;
	
	public static final byte[] HD_BD_SEP = {0,0,0,0,0,0};
	
	public static final byte HD_BD_SEP_CELL = 0;
	
	public static boolean isNull(byte[] by){
		if(by.length==12
				&&by[0]==HD_BD_SEP_CELL&&by[1]==HD_BD_SEP_CELL&&by[2]==HD_BD_SEP_CELL&&by[3]==HD_BD_SEP_CELL&&by[4]==HD_BD_SEP_CELL&&by[5]==HD_BD_SEP_CELL
				&&by[6]==END_CELL&&by[7]==END_CELL&&by[8]==END_CELL&&by[9]==END_CELL&&by[10]==END_CELL&&by[11]==END_CELL){
			return true;
		}else{
			return false;
		}
	}
	
	private byte[] byteStore = new byte[0];
	
	private BufferEngine<byte[]> inputEngine ;
	
	public C2CStrmFct(BufferEngine<byte[]> inputEngine) {
		// TODO Auto-generated constructor stub
		this.inputEngine = inputEngine;
	}

	@Override
	public synchronized void build(byte[] by) {
		// TODO Auto-generated method stub
		if(C2CStrmFct.isNull(by))return;
		Const.CONSOLE.warn(Thread.currentThread().getName());
		Const.CONSOLE.warn(by);
		byte[] temp = new byte[this.byteStore.length+by.length];
		System.arraycopy(this.byteStore, 0, temp, 0, this.byteStore.length);
		System.arraycopy(by, 0, temp, this.byteStore.length, by.length);
		this.byteStore = temp;
		Const.CONSOLE.info("read 2.1");
		this.readAsBuffer();
		Const.CONSOLE.info("read 2.2");
	}
	
	protected void readAsBuffer(){
		int index = -1;
		while(this.byteStore.length>12&&( index = this.endIndex(this.byteStore))>0){
				if(this.byteStore[index]==END_CELL
						&&this.byteStore[index+1]==END_CELL
						&&this.byteStore[index+2]==END_CELL
						&&this.byteStore[index+3]==END_CELL
						&&this.byteStore[index+4]==END_CELL
						&&this.byteStore[index+5]==END_CELL){
					byte[] bufferBy = new byte[index];
					System.arraycopy(this.byteStore, 0, bufferBy, 0, index);
					this.buildBuffer(bufferBy);
					byte[] tempByStore = new byte[this.byteStore.length-(index+6)];
					System.arraycopy(this.byteStore, index+6, tempByStore, 0, tempByStore.length);
					this.byteStore = tempByStore;
					break;
				}
		}
	}
	
	protected int endIndex(byte[] by) {
		for(int i = 6;i<=this.byteStore.length-6;i++){
			if(this.byteStore[i]==END_CELL
					&&this.byteStore[i+1]==END_CELL
					&&this.byteStore[i+2]==END_CELL
					&&this.byteStore[i+3]==END_CELL
					&&this.byteStore[i+4]==END_CELL
					&&this.byteStore[i+5]==END_CELL){
				return i;
			}
		}
		return -1;
	}
	
	protected void buildBuffer(byte[] by){
		for(int i = 6;i<=by.length-6;i++){
			if(by[i]==HD_BD_SEP_CELL
					&&by[i+1]==HD_BD_SEP_CELL
					&&by[i+2]==HD_BD_SEP_CELL
					&&by[i+3]==HD_BD_SEP_CELL
					&&by[i+4]==HD_BD_SEP_CELL
					&&by[i+5]==HD_BD_SEP_CELL){
				byte[] head = new byte[i];
				System.arraycopy(by, 0, head, 0, i);
				byte[] body = new byte[by.length-(i+6)];
				System.arraycopy(by, i+6, body, 0, body.length);
				this.inputEngine.write(new C2CBuffer(head, body));
				return;
			}
		}
	}


}
