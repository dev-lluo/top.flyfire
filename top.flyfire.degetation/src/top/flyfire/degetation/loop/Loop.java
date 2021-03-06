package top.flyfire.degetation.loop;

import java.util.Collection;
import java.util.Iterator;

import top.flyfire.degetation.go.Go;
import top.flyfire.degetation.go.Go.GoTask;
import top.flyfire.degetation.validator.Validator;

public class Loop implements Validator{
	public interface LoopTask<T>{
		boolean run(T t, int index);
	}
	public final static <T> void run(final T[] target,final LoopTask<T> task){
		Go.to(Obj.notNull(target)&&Obj.notNull(task), new GoTask() {
			
			@Override
			public final void run() {
				// TODO Auto-generated method stub
				for(int i = 0;i<target.length;i++){
					if(task.run(target[i], i))return;
				}
			}
		});
	}
	public final static <T> void run(final Collection<T> target,final LoopTask<T> task){
		Go.to(Obj.notNull(target)&&Obj.notNull(task), new GoTask() {
			
			@Override
			public final void run() {
				// TODO Auto-generated method stub
				int index = 0;
				for(Iterator<T> i = target.iterator();i.hasNext();index++){
					if(task.run(i.next(), index))return;
				}
			}
		});
	}
}
