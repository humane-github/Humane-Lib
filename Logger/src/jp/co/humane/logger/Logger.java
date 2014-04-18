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
	//���O�o�͂̎��
	private OUTPUT m_outputType = OUTPUT.CONSOLE_OUT;
	//���O�o�͐ݒ�t�@�C���̃p�X
	private String m_confPath = null;
	//�ݒ�t�@�C��
	private LoggerConfig m_config = null;
	//���O�o��
	private LogWriter m_logWriter = null;
	//���O�̃w�b�_�[�����I�u�W�F�N�g
	private LogHeader m_header = null;
	//���O�̃t�b�^�[�����I�u�W�F�N�g
	private LogFooter m_footer = null;
	
	/**
	 * Logger�C���X�^���X���擾����
	 * **/
	public static Logger create()
	{
		return new Logger(null);
	}
	/**
	 * Logger�C���X�^���X���擾����
	 * **/
	public static Logger create(String confPath)
	{
		return new Logger(confPath);		
	}
	
	/**
	 * �R���X�g���N�^
	 * 
	 * @param	confPath	���O�o�͐ݒ�t�@�C���̃p�X
	 * @param	type		���O�̏o�͕���
	 * **/
	private Logger(String confPath)
	{
		//�ݒ�t�@�C���ǂݍ���
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
		
		//�w�b�_�[�ƃt�b�^�[��ݒ�
		m_logWriter.setHeaderWriter(new StacktraceHeader());
		m_logWriter.setFooterWriter(null);
	}
	
	/**
	 * ���O���o�͂���
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
