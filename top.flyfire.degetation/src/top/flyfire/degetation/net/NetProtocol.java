package top.flyfire.degetation.net;


import top.flyfire.degetation.protocol.Protocol;

public class NetProtocol implements Protocol ,Net {
	
	/**
	 * 内容类型
	 */
	public static final int CTYPE = 0;
	/**
	 * 加密类型
	 */
	public static final int ENC = 1;
	
	
	@Override
	public int bewriteStart() {
		// TODO Auto-generated method stub
		return DEFAULT_BEWRITE_START;
	}

	@Override
	public int bewriteStep() {
		// TODO Auto-generated method stub
		return DEFAULT_BEWRITE_STEP;
	}

	@Override
	public byte[] protocol(byte[] by) {
		// TODO Auto-generated method stub
		byte[] ptcl = new byte[this.bewriteStep()];
		System.arraycopy(by, this.bewriteStart() , ptcl	, 0, ptcl.length);
		return ptcl;
	}

	@Override
	public byte[] content(byte[] by) {
		// TODO Auto-generated method stub
		byte[] content = new byte[by.length-this.bewriteStep()];
		System.arraycopy(by, this.bewriteStep() , content	, 0, content.length);
		return content;
	}

	@Override
	public byte protocol(byte[] by, int index) {
		// TODO Auto-generated method stub
		return by[index];
	}

}
