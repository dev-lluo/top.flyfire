package top.flyfire.degetation.validator;

public interface Validator {
	public final static class Obj{
		public static final boolean notNull(Object obj){
			return !isNull(obj);
		}
		
		public static final boolean isNull(Object obj){
			return obj == null;
		}
		
	}
	
	public final static class Str{
		public static final boolean isNull(String str){
			return Obj.isNull(str);
		}
		public static final boolean notNull(String str){
			return Obj.notNull(str);
		}
		public static final boolean isEmpty(String str){
			return Obj.isNull(str)||str.length()<=0;
		}
		public static final boolean notEmpty(String str){
			return !notNull(str);
		}
	}
}
