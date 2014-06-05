package jp.co.humane.logger;

public abstract class LogWriter
{
	private static String FORMAT = "%s %s %s\n";
	protected LogHeader m_Header = null;
	protected LogFooter m_Footer = null;
	
	/**
	 * ���O���o�͂���
	 * **/
	public abstract void write(String data);
	
	/**
	 * ���O�o�͕����𐶐�����
	 * **/
	protected String generate(String data)
	{
		return String.format(FORMAT,
				m_Header!=null?m_Header.write(null):"",
				data,
				m_Footer!=null?m_Footer.write(null):"");
	}
	public void setHeaderWriter(LogHeader h){m_Header = h;}
	public void setFooterWriter(LogFooter f){m_Footer = f;}
}
