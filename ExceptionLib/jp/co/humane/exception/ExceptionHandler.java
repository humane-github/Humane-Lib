/*
 * �쐬���F 2005/08/10
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
package jp.co.humane.exception;

/**
 * �C���^�[�t�F�[�X�FExceptionHandler
 * ModelGen��O�������̃G���[��������������n���h���[
 * @author suzuki
 */
public interface ExceptionHandler
{
	/**
	 * �G���[�������������邽�߂̃��\�b�h
	 * @param	e	����������O�I�u�W�F�N�g
	 * **/
	public void handleException( GeneralException e );


}
