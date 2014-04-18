/*
 * 作成日： 2005/08/10
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package jp.co.humane.exception;

/**
 * インターフェース：ExceptionHandler
 * ModelGen例外発生時のエラー処理を実装するハンドラー
 * @author suzuki
 */
public interface ExceptionHandler
{
	/**
	 * エラー処理を実装するためのメソッド
	 * @param	e	発生した例外オブジェクト
	 * **/
	public void handleException( GeneralException e );


}
