/*
 * �쐬���F 2005/08/10
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
package jp.co.humane.exception;


/**
 * �N���X�FDefaultHandler
 * �C���^�[�t�F�[�XExceptionHandler����������f�t�H���g�n���h���[
 * @author suzuki
 */
public class DefaultHandler implements ExceptionHandler
{
	/**
	 * �f�t�H���g�̃G���[����
	 * @param	e ����������O
	 * **/
	public void handleException( GeneralException e )
	{
		ErrorDialog dlg = new ErrorDialog(e);
	}
}
