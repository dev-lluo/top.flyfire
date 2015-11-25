package top.flyfire.degetation.buffer;

import java.util.LinkedList;
import java.util.List;

public class BufferEngine {
	
	private boolean isRunning = true;
	
	private List<IBuffer<?>> bufferQueue = new LinkedList<IBuffer<?>>();
	
	public void write(IBuffer<?> buffer){
		if(isRunning)
		synchronized (bufferQueue) {
			this.bufferQueue.add(buffer);
		}
	}
	
	public void write(IBuffer<?>[] buffers){
		if(isRunning)
		synchronized (bufferQueue) {
			for(IBuffer<?> buffer : buffers)bufferQueue.add(buffer);
		}
	}
	
	public void write(List<IBuffer<?>> buffers){
		if(isRunning)
		synchronized (bufferQueue) {
			for(IBuffer<?> buffer : buffers)bufferQueue.add(buffer);
		}
	}
	
	public IBuffer<?> read(){
		synchronized (bufferQueue) {
			while(bufferQueue.isEmpty())
				try {
					bufferQueue.wait(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return bufferQueue.remove(0);
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
