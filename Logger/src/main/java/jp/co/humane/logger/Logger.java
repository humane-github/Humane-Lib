package jp.co.humane.logger;

import java.io.IOException;


public class Logger
{
	private static String CONF_OUTPUT = "OUTPUT";
	private static String CONF_DIRECTORY = "DIRECTORY";
	private static String FORMAT = "%s %s %s";
	public static enum OUTPUT
	{
		LOGFILE,
		CONSOLE_OUT		
	}
	//ログ出力の種類
	private OUTPUT m_outputType = OUTPUT.CONSOLE_OUT;
	//ログ出力設定ファイルのパス
	private String m_confPath = null;
	//設定ファイル
	private LoggerConfig m_config = null;
	//ログ出力
	private LogWriter m_logWriter = null;
	//ログのヘッダー生成オブジェクト
	private LogHeader m_header = null;
	//ログのフッター生成オブジェクト
	private LogFooter m_footer = null;
	
	/**
	 * Loggerインスタンスを取得する
	 * **/
	public static Logger create()
	{
		return new Logger(null);
	}
	/**
	 * Loggerインスタンスを取得する
	 * **/
	public static Logger create(String confPath)
	{
		return new Logger(confPath);		
	}
	
	/**
	 * コンストラクタ
	 * 
	 * @param	confPath	ログ出力設定ファイルのパス
	 * @param	type		ログの出力方式
	 * **/
	private Logger(String confPath)
	{
		//設定ファイル読み込み
		m_confPath = confPath;
		if( confPath != null && confPath.length() > 0 )
		{
			m_config = new LoggerConfig();
			try {
				m_config.load(confPath);
				m_outputType = OUTPUT.valueOf(m_config.getString(CONF_OUTPUT));
			} catch (IOException e) {
				System.out.println(String.format("Logger_config(%s) is not already exists",confPath));
				m_outputType = OUTPUT.CONSOLE_OUT;
				e.printStackTrace();
			}
		}
		
		switch(m_outputType)
		{
		case CONSOLE_OUT:
			m_logWriter = new ConsoleLogWriter();
			break;
		case LOGFILE:
			m_logWriter = new FileLogWriter(m_config);
			break; 
		}
		
		//ヘッダーとフッターを設定
		m_logWriter.setHeaderWriter(new StacktraceHeader());
		m_logWriter.setFooterWriter(null);
	}
	
	/**
	 * ログを出力する
	 * **/
	public void trace(String s)
	{
		m_logWriter.write(s);
	}
	public void trace(String format,Object...args)
	{
		m_logWriter.write(String.format(format, args));
	}
	
	public void setHeader(LogHeader h){m_header = h;}
	public void setFooter(LogFooter f){m_footer = f;}
	
	public static void main(String[] args)
	{
		Logger.create().trace("aaaa");
	}
}
