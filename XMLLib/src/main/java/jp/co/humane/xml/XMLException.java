package jp.co.humane.xml;

import jp.co.humane.exception.GeneralException;

public class XMLException extends GeneralException
{
	/**
	 * �R���X�g���N�^
	 *
	 * @param�@code		�G���[�R�[�h
	 * **/
	public XMLException( String code )
	{
		super(code);
	}

	/**
	 * �R���X�g���N�^
	 *
	 * @param�@code		�G���[�R�[�h
	 * @param	e		��O��޼ު��
	 * **/
	public XMLException ( String code,Exception e)
	{
		super(code,e);
	}
}
