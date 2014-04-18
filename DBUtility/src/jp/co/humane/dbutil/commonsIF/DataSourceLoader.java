/*
 * 作成日： 2005/08/11
 */
package jp.co.humane.dbutil.commonsIF;

import javax.sql.DataSource;

import jp.co.humane.dbutil.exception.DBUtilException;

import org.postgresql.jdbc3.Jdbc3SimpleDataSource;

/**
 * DB接続情報を管理するクラス
 * DB接続情報は、設定情報管理コンポーネントを使用して
 * datasource.xmlファイルから読み込む
 * @author suzuki
 */
public class DataSourceLoader
{

	private static Jdbc3SimpleDataSource m_datasourcePG = null;
	
	/**
	 * DBとの接続を確立する
	 * 
	 * @param	hostname	DBホスト名
	 * @param	dbname		DB名
	 * @param	user		DB接続ユーザー名
	 * @param	password	DB接続ユーザーのログインパスワード
	 * 
	 * **/
	public static boolean load(String hostname,String dbname,String user,String password) throws DBUtilException
	{		
		m_datasourcePG = new Jdbc3SimpleDataSource();
		m_datasourcePG.setServerName(hostname);
		m_datasourcePG.setDatabaseName(dbname);
		m_datasourcePG.setUser(user);
		m_datasourcePG.setPassword(password);
		
		return true;
	}
	
	/**
	 * セッション確立中のデータソースを返す
	 * **/
	public static DataSource getDataSource()
	{
		return m_datasourcePG;
	}
}
