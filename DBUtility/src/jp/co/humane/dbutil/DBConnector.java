package jp.co.humane.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * �V���v����DB����p�N���X
 * **/
public abstract class DBConnector
{
	/**
	 * DB�R�l�N�V����
	 * **/
	private Connection con = null;

	/**
	 * �w��DB�ɐڑ�����
	 * **/
	public void open( ConnectionInfo info ) throws DBException
	{
		try
		{
			// JDBC�h���C�o�ǂݍ���
			String driver = getDriver();
			Class.forName(driver);

			//DB�ڑ�
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
	 * DB�R�l�N�V���������
	 * **/
	public void close()
	{
		if( getConnection() != null )
		{
			try{getConnection().close();}catch( SQLException ex ){ex.printStackTrace();}
		}
	}

	/**
	 * SQL�𔭍s
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
	 * �X�VSQL�𔭍s
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
	 * �e�[�u�������݂��邩��Ԃ�
	 *
	 * @param dbTable	DBTable�I�u�W�F�N�g
	 * @return true:���݂���Afalse:���݂��Ȃ�
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
			rs.close(); //���s���ʂ�j��
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
	 * �g�����U�N�V�����I��
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
	 * �g�����U�N�V�����I��
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
	 * DB�ڑ�URL���擾
	 * **/
	public abstract String getURL(ConnectionInfo info);

	/**
	 * DB�h���C�o��������擾
	 * **/
	public abstract String getDriver();


	/************************ getter / setter *******************************/
	public Connection getConnection() {return con;}
	public void setConnection(Connection con) {this.con = con;}

}
