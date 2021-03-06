package top.flyfire.degetation.console;

public interface IConsole {
	
	int SYSTEM = 0;
	int LOG4J = 1;
	
	int DEFAULT = SYSTEM;
	
	void log(Object obj);
	void warn(Object obj);
	void error(Object obj);
	void info(Object obj);
	
	void log(Throwable e);
	void warn(Throwable e);
	void error(Throwable e);
	void info(Throwable e);
	
	void log(String msg);
	void warn(String msg);
	void error(String msg);
	void info(String msg);
}
