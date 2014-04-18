package jp.co.humane.dbutil;

/**
 * �t�@�C��datasource.xml�t�@�C���ɐݒ肳�ꂽ���������o�ϐ��Ɏ��N���X
 */
public class DataSourceData
{

	/** DB�_���� **/
	private String name = null;
	
	/** DB�z�X�g�� **/
	private String hostname = null;
	
	/** DB�� **/
	private String dbname = null;
	
	/** DB���O�C�����[�U�[�� **/
	private String user = null;
	
	/** DB���O�C���p�X���[�h **/
	private String password = null;
		
	
//-----------------------------------------------------------------------------------------	
	
	/**
	 * �R���X�g���N�^
	 * **/
	public DataSourceData()
	{
	}

	/**
	 * �z�X�g�����擾����
	 * 
	 * @return String
	 */
	public String getHostname() {
		return hostname;
	}
	/**
	 * �z�X�g����ݒ肷��
	 * 
	 * @param host �z�X�g��
	 */
	public void setHostname(String host) {
		this.hostname = host;
	}
	/**
	 * DB�����擾����
	 * 
	 * @return String
	 */
	public String getDbname() {
		return dbname;
	}
	/**
	 * DB����ݒ肷��
	 * 
	 * @param dbname DB��
	 */
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	/**
	 * �_��DB�����擾����
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * �_��DB����ݒ肷��
	 * 
	 * @param name �_��DB��
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * �p�X���[�h���擾����
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * �p�X���[�h��ݒ肷��
	 * 
	 * @param password �p�X���[�h
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * ���[�U�[�����擾����
	 * 
	 * @return String
	 */
	public String getUser() {
		return user;
	}
	/**
	 * ���[�U�[����ݒ肷��
	 * 
	 * @param user ���[�U�[��
	 */
	public void setUser(String user) {
		this.user = user;
	}

	public String toString()
	{
		return "name="+this.getName()+
				" : user="+this.getUser() +
				" : password="+this.getPassword() +
				" : host="+this.getHostname() +
				" : dbname="+this.getDbname();
	}
}
