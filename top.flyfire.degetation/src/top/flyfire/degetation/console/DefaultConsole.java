package top.flyfire.degetation.console;

import top.flyfire.degetation.Const;
import top.flyfire.degetation.json.Json;

public class DefaultConsole implements IConsole,Json,Const{
	
	@Override
	public void log(Object obj) {
		// TODO Auto-generated method stub
		System.out.println("LOG￥"+System.currentTimeMillis()+" : "+ObJ.convert(obj));
	}

	@Override
	public void warn(Object obj) {
		// TODO Auto-generated method stub
		System.err.println("WARN$"+System.currentTimeMillis()+" : "+ObJ.convert(obj));
	}

	@Override
	public void error(Object obj) {
		// TODO Auto-generated method stub
		System.err.println("ERROR$"+System.currentTimeMillis()+" : "+ObJ.convert(obj));
	}

	@Override
	public void info(Object obj) {
		// TODO Auto-generated method stub
		System.out.println("INFO$"+System.currentTimeMillis()+" : "+ObJ.convert(obj));
	}

	@Override
	public void log(Throwable e) {
		// TODO Auto-generated method stub
		System.out.println("LOG$"+System.currentTimeMillis()+" : "+e.getMessage());
	}

	@Override
	public void warn(Throwable e) {
		// TODO Auto-generated method stub
		System.err.println("WARN$"+System.currentTimeMillis()+" : "+e.getMessage());
	}

	@Override
	public void error(Throwable e) {
		// TODO Auto-generated method stub
		System.err.println("ERROR$"+System.currentTimeMillis()+" : "+e.getMessage());
	}

	@Override
	public void info(Throwable e) {
		// TODO Auto-generated method stub
		System.out.println("INFO$"+System.currentTimeMillis()+" : "+e.getMessage());
	}

	@Override
	public void log(String msg) {
		// TODO Auto-generated method stub
		System.out.println("LOG$"+System.currentTimeMillis()+" : "+msg);
	}

	@Override
	public void warn(String msg) {
		// TODO Auto-generated method stub
		System.err.println("WARN$"+System.currentTimeMillis()+" : "+msg);
	}

	@Override
	public void error(String msg) {
		// TODO Auto-generated method stub
		System.err.println("ERROR$"+System.currentTimeMillis()+" : "+msg);
	}

	@Override
	public void info(String msg) {
		// TODO Auto-generated method stub
		System.out.println("INFO$"+System.currentTimeMillis()+" : "+msg);
	}

}
