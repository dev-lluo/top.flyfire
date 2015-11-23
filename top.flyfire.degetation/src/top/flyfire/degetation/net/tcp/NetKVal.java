package top.flyfire.degetation.net.tcp;

import top.flyfire.degetation.KVal;
import top.flyfire.degetation.net.NetProtocol;

public class NetKVal implements KVal<byte[], byte[]> {
	
	private final byte[] key;
	private final byte[] val;
	private final NetProtocol protocol;
	
	public NetKVal(byte[] key,byte[] val,NetProtocol protocol) {
		// TODO Auto-generated constructor stub
		this.key = key;
		this.val = val;
		this.protocol = protocol;
	}
	
	@Override
	public byte[] key() {
		// TODO Auto-generated method stub
		return this.key;
	}

	@Override
	public byte[] val() {
		// TODO Auto-generated method stub
		return this.val;
	}
	
	public NetProtocol getProtocol(){
		return this.protocol;
	}

}