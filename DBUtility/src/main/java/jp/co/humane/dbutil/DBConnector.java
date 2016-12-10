package jp.co.humane.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * シンプルなDB操作用クラス
 * **/
public abstract class DBConnector
{
	/**
	 * DBコネクション
	 * **/
	private Connection con = null;

	/**
	 * 指定DBに接続する
	 * **/
	public void open( ConnectionInfo info ) throws DBException
	{
		try
		{
			// JDBCドライバ読み込み
			String driver = getDriver();
			Class.forName(driver);

			//DB接続
			String dburl = getURL(info);
			setConnection(DriverManager.getConnection(dburl));
		}
		catch( ClassNotFoundException ex )
		{
			throw new DBException(ex);
		}
		catch( SQLException ex2 )
		{
			throw new DBException(ex2);
		}
	}

	/**
	 * DBコネクションを閉じる
	 * **/
	public void close()
	{
		if( getConnection() != null )
		{
			try{getConnection().close();}catch( SQLException ex ){ex.printStackTrace();}
		}
	}

	/**
	 * SQLを発行
	 * **/
	public ResultSet executeQuery( String sql ) throws DBException
	{
		try
		{
			Statement st = getConnection().createStatement();
			return st.executeQuery(sql);
		}
		catch( SQLException ex )
		{
			throw new DBException(ex);
		}
	}

	/**
	 * 更新SQLを発行
	 * **/
	public int executeUpdate( String sql , boolean autoCommit ) throws DBException
	{
		try
		{
			getConnection().setAutoCommit(autoCommit);

			Statement st = getConnection().createStatement();
			int cnt = st.executeUpdate(sql);
			return cnt;
		}
		catch( SQLException ex )
		{
			try
			{
				getConnection().rollback();
				throw new DBException(ex);
			}catch( SQLException ex2 ){ex2.printStackTrace();}
			return -1;
		}
	}

	/**
	 * テーブルが存在するかを返す
	 *
	 * @param dbTable	DBTableオブジェクト
	 * @return true:存在する、false:存在しない
	 * @throws FatalException
	 */
	public boolean existTable(String tablename) throws DBException
	{
		String sql = null;
		sql = "select count(*) from pg_class where relname='" + tablename + "' and relkind='r'";

		boolean ret = false;
		ResultSet rs = null;
		try
		{
			rs = executeQuery(sql);
			if (rs.next() == true ){
				int cnt = rs.getInt(1);
				if (cnt>0) ret = true;
			}
			rs.close(); //実行結果を破棄
		}
		catch( SQLException ex )
		{
			throw new DBException(ex);
		}
		finally
		{
			try{if( rs != null ){rs.close();}}catch(Exception ex){}
		}
		return ret;
	}

	/**
	 * トランザクション終了
	 * **/
	public void commit() throws DBException
	{
		try
		{
			getConnection().commit();
		}
		catch( SQLException ex )
		{
			try
			{
				getConnection().rollback();
				throw new DBException(ex);
			}catch( SQLException ex2 ){ex.printStackTrace();}
		}
	}

	/**
	 * トランザクション終了
	 * **/
	public void rollback()
	{
		try
		{
			getConnection().rollback();
		}
		catch( SQLException ex ){}
	}

	/**
	 * DB接続URLを取得
	 * **/
	public abstract String getURL(ConnectionInfo info);

	/**
	 * DBドライバ文字列を取得
	 * **/
	public abstract String getDriver();


	/************************ getter / setter *******************************/
	public Connection getConnection() {return con;}
	public void setConnection(Connection con) {this.con = con;}

}
