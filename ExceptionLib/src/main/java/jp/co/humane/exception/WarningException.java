/*
 * 作成日： 2005/08/10
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package jp.co.humane.exception;

/**
 * クラス：WarningException
 * 警告レベルの異常時に発生する例外
 * @author suzuki
 */
public class WarningException extends GeneralException
{
	/**
	 * コンストラクタ
	 * 
	 * @param	code	エラーコード
	 * **/
	public WarningException( String code )
	{
		super(code);
	}
	
	/**
	 * コンストラクタ
	 * 
	 * @param　code		エラーコード
	 * @param	e		例外ｵﾌﾞｼﾞｪｸﾄ
	 * **/
	public WarningException( String code,Exception e)
	{
		super(code,e);
	}

}
