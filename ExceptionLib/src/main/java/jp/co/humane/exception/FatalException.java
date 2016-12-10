/*
 * 作成日： 2005/08/10
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package jp.co.humane.exception;

/**
 * クラス：FatalException
 * 致命的エラー時に発生する例外オブジェクト
 * @author suzuki
 */
public class FatalException extends GeneralException
{
	/**
	 * コンストラクタ
	 * 
	 * @param	code	エラーコード
	 * **/
	public FatalException( String code )
	{
		super(code);
	}
	
	/**
	 * コンストラクタ
	 * 
	 * @param　code		エラーコード
	 * @param	e		例外ｵﾌﾞｼﾞｪｸﾄ
	 * **/
	public FatalException( String code,Exception e)
	{
		super(code,e);
	}
}
