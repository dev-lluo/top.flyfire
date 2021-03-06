package top.flyfire.degetation.go;

import top.flyfire.degetation.validator.Validator;

public final class Go implements Validator {
	public interface GoTask{
		void run();
	}
	public interface SimpleGoValidator extends GoValidator<Boolean>{
		Boolean validate();
	}
	public interface  GoValidator<T>{
		T validate();
	}
	public static final void to(boolean flag,GoTask task){
		if(flag){
			task.run();
		}
	}
	public static final void to(SimpleGoValidator validator,GoTask task){
		if(validator.validate()){
			task.run();
		}
	}
	public static final <T> void to(GoValidator<T> validator , TaskList<T> list){
		list.choose(validator.validate());
	}
	public static final <T> void to(T t,TaskList<T> list){
		list.choose(t);
	}
	public static final void notNullTo(Object obj,GoTask task){
		Go.to(Obj.notNull(obj),task);
	}
	public static final void nullTo(Object obj,GoTask task){
		Go.to(Obj.isNull(obj),task);
	}
}
