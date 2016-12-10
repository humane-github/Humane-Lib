/*
 * 作成日： 2005/08/10
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package jp.co.humane.exception;

import java.util.Hashtable;

/**
 * クラス：ExceptionDispacher
 * 発生した例外の種類にあわせて、登録済みエラー処理ハンドラーを実行する
 * @author suzuki
 */
public class ExceptionDispatcher
{
	/**
	 * ハンドラーを登録するハッシュテーブル
	 * **/
	private static Hashtable<String,ExceptionHandler> handlers = new Hashtable<String,ExceptionHandler>();

	/**
	 * デフォルトで使用するハンドラー
	 * **/
	private static ExceptionHandler defHandler = new DefaultHandler();

	/**
	 * コンストラクタ
	 * **/
	private ExceptionDispatcher()
	{
	}

	/**
	 * ハンドラーを登録する
	 *
	 * @param	errCode		エラーコード
	 * @param	handler		ハンドラーオブジェクト
	 * **/
	public static void addHandler( String errCode , ExceptionHandler handler )
	{
		handlers.put(errCode,handler);
	}

	/**
	 * ハンドラーを削除する
	 *
	 * @param	errCode	削除するハンドラーのエラーコード
	 * **/
	public static void removeHandler( String errCode )
	{
		handlers.remove(errCode);
	}

	/**
	 * 引数で渡された例外オブジェクトのエラーコードから
	 * 対応するエラー処理を実行する。
	 * デフォルトでは、エラーコードに対応するメッセージをダイアログで表示する。
	 * 特別なエラー処理を行いたい場合は
	 * ExceptionHandlerインターフェースを実装したエラーハンドラークラスを定義し
	 * 本クラスのaddHandlerメソッドでハンドラークラスを登録する必要がある。
	 *
	 * @param	e	発生した例外
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
	 * 引数で渡されたエラーコードから対応するエラー処理を実行する。<br>
	 * （エラー処理の内容は execCmd(GeneralException e) を参照）
	 * @param code		エラーコード
	 */
	public static void execCmd( String code )
	{
		GeneralException e = new GeneralException(code);
		execCmd(e);
	}

}
