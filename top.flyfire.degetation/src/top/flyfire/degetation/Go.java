package top.flyfire.degetation;

import top.flyfire.degetation.Loop.LoopTask;

public final class Go {
	public interface GoTask{
		void run();
	}
	public class TaskList<T>{
		private KVal<T, GoTask>[] list;
		public TaskList(KVal<T, GoTask>...list) {
			// TODO Auto-generated constructor stub
			this.list = list;
		}
		public void choose(final T key){
			Loop.run(list, new LoopTask<KVal<T, GoTask>>() {

				@Override
				public void run(KVal<T, GoTask> t, int index) {
					// TODO Auto-generated method stub
					if(Validator.Obj.isNull(key)&&Validator.Obj.isNull(t.key())){
						GoTask goTask = t.val();
						Go.to(Validator.Obj.notNull(goTask), goTask);
					}else if(Validator.Obj.notNull(key)&&key.equals(t.key())){
						GoTask goTask = t.val();
						Go.to(Validator.Obj.notNull(goTask), goTask);
					}
				}

				@Override
				public int needNext(KVal<T,GoTask> t, int index) {
					// TODO Auto-generated method stub
					return 0;
				}

				
			});
		}
	}
	public abstract class SimpleGoValidator extends GoValidator<Boolean>{
		abstract Boolean validate();
	}
	public abstract class GoValidator<T>{
		abstract T validate();
	}
	public static void to(boolean flag,GoTask task){
		if(flag){
			task.run();
		}
	}
	public static void to(SimpleGoValidator validator,GoTask task){
		if(validator.validate()){
			task.run();
		}
	}
	public static <T> void to(GoValidator<T> validator , TaskList<T> list){
		
	}
}