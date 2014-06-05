/*
 * 作成日： 2005/08/10
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package jp.co.humane.exception;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * クラス：エラーダイアログ エラー発生時にメッセージを表示するための共通オブジェクト
 * @author suzuki
 * @version 1.0
 * @updated 21-6-2011 13:37:18
 */
public class ErrorDialog
{

	/**
	 * コンストラクタ
	 *
	 * @param	exception	発生した例外
	 * **/
	public ErrorDialog( GeneralException exception )
	{
		/**
		 * メッセージサービスからエラーｺｰﾄﾞに対応したメッセージを取得する
		 * **/
		String msg = exception.getErrCode();

		/**
		 * エラーコードの種類から
		 * エラーレベル（警告orエラーor致命的エラー）とタイトル文字列を取得する
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
