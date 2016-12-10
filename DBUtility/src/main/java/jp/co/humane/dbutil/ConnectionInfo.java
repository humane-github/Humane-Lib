package jp.co.humane.dbutil;

/**
 * DB接続に必要な情報を持つオブジェクト
 * **/
public class ConnectionInfo
{
	// DB名
	private String dbname = null;
	// ユーザー名
	private String user = null;
	// パスワード
	private String password = null;

	// コンストラクタ
	public ConnectionInfo(String dbname, String user, String password) {
		super();
		this.dbname = dbname;
		this.user = user;
		this.password = password;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}




}
