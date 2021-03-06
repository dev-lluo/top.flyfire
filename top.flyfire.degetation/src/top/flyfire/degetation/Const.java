package top.flyfire.degetation;

import java.net.*;

import top.flyfire.degetation.console.Console;
import top.flyfire.degetation.console.IConsole;

public interface Const {
	/**
	 * 控制台
	 */
	IConsole CONSOLE = Console.getConsole();
	/**
	 * 默认缓冲大小
	 */
	int BUFFER_SIZE = 1024;
	/**
	 * 默认池大小
	 */
	int POOL_SIZE = 10;
	/**
	 * @符号
	 */
	char AT = '@';
	String AT$ ="@";
	/**
	 * URL分隔符
	 */
	char URL_SEPARATOR= '/';
	String URL_SEPARATOR$= "/";
	/**
	 * 包分隔符
	 */
	char PCKG_SEPARATOR = '.';
	String PCKG_SEPARATOR$ = ".";
	/**
	 * 文件内联分隔符
	 */
	char IN_SEPARATOR = '!';
	String IN_SEPARATOR$ = "!";
	/**
	 * 当前线程的类加载器
	 */
	ClassLoader LOADER = Thread.currentThread().getContextClassLoader();
	/**
	 * 当前运行URL
	 */
	URL URL = Const.LOADER.getResource("//");
	/**
	 * 当前运行目录
	 */
	String PATH = Const.URL.getPath();
	/**
	 * ON
	 */
	boolean ON = true;
	/**
	 * OFF
	 */
	boolean OFF = false;
	/**
	 * 版权
	 */
	String COPYRIGHT = "flyfire.top";
	
	
	public interface Protocol{
		String HTTP = "http";
		String FILE = "file";
		String JAR = "jar";
	}
	
	public interface Suffix{
		String JAR = ".jar";
		String TXT = ".txt";
		String CLASS = ".class";
		String JAVA = ".java";
		String PROPERTIES = ".properties";
		String XML = ".xml";
		String JSP = ".jsp";
		String EXE = ".exe";
		String BAT = ".bat";
		String SH = ".sh";
	}
	
	
}
