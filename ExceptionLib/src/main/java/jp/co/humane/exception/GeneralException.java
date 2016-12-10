/*
 * 作成日： 2005/08/10
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package jp.co.humane.exception;

/**
 * クラス：ModelGenException
 * ModelGenで発生する全ての例外の基底クラス
 * @author suzuki
 */
public class GeneralException extends Exception
{
	/**
	 * エラーコード
	 * **/
	protected String errCode = "";

	/**
	 * エラー対象のウィンドウ（JDialog or JFrame)
	 */
	protected Object targetWindow = null;

	/**
	 * 発生例外ｵﾌﾞｼﾞｪｸﾄ
	 * **/
	protected Exception exception = null;

	/**
	 * コンストラクタ
	 *
	 * @param　code		エラーコード
	 * **/
	public GeneralException( String code )
	{
		super();
		this.errCode = code;
	}

	/**
	 * コンストラクタ
	 *
	 * @param　code		エラーコード
	 * @param	e		例外ｵﾌﾞｼﾞｪｸﾄ
	 * **/
	public GeneralException( String code,Exception e)
	{
		super();
		errCode = code;
		exception = e;
	}
	/**
	 * エラーコードを取得する
	 *
	 * @return	String	エラーコード
	 *
	 * **/
	public String getErrCode()
	{
		return this.errCode;
	}

	/**
	 * エラー対象のウィンドウを返す
	 * @return エラー対象のウィンドウ（JDialog or JFrame）
	 */
	public Object getTargetWindow() {
		return targetWindow;
	}

	/**
	 * エラー対象のウィンドウを設定する
	 * @param targetWindow エラー対象のウィンドウ（JDialog or JFrame）
	 */
	public void setTargetWindow(Object targetWindow) {
		this.targetWindow = targetWindow;
	}

}
