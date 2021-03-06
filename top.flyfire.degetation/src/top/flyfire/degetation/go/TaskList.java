package top.flyfire.degetation.go;

import top.flyfire.degetation.go.Go.GoTask;
import top.flyfire.degetation.loop.Loop;
import top.flyfire.degetation.loop.Loop.LoopTask;
import top.flyfire.degetation.validator.Validator.Obj;

public class TaskList<T>{
	private KVTask<T>[] list;
	public TaskList(KVTask<T>...list) {
		// TODO Auto-generated constructor stub
		this.list = list;
	}
	protected void choose(final T key){
		Loop.run(list, new LoopTask<KVTask<T>>() {

			@Override
			public boolean run(KVTask<T> t, int index) {
				// TODO Auto-generated method stub
				if(Obj.isNull(key)&&Obj.isNull(t.key())){
					GoTask goTask = t.val();
					Go.to(Obj.notNull(goTask), goTask);
					return true;
				}else if(Obj.notNull(key)&&key.equals(t.key())){
					GoTask goTask = t.val();
					Go.to(Obj.notNull(goTask), goTask);
					return true;
				}
				return false;
			}

		});
	}
}
