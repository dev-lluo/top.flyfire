package top.flyfire.degetation.net.tcp.c2c;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import top.flyfire.degetation.Const;
import top.flyfire.degetation.json.Json;

public class C2CHeader {
	
	public final static String FROM_SERVER = "0-0-0-0";
	
	public final static class CType{
		public final static String TEXT = "TEXT";
		
		public final static String BIN = "BIN";
		
	}
	
	public final static class Tag{
		public final static String TARGET = "target";
		
		public final static String TIMESTAMP = "timestamp";
		
		public final static String CONTENTTYPE = "contentType";
	}
	
	private String target;
	
	private String timestamp;
	
	private String contentType;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public C2CHeader(String target, String timestamp, String contentType) {
		super();
		this.target = target;
		this.timestamp = timestamp;
		this.contentType = contentType;
	}
	
	@SuppressWarnings("rawtypes")
	public C2CHeader(byte[] by){
		try {
			String str = new String(by, "UTF-8");
			Map map = Json.JsO.convert(str);
			this.target = map.get(Tag.TARGET).toString();
			this.timestamp = map.get(Tag.TIMESTAMP).toString();
			this.contentType = map.get(Tag.CONTENTTYPE).toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Const.CONSOLE.error(e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public C2CHeader(Map map){
		this(map.get(Tag.TARGET).toString(),map.get(Tag.TIMESTAMP).toString(),map.get(Tag.CONTENTTYPE).toString());
	}
	
	public byte[] serialize(){
		try {
			return Json.ObJ.convert(this).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Const.CONSOLE.error(e);
			return null;
		}
	}

	
	
}
