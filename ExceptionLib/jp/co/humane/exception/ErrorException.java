/*
 * �쐬���F 2005/08/10
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
package jp.co.humane.exception;

/**
 * �N���X�F�G���[���ɔ��������O
 * @author suzuki
 */
public class ErrorException extends GeneralException
{
	/**
	 * �R���X�g���N�^
	 * 
	 * @param code	�G���[�R�[�h
	 * **/
	public ErrorException( String code )
	{
		super( code );
	}
	
	/**
	 * �R���X�g���N�^
	 * 
	 * @param�@code		�G���[�R�[�h
	 * @param	e		��O��޼ު��
	 * **/
	public ErrorException( String code,Exception e)
	{
		super(code,e);
	}

}
