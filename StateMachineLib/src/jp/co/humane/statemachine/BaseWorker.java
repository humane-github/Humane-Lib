package jp.co.humane.statemachine;

public abstract class BaseWorker
{
	//�I�u�W�F�N�g��ID
	private static int ID = 0;
	private static int newID(){return ++ID;}
	private int m_id = 0;
	
	//�X�e�[�g�}�V���I�u�W�F�N�g
	private StateMachine m_stateMachine = null;
	
	/**
	 * �R���X�g���N�^
	 * **/
	public BaseWorker()
	{
		m_id = newID();
		m_stateMachine = new StateMachine(this);
	}
	
	/**
	 * ��Worker����̃��b�Z�[�W���󂯎��
	 * **/
	public boolean handleMessage(StateMessage msg)
	{
		return m_stateMachine.handleMessage(msg);
	}
	
	/**
	 * Getter
	 * **/
	public StateMachine StateMachine(){return m_stateMachine;}
	public int ID(){return m_id;}
}
