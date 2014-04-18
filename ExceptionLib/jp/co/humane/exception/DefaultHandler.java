/*
 * 作成日： 2005/08/10
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package jp.co.humane.exception;


/**
 * クラス：DefaultHandler
 * インターフェースExceptionHandlerを実装するデフォルトハンドラー
 * @author suzuki
 */
public class DefaultHandler implements ExceptionHandler
{
	/**
	 * デフォルトのエラー処理
	 * @param	e 発生した例外
	 * **/
	public void handleException( GeneralException e )
	{
		ErrorDialog dlg = new ErrorDialog(e);
	}
}
