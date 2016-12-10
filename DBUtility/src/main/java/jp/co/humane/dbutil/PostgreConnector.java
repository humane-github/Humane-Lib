package jp.co.humane.dbutil;

/**
 * PostgreSQL接続用のDBコネクター
 * **/
public class PostgreConnector extends DBConnector
{
	// DBドライバ文字列
	private final static String DRIVER = "org.postgresql.Driver";

	// DB接続URL
	private final static String URL = "jdbc:postgresql:%s?user=%s&password=%s";

	@Override
	public String getDriver()
	{
		return DRIVER;
	}

	@Override
	public String getURL( ConnectionInfo info )
	{
		String url = String.format(URL, info.getDbname(),info.getUser(),info.getPassword());
		return url;
	}

}
