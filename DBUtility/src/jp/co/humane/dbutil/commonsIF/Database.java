package jp.co.humane.dbutil.commonsIF;

import jp.co.humane.dbutil.exception.DBUtilException;

public class Database
{
	/**
	 * �f�[�^�x�[�X�Ƀ��O�C������
	 * 
	 * @param	hostname	DB�z�X�g��
	 * @param	dbname		DB��
	 * @param	user		DB�ڑ����[�U�[��
	 * @param	password	DB�ڑ����[�U�[�̃p�X���[�h
	 * **/
	public static void login(String hostname,String dbname,String user,String password) throws DBUtilException
	{
		//�f�[�^�\�[�X��ǂݍ���
		DataSourceLoader.load(hostname, dbname, user, password);
		//�R�l�N�V�������J��
		ConnectionPool.open();
	}

	/**
	 * �f�[�^�x�[�X���烍�O�A�E�g����
	 *
	 * **/
	public static void logout()
	{
		ConnectionPool.close();
	}

	/**
	 * �f�[�^�x�[�X�A�N�Z�X�p�I�u�W�F�N�g���擾����
	 * **/
	public static DBAccessor getAccessor()
	{
		return new DBAccessor();
	}
}
