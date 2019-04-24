package logStuff;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class MyLoggerle {
	private PrintStream out;
	public MyLoggerle(String dataLocation){
		try {
			out = new PrintStream(new FileOutputStream(dataLocation));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void logLine(String s){
		out.println(s);
		System.out.println(s);
	}
	public void log(String s){
		out.print(s);
		System.out.print(s);
	}
}
