package jp.co.humane.dbutil.commonsIF;

/**
 * �A�v���P�[�V�����𗘗p���Ă��郆�[�U�[���
 * **/
public class User
{
	// ���[�U�[��
	private String name = null;
	// �p�X���[�h
	private String password = null;

	/**
	 * �R���X�g���N�^
	 * **/
	public User( String n , String p )
	{
		name = n;
		password = p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
