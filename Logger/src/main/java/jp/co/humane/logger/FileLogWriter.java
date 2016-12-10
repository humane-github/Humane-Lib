package jp.co.humane.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogWriter extends LogWriter
{	
	private static String CONF_DIRECTORY = "DIRECTORY";
	private LoggerConfig m_config = null;
	private File m_log = null;
	private FileWriter m_fileWriter = null;
	/**
	 * コンストラクタ
	 * 
	 * @param	config	ログ出力設定ファイルオブジェクト
	 * **/
	public FileLogWriter(LoggerConfig config)
	{
		m_config = config;
	}
	
	public void write(String data)
	{
		try
		{
			if( m_log == null )
			{
				//ファイル名作成
				String filename = m_config.getString("FILENAME");
				filename += dateString();
				//ファイルを作る
				m_log = new File(m_config.getString(CONF_DIRECTORY)+File.separator+filename+".log");
				m_fileWriter = new FileWriter(m_log,true);
			}
			m_fileWriter.write(generate(data));
			m_fileWriter.flush();
		}
		catch( IOException ex )
		{
			ex.printStackTrace();
		}
	}
	
	private String dateString()
	{
		// yyyy/mm/ddでフォーマット
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(new Date());
	}	
}
