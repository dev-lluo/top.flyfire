package top.flyfire.degetation.thread;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import top.flyfire.degetation.Const;

public class ThreadMgr implements Const {
	
	private ThreadMgr(){
		System.exit(0);
	}
	
	private static Thread monitor = new Thread(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(monitorNeedRunning){
				
			}
		}
		
	};
	private static boolean monitorNeedRunning = false;
	static{
		monitor.start();
	}
	
	private static Map<String, ThreadPool> poolStore = new HashMap<String, ThreadPool>();
	
	private static volatile boolean isRunning = true;
	
	public static ThreadPool live() {
		if(isRunning){
			synchronized (poolStore) {
				int id = poolStore.size();
				String name = id+"."+COPYRIGHT;
				ThreadPool pool = new ThreadPool(name);
				poolStore.put(name, pool);
				return pool;
			}
		}
		return null;
	}
	
	public static ThreadPool live(int poolSize) {
		if(isRunning){
			if (poolSize <= 0)
				poolSize = POOL_SIZE;
			synchronized (poolStore) {
				int id = poolStore.size();
				String name = id+"."+COPYRIGHT;
				ThreadPool pool = new ThreadPool(name,poolSize);
				poolStore.put(name, pool);
				return pool;
			}
		}
		return null;
	}
	
	public static ThreadPool live(String name){
		if(isRunning){
			synchronized (poolStore) {
				if(poolStore.containsKey(name)){
					return poolStore.get(name);
				}else{
					ThreadPool pool = new ThreadPool(name,POOL_SIZE);
					poolStore.put(name, pool);
					return pool;
				}
				
			}
		}
		return null;
	}
	
	public static ThreadPool live(String name,int poolSize) {
		if(isRunning){
			if (poolSize <= 0)
				poolSize = POOL_SIZE;
			synchronized (poolStore) {
				if(poolStore.containsKey(name)){
					throw new RuntimeException("["+name+"]已经被使用，或该线程池已经被创建！！！");
				}else{
					ThreadPool pool = new ThreadPool(name,poolSize);
					poolStore.put(name, pool);
					return pool;
				}
				
			}
		}
		return null;
	}
	
	public static void die(){
		ThreadMgr.destory();
	}
	
	public static void die(String name){
		synchronized (poolStore) {
			if(poolStore.containsKey(name)){
				ThreadPool pool = poolStore.remove(name);
				pool.destroy();
			}
		}
	}
	
	public static void destory(){
		monitorNeedRunning = false;
		isRunning = false;
		synchronized (poolStore) {
			for(Iterator<Entry<String, ThreadPool>> i = poolStore.entrySet().iterator();i.hasNext();){
				Entry<String, ThreadPool> entry = i.next();
				entry.getValue().destroy();
				CONSOLE.info("destory["+entry.getKey()+"]");
				i.remove();
			}
			poolStore = null;
		}
		monitor = null;
	}

}
