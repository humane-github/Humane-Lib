/*
 * �쐬���F 2005/08/10
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
package jp.co.humane.exception;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * �N���X�F�G���[�_�C�A���O �G���[�������Ƀ��b�Z�[�W��\�����邽�߂̋��ʃI�u�W�F�N�g
 * @author suzuki
 * @version 1.0
 * @updated 21-6-2011 13:37:18
 */
public class ErrorDialog
{

	/**
	 * �R���X�g���N�^
	 *
	 * @param	exception	����������O
	 * **/
	public ErrorDialog( GeneralException exception )
	{
		/**
		 * ���b�Z�[�W�T�[�r�X����G���[���ނɑΉ��������b�Z�[�W���擾����
		 * **/
		String msg = exception.getErrCode();

		/**
		 * �G���[�R�[�h�̎�ނ���
		 * �G���[���x���i�x��or�G���[or�v���I�G���[�j�ƃ^�C�g����������擾����
		 * **/
		int level = JOptionPane.INFORMATION_MESSAGE;
		String title = "Information";
		String code = exception.getErrCode();
		if( code != null && code.length() > 0 )
		{
			if( code.startsWith("W") )
			{
				level = JOptionPane.WARNING_MESSAGE;
				title = "Warning";
			}
			else if( code.startsWith("E") )
			{
				level = JOptionPane.ERROR_MESSAGE;
				title = "Error";
			}
			else if( code.startsWith("F") )
			{
				level = JOptionPane.ERROR_MESSAGE;
				title = "Fatal Error";
			}
		}


//		JOptionPane.showMessageDialog(null,msg,title,level);

		Object targetWindow = exception.getTargetWindow();
		if (targetWindow instanceof JFrame){
			JOptionPane.showMessageDialog((JFrame)targetWindow,msg,title,level);
		}else if (targetWindow instanceof JDialog){
			JOptionPane.showMessageDialog((JDialog)targetWindow,msg,title,level);
		}else{
			JOptionPane.showMessageDialog(null,msg,title,level);
		}
	}

}
