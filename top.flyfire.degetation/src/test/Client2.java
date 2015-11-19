package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Client2 {
	public static final String IP_ADDR = "localhost";//服务器地址 
	public static final int PORT = 10001;//服务器端口号 
	

    public static void main(String[] args) throws UnknownHostException, IOException {  
        System.out.println("客户端启动...");  
        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n"); 
        Socket socket = new Socket(IP_ADDR, PORT);;
        while (true) {  
        	
        	try {
        		//读取服务器端数据   
	            InputStream input = socket.getInputStream();
	            //向服务器端发送数据  
	            OutputStream out = socket.getOutputStream();  
	            System.out.print("请输入: \t");  
	            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();  
	            byte[] content = str.getBytes("UTF-8");
	            System.out.println(content.length);
	            byte[] head = new byte[6];
	            System.out.println(head.length);
	            head[0] = 0;
	            byte[] data = new byte[head.length+content.length];
	            System.out.println(data.length);
	            System.arraycopy(head, 0, data, 0, head.length);
	            System.out.println(data.toString());
	            System.arraycopy(content, 0, data, head.length, content.length);
	            System.out.println(Arrays.toString(data));
	            out.write(data);
	            out.flush();
        	} catch (Exception e) {
        		e.printStackTrace();
        		System.out.println("�ͻ����쳣:" + e.getMessage()); 
        	} 
        }  
        
    }  
}