package top.flyfire.degetation;

import top.flyfire.degetation.Go.GoTask;

public class Loop {
	public interface LoopTask<T>{
		int BREAK = -1;
		int NEXT = 0;
		int CONTINUE = 1;
		void run(T t, int index);
		int needNext(T t,int index);
	}
	public static <T> void run(final T[] target,final LoopTask<T> task){
		Go.to(Validator.Obj.notNull(target)&&Validator.Obj.notNull(task), new GoTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 0, flag=LoopTask.BREAK;i<target.length;i++){
					flag=task.needNext(target[i], i);
					if(flag==LoopTask.NEXT){
						task.run(target[i], i);
					}else if(flag==LoopTask.CONTINUE){
						continue;
					}else if(flag==LoopTask.BREAK){
						break;
					}
				}
			}
		});
	}
}
