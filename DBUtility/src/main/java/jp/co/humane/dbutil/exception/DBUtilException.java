package jp.co.humane.dbutil.exception;

public class DBUtilException extends Exception
{
	//�G���[�R�[�h
	private String m_errorCode = "";
	
	/**
	 * �R���X�g���N�^
	 * 
	 * @param	c	�G���[�R�[�h
	 * **/
	public DBUtilException(String c)
	{
		super();
		m_errorCode = c;
	}
}
