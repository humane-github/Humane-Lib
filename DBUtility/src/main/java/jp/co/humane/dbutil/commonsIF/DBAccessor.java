/*
 * �쐬��: 2005/03/30
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
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
 * ModelGen�f�[�^�x�[�X�ւr�p�k�𔭍s���A���ʂ�Ԃ��B
 * SQL�̎��s�Ǝ��s���ʃI�u�W�F�N�g�̍쐬�́ADbUtils�R���|�[�l���g�𗘗p����
 */
public final class DBAccessor
{
	/**
	 * �g�����U�N�V���������Ŏg�p����Connection�I�u�W�F�N�g
	 * **/
	private Connection transactConnect = null;

	/**
	 * �o�b�`���[�h�p�̃X�e�[�g�����g���i�[����
	 * �L�[=���p�҂��C�ӂɌ��߂�g�����U�N�V������
	 * �l=PreparedStatement�I�u�W�F�N�g
	 * **/
	private HashMap<String,PreparedStatement> transactMap = new HashMap<String,PreparedStatement>();

	/**
	 * �R���X�g���N�^
	 *
	 * **/
	public DBAccessor(){}

	/**
	 * ���[���o�b�N�����s���܂��B
	 *
	 * @exception DBException SQLException���L���b�`�����Ƃ�
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
	 * �g�����U�N�V�������J�n����
	 *
	 * **/
	public void beginTransaction() throws DBUtilException
	{
		transactConnect = ConnectionPool.getConnection();
	}

	/**
	 * �g�����U�N�V��������SQL�����s���܂�
	 *
	 * @param sql			���s����SQL
	 * @param params		SQL�̃p�����[�^�[
	 * @param beanClass		���ʂ��i�[����Bean�N���X
	 * @return
	 * @throws DBUtilException
	 */
	public <T> List<T> executeQuery(String sql,Object[] params,Class beanClass) throws DBUtilException
	{
		return (List<T>)executeQuery(sql,params,new BeanListHandler(beanClass));
	}

	/**
	 * �g�����U�N�V��������SQL�����s���܂�
	 * �G���[�����������ꍇ�́A�X�V�����[���o�b�N��DB�ڑ�����܂�
	 *
	 * @param	sql		���s����SQL
	 * @param	params	SQL�̃p�����[�^�[
	 * @return	int		�X�V����
	 * **/
	public Object executeQuery(String sql,Object[] params,ResultSetHandler handler) throws DBUtilException
	{
		try
		{
			/**
			 * SQL���s�I�u�W�F�N�g�擾
			 * **/
			QueryRunner runner = ConnectionPool.getQueryRunner();
			if( runner == null ) throw new DBUtilException("F9000");

			/**
			 * �N�G���[���s
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
	 * �g�����U�N�V��������SQL�����s���܂�
	 * �G���[�����������ꍇ�́A�X�V�����[���o�b�N��DB�ڑ�����܂�
	 *
	 * @param	sql		���s����SQL
	 * @param	params	SQL�̃p�����[�^�[
	 * @return	int		�X�V����
	 * **/
	public int executeUpdate( String sql,Object[] params ) throws DBUtilException
	{

		/** �g�����U�N�V���������p��Connection���݊m�F **/
		if( transactConnect == null ) return -1;


		int count = -1;
		try
		{
			/** SQL���s�I�u�W�F�N�g�擾 **/
			QueryRunner runner = ConnectionPool.getQueryRunner();
			if( runner == null ) throw new DBUtilException("F9000");

			/** SQL���s **/
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
	 * �o�b�`���[�h�Ŏ��s����SQL��o�^����
	 *
	 * @param	transactName	�g�����U�N�V�������i�C�Ӂj
	 * @param	sql				�o�b�`�Ŏ��s����SQL
	 * @throws	DBUtilException	�X�e�[�g�����g�쐬���Ɉُ픭��
	 * **/
	public void addQuery( String transactName, String sql ) throws DBUtilException
	{
		try
		{
			/**
			 * ���s����SQL��o�^
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
	 * �o�b�`���[�h��SQL�����s����
	 *
	 * @param	transactName	�g�����U�N�V�������i�C�Ӂj
	 * @param	params			�X�e�[�g�����g�ɐݒ肷��p�����[�^�[
	 * **/
	public void addBatch( String transactName,Object[] params ) throws DBUtilException
	{
		PreparedStatement batchStmt = null;
		try
		{
			// �X�e�[�g�����g�ɕϐ����Z�b�g
			batchStmt = transactMap.get(transactName);
			if( batchStmt == null ) throw new DBUtilException("F0001");
			batchStmt = fillStatement(batchStmt,params);

			// �X�e�[�g�����g�Ɏ��s����SQL��o�^
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
	 * �o�b�`���[�h���I������
	 *
	 * @param	transactName	�g�����U�N�V������
	 * **/
	public void execBatch( String transactName ) throws DBUtilException
	{
		PreparedStatement batchStmt = null;

		try
		{
			System.out.println("execute transaction name="+transactName);
			/**
			 * �X�e�[�g�����g�ɂ��܂��Ă�N�G���[�����s
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
	 * �g�����U�N�V�������I�����܂�
	 * **/
	public void finishTransaction() throws DBUtilException
	{
		/**
		 * �g�����U�N�V�����������s���Ă��Ȃ��ꍇ��
		 * �����������Ȃ�
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
	 * �g�����U�N�V���������Ŏg�p����Connection�I�u�W�F�N�g��Ԃ�
	 * @return	Connection�I�u�W�F�N�g
	 */
	public Connection getConnection()
	{
		return transactConnect;
	}


	/***************************************/
	/**************����J���\�b�h***********/
	/***************************************/
	/**
	 * PreparedStatement�ɕϐ����Z�b�g����
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
