package jp.co.humane.xml;

import jp.co.humane.exception.GeneralException;

public class XMLException extends GeneralException
{
	/**
	 * コンストラクタ
	 *
	 * @param　code		エラーコード
	 * **/
	public XMLException( String code )
	{
		super(code);
	}

	/**
	 * コンストラクタ
	 *
	 * @param　code		エラーコード
	 * @param	e		例外ｵﾌﾞｼﾞｪｸﾄ
	 * **/
	public XMLException ( String code,Exception e)
	{
		super(code,e);
	}
}
