/*
 * �쐬���F 2005/08/11
 */
package jp.co.humane.dbutil.commonsIF;

import javax.sql.DataSource;

import jp.co.humane.dbutil.exception.DBUtilException;

import org.postgresql.jdbc3.Jdbc3SimpleDataSource;

/**
 * DB�ڑ������Ǘ�����N���X
 * DB�ڑ����́A�ݒ���Ǘ��R���|�[�l���g���g�p����
 * datasource.xml�t�@�C������ǂݍ���
 * @author suzuki
 */
public class DataSourceLoader
{

	private static Jdbc3SimpleDataSource m_datasourcePG = null;
	
	/**
	 * DB�Ƃ̐ڑ����m������
	 * 
	 * @param	hostname	DB�z�X�g��
	 * @param	dbname		DB��
	 * @param	user		DB�ڑ����[�U�[��
	 * @param	password	DB�ڑ����[�U�[�̃��O�C���p�X���[�h
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
	 * �Z�b�V�����m�����̃f�[�^�\�[�X��Ԃ�
	 * **/
	public static DataSource getDataSource()
	{
		return m_datasourcePG;
	}
}
