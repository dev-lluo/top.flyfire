package top.flyfire.degetation.code;


import top.flyfire.degetation.Structure;

@SuppressWarnings("rawtypes")
public abstract class Code <T extends Code> implements Structure<T> {

	protected final StringBuilder builder = new StringBuilder();
	
	public Code(){}
	
	public Code(String code){
		
	}
	
}