package jp.co.humane.dbutil;

/**
 * PostgreSQL�ڑ��p��DB�R�l�N�^�[
 * **/
public class PostgreConnector extends DBConnector
{
	// DB�h���C�o������
	private final static String DRIVER = "org.postgresql.Driver";

	// DB�ڑ�URL
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
