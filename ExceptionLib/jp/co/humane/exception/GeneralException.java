/*
 * �쐬���F 2005/08/10
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
package jp.co.humane.exception;

/**
 * �N���X�FModelGenException
 * ModelGen�Ŕ�������S�Ă̗�O�̊��N���X
 * @author suzuki
 */
public class GeneralException extends Exception
{
	/**
	 * �G���[�R�[�h
	 * **/
	protected String errCode = "";

	/**
	 * �G���[�Ώۂ̃E�B���h�E�iJDialog or JFrame)
	 */
	protected Object targetWindow = null;

	/**
	 * ������O��޼ު��
	 * **/
	protected Exception exception = null;

	/**
	 * �R���X�g���N�^
	 *
	 * @param�@code		�G���[�R�[�h
	 * **/
	public GeneralException( String code )
	{
		super();
		this.errCode = code;
	}

	/**
	 * �R���X�g���N�^
	 *
	 * @param�@code		�G���[�R�[�h
	 * @param	e		��O��޼ު��
	 * **/
	public GeneralException( String code,Exception e)
	{
		super();
		errCode = code;
		exception = e;
	}
	/**
	 * �G���[�R�[�h���擾����
	 *
	 * @return	String	�G���[�R�[�h
	 *
	 * **/
	public String getErrCode()
	{
		return this.errCode;
	}

	/**
	 * �G���[�Ώۂ̃E�B���h�E��Ԃ�
	 * @return �G���[�Ώۂ̃E�B���h�E�iJDialog or JFrame�j
	 */
	public Object getTargetWindow() {
		return targetWindow;
	}

	/**
	 * �G���[�Ώۂ̃E�B���h�E��ݒ肷��
	 * @param targetWindow �G���[�Ώۂ̃E�B���h�E�iJDialog or JFrame�j
	 */
	public void setTargetWindow(Object targetWindow) {
		this.targetWindow = targetWindow;
	}

}
