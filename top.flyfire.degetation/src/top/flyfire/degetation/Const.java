package top.flyfire.degetation;

import java.net.*;

public interface Const {
	/**
	 * 默认缓冲大小
	 */
	int BUFFER_SIZE = 1024;
	/**
	 * 默认池大小
	 */
	int POOL_SIZE = 10;
	/**
	 * URL分隔符
	 */
	char URL_SEPARATOR= '/';
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
	 * 版权
	 */
	String COPYRIGHT = "flyfire.top";
	
	
}