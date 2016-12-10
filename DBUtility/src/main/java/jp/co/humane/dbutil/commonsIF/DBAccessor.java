/*
 * 作成日: 2005/03/30
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package jp.co.humane.dbutil.commonsIF;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import jp.co.humane.dbutil.DBException;
import jp.co.humane.dbutil.DataSourceData;
import jp.co.humane.dbutil.exception.DBUtilException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * ModelGenデータベースへＳＱＬを発行し、結果を返す。
 * SQLの実行と実行結果オブジェクトの作成は、DbUtilsコンポーネントを利用する
 */
public final class DBAccessor
{
	/**
	 * トランザクション処理で使用するConnectionオブジェクト
	 * **/
	private Connection transactConnect = null;

	/**
	 * バッチモード用のステートメントを格納する
	 * キー=利用者が任意に決めるトランザクション名
	 * 値=PreparedStatementオブジェクト
	 * **/
	private HashMap<String,PreparedStatement> transactMap = new HashMap<String,PreparedStatement>();

	/**
	 * コンストラクタ
	 *
	 * **/
	public DBAccessor(){}

	/**
	 * ロールバックを実行します。
	 *
	 * @exception DBException SQLExceptionをキャッチしたとき
	 */
	public void rollback(Connection con) throws DBUtilException
	{
		try
		{
			DbUtils.rollback(con);
		}
		catch (SQLException e)
		{
			throw new DBUtilException("F9000");
		}
	}

	/**
	 * トランザクションを開始する
	 *
	 * **/
	public void beginTransaction() throws DBUtilException
	{
		transactConnect = ConnectionPool.getConnection();
	}

	/**
	 * トランザクション内でSQLを実行します
	 *
	 * @param sql			実行するSQL
	 * @param params		SQLのパラメーター
	 * @param beanClass		結果を格納するBeanクラス
	 * @return
	 * @throws DBUtilException
	 */
	public <T> List<T> executeQuery(String sql,Object[] params,Class beanClass) throws DBUtilException
	{
		return (List<T>)executeQuery(sql,params,new BeanListHandler(beanClass));
	}

	/**
	 * トランザクション内でSQLを実行します
	 * エラーが発生した場合は、更新をロールバックしDB接続を閉じます
	 *
	 * @param	sql		実行するSQL
	 * @param	params	SQLのパラメーター
	 * @return	int		更新件数
	 * **/
	public Object executeQuery(String sql,Object[] params,ResultSetHandler handler) throws DBUtilException
	{
		try
		{
			/**
			 * SQL実行オブジェクト取得
			 * **/
			QueryRunner runner = ConnectionPool.getQueryRunner();
			if( runner == null ) throw new DBUtilException("F9000");

			/**
			 * クエリー実行
			 * **/
			return runner.query(transactConnect,sql,params,handler);
		}
		catch( Exception e )
		{
			rollback(transactConnect);
			e.printStackTrace();
			throw new DBUtilException("F0001");
		}
	}


	/**
	 * トランザクション内でSQLを実行します
	 * エラーが発生した場合は、更新をロールバックしDB接続を閉じます
	 *
	 * @param	sql		実行するSQL
	 * @param	params	SQLのパラメーター
	 * @return	int		更新件数
	 * **/
	public int executeUpdate( String sql,Object[] params ) throws DBUtilException
	{

		/** トランザクション処理用のConnection存在確認 **/
		if( transactConnect == null ) return -1;


		int count = -1;
		try
		{
			/** SQL実行オブジェクト取得 **/
			QueryRunner runner = ConnectionPool.getQueryRunner();
			if( runner == null ) throw new DBUtilException("F9000");

			/** SQL実行 **/
			count = runner.update(transactConnect,sql,params);
		}
		catch( Exception e )
		{
			rollback(transactConnect);
			e.printStackTrace();
			throw new DBUtilException("F0001");
		}

		return count;
	}

	/**
	 * バッチモードで実行するSQLを登録する
	 *
	 * @param	transactName	トランザクション名（任意）
	 * @param	sql				バッチで実行するSQL
	 * @throws	DBUtilException	ステートメント作成時に異常発生
	 * **/
	public void addQuery( String transactName, String sql ) throws DBUtilException
	{
		try
		{
			/**
			 * 実行するSQLを登録
			 * **/
			PreparedStatement batchStmt = transactConnect.prepareStatement(sql);
			transactMap.put(transactName,batchStmt);

		}
		catch( Exception e )
		{
			e.printStackTrace();
			rollback(transactConnect);
			throw new DBUtilException("F9000");
		}
	}

	/**
	 * バッチモードでSQLを実行する
	 *
	 * @param	transactName	トランザクション名（任意）
	 * @param	params			ステートメントに設定するパラメーター
	 * **/
	public void addBatch( String transactName,Object[] params ) throws DBUtilException
	{
		PreparedStatement batchStmt = null;
		try
		{
			// ステートメントに変数をセット
			batchStmt = transactMap.get(transactName);
			if( batchStmt == null ) throw new DBUtilException("F0001");
			batchStmt = fillStatement(batchStmt,params);

			// ステートメントに実行するSQLを登録
			batchStmt.addBatch();
//			addBatchCount();
//
//			if( getBatchCount() >= MAX_BATCH_SIZE )
//			{
//				batchStmt.executeBatch();
//				setBatchCount(0);
//			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			rollback(transactConnect);
			throw new DBUtilException("F0001");
		}
	}

	/**
	 * バッチモードを終了する
	 *
	 * @param	transactName	トランザクション名
	 * **/
	public void execBatch( String transactName ) throws DBUtilException
	{
		PreparedStatement batchStmt = null;

		try
		{
			System.out.println("execute transaction name="+transactName);
			/**
			 * ステートメントにたまってるクエリーを実行
			 * **/
			batchStmt = transactMap.get(transactName);


			if( batchStmt == null )
			{
				rollback(transactConnect);
				throw new DBUtilException("F0001");
			}

			batchStmt.executeBatch();
			//setBatchCount(0);
		}
		catch( Exception e )
		{
            if( e instanceof BatchUpdateException )
            {
                ((BatchUpdateException)e).getNextException().printStackTrace();
            }
			e.printStackTrace();
			rollback(transactConnect);
			throw new DBUtilException("F0001");
		}
	}

	/**
	 * トランザクションを終了します
	 * **/
	public void finishTransaction() throws DBUtilException
	{
		/**
		 * トランザクション処理を行っていない場合は
		 * 何も処理しない
		 * **/
		if( transactConnect == null ) return;
		try
		{
			transactConnect.commit();
		}
		catch( Exception e )
		{
			throw new DBUtilException("F9100");
		}

	}


	/**
	 * トランザクション処理で使用するConnectionオブジェクトを返す
	 * @return	Connectionオブジェクト
	 */
	public Connection getConnection()
	{
		return transactConnect;
	}


	/***************************************/
	/**************非公開メソッド***********/
	/***************************************/
	/**
	 * PreparedStatementに変数をセットする
	 * **/
	private PreparedStatement fillStatement(PreparedStatement stmt,Object[] params)	throws SQLException
	{
		if (params == null)
		{
			return stmt;
		}

		for (int i = 0; i < params.length; i++)
		{
			if (params[i] != null)
			{
				stmt.setObject(i + 1, params[i]);
			}
			else
			{
				stmt.setNull(i + 1, Types.OTHER);
			}
		}

		return stmt;
	}
}
