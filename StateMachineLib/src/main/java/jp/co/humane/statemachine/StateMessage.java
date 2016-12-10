package jp.co.humane.statemachine;

public class StateMessage
{
	//送信元オブジェクトID
	private int m_sender = 0;
	//送信先オブジェクトID
	private int m_reciver = 0;
	//状態
	private BaseStatus m_status = null;
	//送信先へのパラメーター
	private Object m_argument = null;
	
	
	/**
	 * コンストラクタ
	 * 
	 * @param	sender	送信元オブジェクトID
	 * @param	reciver	送信先オブジェクトID
	 * @param	v		パラメーター
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
