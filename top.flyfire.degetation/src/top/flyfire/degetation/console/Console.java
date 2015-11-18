package top.flyfire.degetation.console;

import top.flyfire.degetation.go.Go;
import top.flyfire.degetation.go.Go.GoTask;

public class Console {
	private static IConsole console = null;
	static{
		Go.nullTo(console, new GoTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				console = new DefaultConsole();
			}
		});
	}
	
	public static IConsole getConsole() {
		return Console.console;
	}
	
}
