package top.flyfire.degetation.buffer;

import java.util.LinkedList;
import java.util.List;

import top.flyfire.degetation.Const;


public class BufferEngine<T> {
	
	private boolean isRunning = true;
	
	private List<IBuffer<T>> bufferQueue = new LinkedList<IBuffer<T>>();
	
	public void write(IBuffer<T> buffer){
		if(isRunning)
		synchronized (bufferQueue) {
			this.bufferQueue.add(buffer);
		}
	}
	
	public void write(IBuffer<T>...buffers){
		if(isRunning)
		synchronized (bufferQueue) {
			for(IBuffer<T> buffer : buffers)bufferQueue.add(buffer);
		}
	}
	
	public void write(List<IBuffer<T>> buffers){
		if(isRunning)
		synchronized (bufferQueue) {
			for(IBuffer<T> buffer : buffers)bufferQueue.add(buffer);
		}
	}
	
	public void read(IBuffer<T> buffer){
		synchronized (bufferQueue) {
			if(!bufferQueue.isEmpty()){
				buffer.load(bufferQueue.remove(0).unLoad());
			}else{
				try {
					bufferQueue.wait(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Const.CONSOLE.error(e);
				}
			}
		}
	}
	
	public void destroy(){
		isRunning = false;
		while(!bufferQueue.isEmpty())
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		this.bufferQueue = null;
	}
	
	public boolean isEmpty(){
		synchronized (bufferQueue) {
			return bufferQueue.isEmpty();
		}
	}

}
