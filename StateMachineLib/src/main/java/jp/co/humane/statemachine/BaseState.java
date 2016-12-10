package jp.co.humane.statemachine;

public interface BaseState
{
	/**
	 * 開始時に実行される
	 * **/
	public void entry(BaseWorker worker);
	/**
	 * 終了時に実行される
	 * **/
	public void exit(BaseWorker worker);
	/**
	 * 常に実行される
	 * **/
	public void exec(BaseWorker worker);
	/**
	 * 他オブジェクトからのメッセージ受信を行う
	 * **/
	public boolean onMessage(BaseWorker owner,StateMessage msg);
	/**
	 * タイムアウト時に実行される
	 * **/
	public void timeout(BaseWorker worker);
}
