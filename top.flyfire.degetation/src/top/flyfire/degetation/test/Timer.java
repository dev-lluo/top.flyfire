package top.flyfire.degetation.test;

public class Timer {
	public static final void run(TimerTask task){
		long startTime = System.currentTimeMillis();
		task.run();
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-startTime);
	}
	public interface TimerTask{
		void run();
	}
}
