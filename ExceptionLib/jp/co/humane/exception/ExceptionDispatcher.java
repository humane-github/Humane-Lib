/*
 * �쐬���F 2005/08/10
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
package jp.co.humane.exception;

import java.util.Hashtable;

/**
 * �N���X�FExceptionDispacher
 * ����������O�̎�ނɂ��킹�āA�o�^�ς݃G���[�����n���h���[�����s����
 * @author suzuki
 */
public class ExceptionDispatcher
{
	/**
	 * �n���h���[��o�^����n�b�V���e�[�u��
	 * **/
	private static Hashtable<String,ExceptionHandler> handlers = new Hashtable<String,ExceptionHandler>();

	/**
	 * �f�t�H���g�Ŏg�p����n���h���[
	 * **/
	private static ExceptionHandler defHandler = new DefaultHandler();

	/**
	 * �R���X�g���N�^
	 * **/
	private ExceptionDispatcher()
	{
	}

	/**
	 * �n���h���[��o�^����
	 *
	 * @param	errCode		�G���[�R�[�h
	 * @param	handler		�n���h���[�I�u�W�F�N�g
	 * **/
	public static void addHandler( String errCode , ExceptionHandler handler )
	{
		handlers.put(errCode,handler);
	}

	/**
	 * �n���h���[���폜����
	 *
	 * @param	errCode	�폜����n���h���[�̃G���[�R�[�h
	 * **/
	public static void removeHandler( String errCode )
	{
		handlers.remove(errCode);
	}

	/**
	 * �����œn���ꂽ��O�I�u�W�F�N�g�̃G���[�R�[�h����
	 * �Ή�����G���[���������s����B
	 * �f�t�H���g�ł́A�G���[�R�[�h�ɑΉ����郁�b�Z�[�W���_�C�A���O�ŕ\������B
	 * ���ʂȃG���[�������s�������ꍇ��
	 * ExceptionHandler�C���^�[�t�F�[�X�����������G���[�n���h���[�N���X���`��
	 * �{�N���X��addHandler���\�b�h�Ńn���h���[�N���X��o�^����K�v������B
	 *
	 * @param	e	����������O
	 * **/
	public static void execCmd( GeneralException e )
	{
//		ExceptionHandler handler = (ExceptionHandler)handlers.get(e.getErrCode());
//		if( handler == null ) defHandler.handleException(e);
//		else
//		{
//			handler.handleException(e);
//		}
		;
		if( handlers.get(e.getErrCode()) == null ) defHandler.handleException(e);
		else
		{
			ExceptionHandler handler = (ExceptionHandler)handlers.get(e.getErrCode());
			handler.handleException(e);
		}
	}

	/**
	 * �����œn���ꂽ�G���[�R�[�h����Ή�����G���[���������s����B<br>
	 * �i�G���[�����̓��e�� execCmd(GeneralException e) ���Q�Ɓj
	 * @param code		�G���[�R�[�h
	 */
	public static void execCmd( String code )
	{
		GeneralException e = new GeneralException(code);
		execCmd(e);
	}

}
