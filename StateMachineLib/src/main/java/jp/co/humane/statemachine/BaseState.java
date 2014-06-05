package jp.co.humane.statemachine;

public interface BaseState
{
	/**
	 * �J�n���Ɏ��s�����
	 * **/
	public void entry(BaseWorker worker);
	/**
	 * �I�����Ɏ��s�����
	 * **/
	public void exit(BaseWorker worker);
	/**
	 * ��Ɏ��s�����
	 * **/
	public void exec(BaseWorker worker);
	/**
	 * ���I�u�W�F�N�g����̃��b�Z�[�W��M���s��
	 * **/
	public boolean onMessage(BaseWorker owner,StateMessage msg);
	/**
	 * �^�C���A�E�g���Ɏ��s�����
	 * **/
	public void timeout(BaseWorker worker);
}
