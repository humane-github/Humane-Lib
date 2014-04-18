package jp.co.humane.statemachine;

import java.util.Timer;
import java.util.TimerTask;

public class StateMachine
{

	private BaseWorker m_owner = null;
	private BaseState m_currentState = null;
	private BaseState m_globalState = null;
	private Timer m_timeoutTimer = null;
	
	public StateMachine(BaseWorker w)
	{
		m_owner = w;
	}

	public void setGlobalState(BaseState state)
	{
		if( m_globalState != null ){m_globalState.exit(m_owner);}
		m_globalState = state;
		if(m_globalState != null){m_globalState.entry(m_owner);}		
	}
	
	/**
	 * 状態を変更する
	 * 
	 * @param	state
	 * **/
	public void changeState(BaseState state)
	{
		if( m_currentState != null ){m_currentState.exit(m_owner);}
		m_currentState = state;
		if(m_currentState != null){m_currentState.entry(m_owner);}
	}
	
	/**
	 * 現在のStateを実行する
	 * **/
	public void exec()
	{
		if( m_globalState != null ){m_globalState.exec(m_owner);}
		if( m_currentState != null ){m_currentState.exec(m_owner);}
	}
		
	/**
	 * カレントステートのタイムアウトを設定する
	 * 
	 * @param	timeoutMilliseconds	タイムアウト時間（ミリ秒） 
	 * **/
    public boolean setTimeoutTimer(int timeoutMilliseconds)
    {
    	boolean res = true;
    	if( timeoutMilliseconds < 0 )
    	{
    		m_timeoutTimer.cancel();
    	}
    	else
    	{
        	try
        	{
            	TimerTask task = new TimerTask()
            	{
        			public void run()
        			{
        				if( m_currentState != null ){m_currentState.timeout(m_owner);}
        			}
        		};
        		m_timeoutTimer = new Timer();
        		m_timeoutTimer.schedule(task, timeoutMilliseconds);
        	}
        	catch( Exception ex )
        	{
        		ex.printStackTrace();
        		res = false;
        	}    		
    	}
    	return res;
    }
    
	/**
	 * 他Workerからのメッセージを受け取る
	 * **/
	public boolean handleMessage(StateMessage msg)
	{
		boolean result = false;
		if( m_globalState != null )
		{
			result = m_globalState.onMessage(m_owner, msg);
		}
		return ( m_currentState != null && m_currentState.onMessage(m_owner, msg));
	}
}
