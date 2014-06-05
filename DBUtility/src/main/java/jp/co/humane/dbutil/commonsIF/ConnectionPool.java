package jp.co.humane.dbutil.commonsIF;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import jp.co.humane.dbutil.exception.DBUtilException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * �R�l�N�V�����𑀍삷��N���X
 */
public class ConnectionPool
{
	//QueryRunner�I�u�W�F�N�g
	private static QueryRunner m_queryRunner = null;
	//Connection�I�u�W�F�N�g
	private static Connection m_connection = null;

	/**
	 * ���O�C�����[�U�[����DB�ւ̃R�l�N�V�������m������
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
	 * �R�l�N�V���������
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
	 * �R�l�N�V�������擾����
	 *
	 * @return	Connection		Connection
	 * **/
	public static Connection getConnection()
	{
		return m_connection;
	}

	/**
	 * ��QueryRunner���擾����
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
