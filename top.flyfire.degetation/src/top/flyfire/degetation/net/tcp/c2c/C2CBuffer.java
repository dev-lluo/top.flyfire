package top.flyfire.degetation.net.tcp.c2c;

import top.flyfire.degetation.buffer.IBuffer;

public class C2CBuffer implements IBuffer<byte[]> {
	
	private byte[] head;
	
	private C2CHeader header;
	
	private byte[] body;

	public byte[] head(){
		return this.head;
	}
	
	public void head(byte[] head){
		this.head = head;
		this.header = new C2CHeader(this.head);
	}
	
	public C2CHeader header(){
		return this.header;
	}

	@Override
	public byte[] unLoad() {
		// TODO Auto-generated method stub
		return this.body;
	}

	@Override
	public void load(byte[] t) {
		// TODO Auto-generated method stub
		this.body = t;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (this.head==null||this.head.length==0)&&(this.body==null||this.body.length==0);
	}

	public C2CBuffer(byte[] head, byte[] body) {
		super();
		this.head = head;
		this.header = new C2CHeader(this.head);
		this.body = body;
	}
	
	public C2CBuffer(){
		super();
	}
	
	

}
