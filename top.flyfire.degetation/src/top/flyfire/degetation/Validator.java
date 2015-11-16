package top.flyfire.degetation;

public final class Validator {
	public final static class Obj{
		public static boolean notNull(Object obj){
			return !isNull(obj);
		}
		
		public static boolean isNull(Object obj){
			return obj == null;
		}
		
	}
	
	public final static class Str{
		public static boolean isNull(String str){
			return Obj.isNull(str);
		}
		public static boolean notNull(String str){
			return Obj.notNull(str);
		}
		public static boolean isEmpty(String str){
			return Obj.isNull(str)||str.length()<=0;
		}
		public static boolean notEmpty(String str){
			return !notNull(str);
		}
	}
}
