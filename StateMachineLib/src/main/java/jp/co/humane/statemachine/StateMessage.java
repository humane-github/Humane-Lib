package jp.co.humane.statemachine;

public class StateMessage
{
	//���M���I�u�W�F�N�gID
	private int m_sender = 0;
	//���M��I�u�W�F�N�gID
	private int m_reciver = 0;
	//���
	private BaseStatus m_status = null;
	//���M��ւ̃p�����[�^�[
	private Object m_argument = null;
	
	
	/**
	 * �R���X�g���N�^
	 * 
	 * @param	sender	���M���I�u�W�F�N�gID
	 * @param	reciver	���M��I�u�W�F�N�gID
	 * @param	v		�p�����[�^�[
	 * **/
	public StateMessage(int sender,int reciver,BaseStatus s,Object v)
	{
		m_sender = sender;
		m_reciver = reciver;
		m_status = s;
		m_argument = v;
	}
	public BaseStatus Status(){return m_status;}
	public int getSender() {return m_sender;}
	public void setSender(int m_sender) {this.m_sender = m_sender;}
	public int getReciver() {return m_reciver;}
	public void setReciver(int m_reciver) {this.m_reciver = m_reciver;}
	public Object getArgument() {return m_argument;}
	public void setArgument(Object m_argument) {this.m_argument = m_argument;}	
}
