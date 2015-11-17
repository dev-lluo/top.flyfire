package top.flyfire.degetation.util;

public class StringUtil {
	public final static String upperFirst(String str){
		StringBuffer buffer = new StringBuffer(str);
		return buffer.replace(0, 1, buffer.substring(0, 1).toUpperCase()).toString();
	}
	
}
