package jp.co.humane.logger;

public abstract class LogWriter
{
	private static String FORMAT = "%s %s %s\n";
	protected LogHeader m_Header = null;
	protected LogFooter m_Footer = null;
	
	/**
	 * ログを出力する
	 * **/
	public abstract void write(String data);
	
	/**
	 * ログ出力文字を生成する
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
