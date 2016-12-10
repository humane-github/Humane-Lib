package jp.co.humane.dbutil;

/**
 * ファイルdatasource.xmlファイルに設定された情報をメンバ変数に持つクラス
 */
public class DataSourceData
{

	/** DB論理名 **/
	private String name = null;
	
	/** DBホスト名 **/
	private String hostname = null;
	
	/** DB名 **/
	private String dbname = null;
	
	/** DBログインユーザー名 **/
	private String user = null;
	
	/** DBログインパスワード **/
	private String password = null;
		
	
//-----------------------------------------------------------------------------------------	
	
	/**
	 * コンストラクタ
	 * **/
	public DataSourceData()
	{
	}

	/**
	 * ホスト名を取得する
	 * 
	 * @return String
	 */
	public String getHostname() {
		return hostname;
	}
	/**
	 * ホスト名を設定する
	 * 
	 * @param host ホスト名
	 */
	public void setHostname(String host) {
		this.hostname = host;
	}
	/**
	 * DB名を取得する
	 * 
	 * @return String
	 */
	public String getDbname() {
		return dbname;
	}
	/**
	 * DB名を設定する
	 * 
	 * @param dbname DB名
	 */
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	/**
	 * 論理DB名を取得する
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * 論理DB名を設定する
	 * 
	 * @param name 論理DB名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * パスワードを取得する
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * パスワードを設定する
	 * 
	 * @param password パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * ユーザー名を取得する
	 * 
	 * @return String
	 */
	public String getUser() {
		return user;
	}
	/**
	 * ユーザー名を設定する
	 * 
	 * @param user ユーザー名
	 */
	public void setUser(String user) {
		this.user = user;
	}

	public String toString()
	{
		return "name="+this.getName()+
				" : user="+this.getUser() +
				" : password="+this.getPassword() +
				" : host="+this.getHostname() +
				" : dbname="+this.getDbname();
	}
}
