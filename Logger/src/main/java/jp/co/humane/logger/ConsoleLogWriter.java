package jp.co.humane.logger;

public class ConsoleLogWriter extends LogWriter
{
	public void write(String data)
	{
		System.out.println(generate(data));
	}
}
