package jp.co.humane.dbutil.exception;

public class DBUtilException extends Exception
{
	//エラーコード
	private String m_errorCode = "";
	
	/**
	 * コンストラクタ
	 * 
	 * @param	c	エラーコード
	 * **/
	public DBUtilException(String c)
	{
		super();
		m_errorCode = c;
	}
}
