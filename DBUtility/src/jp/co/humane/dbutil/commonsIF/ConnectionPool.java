package jp.co.humane.dbutil.commonsIF;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import jp.co.humane.dbutil.exception.DBUtilException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * コネクションを操作するクラス
 */
public class ConnectionPool
{
	//QueryRunnerオブジェクト
	private static QueryRunner m_queryRunner = null;
	//Connectionオブジェクト
	private static Connection m_connection = null;

	/**
	 * ログインユーザー毎にDBへのコネクションを確立する
	 * **/
	public static void open() throws DBUtilException
	{
		try
		{
			if( getQueryRunner() == null ) throw new DBUtilException("F9000");
			m_connection = getQueryRunner().getDataSource().getConnection();
			if( m_connection == null ) throw new DBUtilException("F9000");
			m_connection.setAutoCommit(false);
		}
		catch( SQLException e ){ throw new DBUtilException("F9000");}
	}

	/**
	 * コネクションを閉じる
	 *
	 * **/
	public static void close()
	{
		if( getConnection() != null )
		{
			DbUtils.commitAndCloseQuietly(getConnection());
		}
	}

	/**
	 * コネクションを取得する
	 *
	 * @return	Connection		Connection
	 * **/
	public static Connection getConnection()
	{
		return m_connection;
	}

	/**
	 * たQueryRunnerを取得する
	 *
	 * @return	QueryRunner	QueryRunner
	 * **/
	public static QueryRunner getQueryRunner() throws DBUtilException
	{
		if( m_queryRunner == null )
		{
			m_queryRunner = new QueryRunner(DataSourceLoader.getDataSource());
		}
		return m_queryRunner;
	}
}
