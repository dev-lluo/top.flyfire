package top.flyfire.degetation.stream;

import java.io.IOException;
import java.io.InputStream;

import top.flyfire.degetation.Const;

public class Stream implements Const{
	public static String toString(InputStream ins){
		StringBuilder builder = new StringBuilder();
		byte[] by = new byte[BUFFER_SIZE];
		try {
			for(int len = ins.read(by);len>0;len = ins.read(by)){
				builder.append(new String(by,0,len));
			}
			return builder.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	
	public static void run(InputStream ins,Task task){
		byte[] by = new byte[BUFFER_SIZE];
		try {
			for(int len = ins.read(by);len>0;len = ins.read(by)){
				byte[] real = new byte[len];
				System.arraycopy(by, 0, real, 0, len);
				if(task.exec(real, len))return;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	public interface Task{
		boolean exec(byte[] by ,int len)throws IOException;
	}
}
