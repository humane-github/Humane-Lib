/*
 * 作成日： 2005/08/10
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package jp.co.humane.exception;

/**
 * クラス：ErrorCode
 * ModelGenで発生する各種エラーの種別を判定するためのエラーコードを持つ
 * @author suzuki
 */
public class ErrorCode
{
	/** ModelGenﾃﾞｰﾀﾍﾞｰｽに存在しないﾓﾃﾞﾙを参照しようとした。 */
	public final static String W0001 = "W0001";

	/** ﾓﾃﾞﾙ名の変更のとき、変更後のﾓﾃﾞﾙ名に使用済みﾓﾃﾞﾙ名が指定された。 */
	public final static String W0002 = "W0002";

	/** ModelGenﾃﾞｰﾀﾍﾞｰｽに存在しないｼｽﾃﾑを参照しようとした。 */
	public final static String W0003 = "W0003";

	/** ModelGenﾃﾞｰﾀﾍﾞｰｽに存在しないﾃﾞｰﾀｾｯﾄを参照しようとした。 */
	public final static String W0004 = "W0004";

	/** ModelGenﾃﾞｰﾀﾍﾞｰｽに存在しないﾘｻﾞﾙﾄｾｯﾄを参照しようとした。 */
	public final static String W0005 = "W0005";

	/** ModelGenﾃﾞｰﾀﾍﾞｰｽBlock表に存在しないﾌﾞﾛｯｸを参照しようとした。 */
	public final static String W0006 = "W0006";

	/** ModelGenﾃﾞｰﾀﾍﾞｰｽMask表に存在しないﾏｽｸを参照しようとした。 */
	public final static String W0007 = "W0007";

	/** Password表password列の値と、ﾕｰｻﾞｰ入力ﾊﾟｽﾜｰﾄﾞが一致しない。 */
	public final static String W0008 = "W0008";

	/** ﾕｰｻﾞｰｲﾝﾀｰﾌｪｰｽから指示された入力値に、ModelGenで使用禁止文字が含まれる。 */
	public final static String W0009 = "W0009";

	/** ﾓﾃﾞﾙﾌｫﾙﾀﾞの指示異常。	 */
	public final static String W0010 = "W0010";

	/** ﾃﾞｰﾀｿｰｽID未取得時に、ﾃﾞｰﾀﾍﾞｰｽへの操作を行った。 */
	public final static String E0001 = "E0001";

	/** datasource.xmlに設定されたDBがﾈｯﾄﾜｰｸ上に見つからない。 */
	public final static String E0002 = "E0002";

	/** datasource.xmlに設定されたDBのﾕｰｻﾞｰ名が一致しない。 */
	public final static String E0003 = "E0003";

	/** datasource.xmlに設定されたDBのﾊﾟｽﾜｰﾄﾞが一致しない。 */
	public final static String E0004 = "E0004";

	/** datasource.xmlに設定されたSOAP-URLのｻｰﾋﾞｽがﾈｯﾄﾜｰｸ上に見つからない。 */
	public final static String E0005 = "E0005";

	/** Password表id列のﾕｰｻﾞｰｸﾗｽと一致しないﾕｰｻﾞｰが指定された。 */
	public final static String E0006 = "E0006";

	/** 外部出力ﾃﾞｰﾀﾌｧｲﾙの参照に失敗した。 */
	public final static String E0007 = "E0007";


	/** ダイアログXMLの解析に失敗した。 */
	public final static String E0009 = "E0009";

	/** ダイアログXMLの更新に失敗した。 */
	public final static String E0010 = "E0010";

	/** SQLの実行に失敗。	 */
	public final static String F0001 = "SQLの実行に失敗しました";

	/** 外部ｺﾝﾎﾟｰﾈﾝﾄの呼び出しに失敗した。 */
	public final static String F0002 = "外部コンポーネントの呼び出しに失敗しました";

	/** 処理に必要な外部設定ﾌｧｲﾙが見つからない。 */
	public final static String F0003 = "処理に必要な外部設定ﾌｧｲﾙが見つかりません";

	/** 処理に必要な外部設定ﾌｧｲﾙの読み込みに失敗した。 */
	public final static String F0004 = "処理に必要な外部設定ﾌｧｲﾙの読み込みに失敗しました";

	/** ModelGenﾃﾞｰﾀﾍﾞｰｽの接続に失敗。（原因不明)	 */
	public final static String F9000 = "ModelGenﾃﾞｰﾀﾍﾞｰｽの接続に失敗しました";

	/** 予期せぬ例外が発生。 */
	public final static String F9100 = "予期せぬ例外が発生しました";
}
