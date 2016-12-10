package jp.co.humane.statemachine;

public abstract class BaseWorker
{
	//オブジェクトのID
	private static int ID = 0;
	private static int newID(){return ++ID;}
	private int m_id = 0;
	
	//ステートマシンオブジェクト
	private StateMachine m_stateMachine = null;
	
	/**
	 * コンストラクタ
	 * **/
	public BaseWorker()
	{
		m_id = newID();
		m_stateMachine = new StateMachine(this);
	}
	
	/**
	 * 他Workerからのメッセージを受け取る
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
