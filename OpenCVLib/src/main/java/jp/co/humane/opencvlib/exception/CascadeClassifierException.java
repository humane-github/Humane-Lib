package jp.co.humane.opencvlib.exception;

import jp.co.humane.exception.GeneralException;

public class CascadeClassifierException extends GeneralException
{
	public CascadeClassifierException(String errcode,Exception e)
	{
		super(errcode,e);
	}
}
