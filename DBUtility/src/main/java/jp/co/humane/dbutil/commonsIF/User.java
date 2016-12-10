package jp.co.humane.dbutil.commonsIF;

/**
 * アプリケーションを利用しているユーザー情報
 * **/
public class User
{
	// ユーザー名
	private String name = null;
	// パスワード
	private String password = null;

	/**
	 * コンストラクタ
	 * **/
	public User( String n , String p )
	{
		name = n;
		password = p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
