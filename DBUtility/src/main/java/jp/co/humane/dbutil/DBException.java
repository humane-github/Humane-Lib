package jp.co.humane.dbutil;

public class DBException extends Exception
{
	// コンストラクタ
	public DBException(){}

	public DBException( Throwable e ){super(e);}
	public DBException( String msg,Throwable e ){super(msg,e);}
	public DBException( String msg ){super(msg);}
}
