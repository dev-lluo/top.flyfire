package top.flyfire.degetation.util;

import java.net.URI;

public class ProtocolUtil {
	public static String unProtocol(URI uri){
		String temp = uri.toString();
		return temp.replaceFirst("^([a-zA-Z]+\\:)+/{1,2}", "");
	}
	
}
