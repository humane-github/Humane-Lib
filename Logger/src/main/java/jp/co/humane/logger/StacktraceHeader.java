package jp.co.humane.logger;

/**
 * 呼び出し元クラスの情報をヘッダーに付加する
 * **/
public class StacktraceHeader implements LogHeader
{
	private static int CURRENT  = 6;
	public String write(Object arg)
	{
		String format ="%s.%s(%s:%d)";
		StackTraceElement[] elems = Thread.currentThread().getStackTrace();
		if( elems == null || elems.length < CURRENT ){return "";}
		return String.format(format,elems[CURRENT-1].getClassName(),
								elems[CURRENT-1].getMethodName(),
								elems[CURRENT-1].getFileName(),
								elems[CURRENT-1].getLineNumber());
	}
}
