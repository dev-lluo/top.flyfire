package top.flyfire.degetation.thread;

import top.flyfire.degetation.Const;

public abstract class RunTask <T> implements Runnable ,Const{
	
	protected T result;
	
	protected boolean needWait = true;
	
	@Override
	public final void run() {
		// TODO Auto-generated method stub
		this.doBefore();
		this.exec();
		this.doAfter();
	}
	
	protected void info(String info){
		CONSOLE.info(info);
	}
	
	protected void doBefore(){
		CONSOLE.info("before=======================================");
	}
	
	protected void doAfter() {
		CONSOLE.info("after========================================");
		this.needWait = false;
	}
	
	protected abstract void exec();
	
	public T result(){
		synchronized (this) {
			while(needWait);
			return result;
		}
	}
	

}
