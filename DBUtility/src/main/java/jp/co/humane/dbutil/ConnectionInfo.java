package jp.co.humane.dbutil;

/**
 * DB�ڑ��ɕK�v�ȏ������I�u�W�F�N�g
 * **/
public class ConnectionInfo
{
	// DB��
	private String dbname = null;
	// ���[�U�[��
	private String user = null;
	// �p�X���[�h
	private String password = null;

	// �R���X�g���N�^
	public ConnectionInfo(String dbname, String user, String password) {
		super();
		this.dbname = dbname;
		this.user = user;
		this.password = password;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}




}
