package top.flyfire.degetation.json;

@SuppressWarnings("serial")
public class UnknowJTypeException extends RuntimeException {

	public UnknowJTypeException(String json) {
		super("UnknowJTypeException#"+System.currentTimeMillis()+"#"+json);
		// TODO Auto-generated constructor stub
	}
}