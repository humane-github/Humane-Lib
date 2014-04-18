package jp.co.humane.dbutil.commonsIF;

import jp.co.humane.dbutil.exception.DBUtilException;

public class Database
{
	/**
	 * データベースにログインする
	 * 
	 * @param	hostname	DBホスト名
	 * @param	dbname		DB名
	 * @param	user		DB接続ユーザー名
	 * @param	password	DB接続ユーザーのパスワード
	 * **/
	public static void login(String hostname,String dbname,String user,String password) throws DBUtilException
	{
		//データソースを読み込む
		DataSourceLoader.load(hostname, dbname, user, password);
		//コネクションを開く
		ConnectionPool.open();
	}

	/**
	 * データベースからログアウトする
	 *
	 * **/
	public static void logout()
	{
		ConnectionPool.close();
	}

	/**
	 * データベースアクセス用オブジェクトを取得する
	 * **/
	public static DBAccessor getAccessor()
	{
		return new DBAccessor();
	}
}
