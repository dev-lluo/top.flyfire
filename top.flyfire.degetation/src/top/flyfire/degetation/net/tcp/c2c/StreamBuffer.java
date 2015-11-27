package top.flyfire.degetation.net.tcp.c2c;

import top.flyfire.degetation.buffer.IBuffer;

public class StreamBuffer implements IBuffer<byte[][]>{
	
	public static final int HEAD = 0;
	public static final int BODY = 1;
	public static final int FOOT = 2;
	
	public boolean isEnd(byte[] by){
		return by.length==6&&by[0]==-1&&by[1]==-1&by[2]==-1&by[3]==-1&&by[4]==-1&&by[5]==-1;
	}
	
	private byte[][] buffer;
	
	public StreamBuffer(){
		this(new byte[0][0]);
	}

	public StreamBuffer(byte[][] buffer) {
		super();
		this.buffer = buffer;
	}

	@Override
	public byte[][] unLoad() {
		// TODO Auto-generated method stub
		byte[][] temp = this.buffer;
		this.buffer = null;
		return temp;
		
	}
	
	public byte[] unLoad(int pos){
		return buffer[pos];
	}

	@Override
	public void load(byte[][] t) {
		// TODO Auto-generated method stub
		this.buffer = t;
	}
	
	public void load(byte[] t,int pos){
		byte[] temp = new byte[t.length+this.buffer[pos].length];
		System.arraycopy(this.buffer[pos], 0, temp, 0, this.buffer[pos].length);
		System.arraycopy(t, 0, temp, this.buffer[pos].length, t.length);
		this.buffer[pos] = temp;
	}
	
	public void replace(byte[] t,int pos){
		this.buffer[pos] = t;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.buffer==null||this.buffer.length==0;
	}

	
}