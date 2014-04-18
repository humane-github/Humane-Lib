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
	 * �R���X�g���N�^
	 * 
	 * @param	config	���O�o�͐ݒ�t�@�C���I�u�W�F�N�g
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
				//�t�@�C�����쐬
				String filename = m_config.getString("FILENAME");
				filename += dateString();
				//�t�@�C�������
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
		// yyyy/mm/dd�Ńt�H�[�}�b�g
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(new Date());
	}	
}
