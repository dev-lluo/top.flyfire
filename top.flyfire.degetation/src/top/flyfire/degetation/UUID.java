package top.flyfire.degetation;

public final class UUID {
	private UUID(){}
	public final static UUID $ = new UUID();
	private final  char[] chars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public final String create(int len ,int radix){
	    char[] uuid = null;
	    radix = radix==0?chars.length:radix;
	 
	    if (len>0) {
	      // Compact form
	      uuid = new char[len];
	      for (int i = 0; i < len; i++) uuid[i] = chars[(int) (Math.random()*radix)];
	    } else {
	      // rfc4122, version 4 form
	      byte r;
	      len=36;
	      uuid = new char[len];
	      // rfc4122 requires these characters
	      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
	      uuid[14] = '4';
	 
	      // Fill in random data.  At i==19 set the high bits of clock sequence as
	      // per rfc4122, sec. 4.1.5
	      for (int i = 0; i < 36; i++) {
	        if (uuid[i]=='\0') {
	          r =  (byte) (Math.random()*16);
	          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	        }
	      }
	    }
	 
	    return toString(uuid);
	}
	
	public final String create(){
		return create(32,62);
	}
	public final String createGUID(){
		return UUID.$.create(0,62);
	}
	private final String toString(char[] str){
		if(str!=null){
			StringBuffer buffer = new StringBuffer();
			for(int i = 0;i<str.length;i++){
				buffer.append(str[i]);
			}
			return buffer.toString();
		}else{
			return "";
		}
	}

}
