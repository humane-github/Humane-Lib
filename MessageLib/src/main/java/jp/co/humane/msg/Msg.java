package jp.co.humane.msg;
/*
 * 作成日： 2005/08/29
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;

/**
 * ロケールを判断して、メッセージを返すクラス
 * @author suzuki
 */
public class Msg
{
	/** 日本語メッセージ読み込み **/
	public final static int LANG_JAPANESE = 0;
	/** 英語メッセージ読み込み **/
	public final static int LANG_ENGLISH = 1;
	/** 中国語メッセージ読み込み **/
	public final static int LANG_CHINESE = 2;
	/** 設定ファイルの言語指定で読み込み **/
	public final static int LANG_AUTO = 3;
	/**
	 * 日本語用メッセージファイルのファイル名
	 * **/
	public final static String JAPANESE_MSGFILE = "japanese.properties";
	
	/**
	 * 英語用メッセージファイルのファイル名
	 * **/
	public final static String ENGLISH_MSGFILE = "english.properties";
	
	/**
	 * 中国語用メッセージファイルのファイル名
	 * **/
	public final static String CHINESE_MSGFILE = "chinese.properties";
	
	/**
	 * メッセージ読み込み用バンドルクラス
	 * **/
	private static PropertyResourceBundle _bundle = null;
	
	/**
	 * リソースバンドルオブジェクトを生成する
	 * 
	 * **/
	private static PropertyResourceBundle getResourceBundle( int lang )
	{
		// OSのロケールからメッセージファイルのファイル名を取得
		
		String msgfilename = JAPANESE_MSGFILE;
		
		/**
		 * メッセージファイルのInputStreamを取得
		 * **/
		try
		{
			File msgFile = new File(System.getProperty("user.dir"),msgfilename);
			FileInputStream fis = new FileInputStream(msgFile);
			PropertyResourceBundle property = new PropertyResourceBundle(fis);
			fis.close();
			return property;
		}
		catch( IOException ioe )
		{
			System.out.println("message file:"+System.getProperty("user.dir")+File.separator+msgfilename+" not found...");
		}
			
		return null;
	}
	
	/**
	 * メッセージを取得する<br>
	 * メッセージキーコードに対応するメッセージが見つからない場合は
	 *　引数で指定したメッセージキーコードを返す。
	 * 
	 * @param	key		メッセージキーコード
	 * @return	String	メッセージ
	 * **/
	public static String get( String key )
	{
		return get(key,Msg.LANG_AUTO);
	}
	
	/**
	 * メッセージを取得する。<br>
	 * メッセージキーコードに対応するメッセージが見つからない場合は
	 * 引数で指定したメッセージキーコードを返す。
	 * ロケールは、引数で指定されたものを使用する
	 * 
	 * @param	key		メッセージキーコード
	 * @param  lang	言語指定
	 * @return	String	メッセージ	
	 * **/
	public static String get( String key, int lang )
	{
		String msg = null;
		if( _bundle == null ) _bundle = getResourceBundle(lang);
		
		if( _bundle == null ) return key;
		else msg = (String)_bundle.handleGetObject(key);	
		
		if( msg != null && msg.length() > 0 ) return msg;
		else return key;		
	}
	
	/**
	 * メッセージを取得する。<br>
	 * メッセージキーコードに対応するメッセージが見つからない場合は
	 * 引数で指定したメッセージキーコードを返す。
	 * ロケールは、引数で指定されたものを使用する
	 * このメソッドは、都度メッセージファイルを読み込んで処理を行う。
	 * そのため、getメソッドに比べて処理が遅い。
	 * 本メソッドは、日本語環境で動作しているときに、英語メッセージを取得したい場合などで使用する
	 * 
	 * @param	key		メッセージキーコード
	 * @param  lang	言語指定
	 * @return	String	メッセージ	
	 * **/
	public static String getMessageFromFile( String key, int lang )
	{
		String msg = null;
		PropertyResourceBundle bundle = getResourceBundle(lang);
		
		if( bundle == null ) return key;
		else msg = (String)bundle.handleGetObject(key);	
		
		if( msg != null && msg.length() > 0 ) return msg;
		else return key;				
	}
	
	/**
	 * メッセージを取得する。<br>
	 * 取得したメッセージにパラメーター置き換え記述子が埋め込まれている場合は<br>
	 * 引数で指定されたパラメーター配列の値に置き換えたメッセージを返す。<br>
	 * パラメーター置き換え記述子は、{}で囲まれた0～nの数値で指定する。<br>
	 * <br>
	 * 例）<br>
	 * メッセージ = このメッセージは{0}曜日に{1}さんから送信されました。<br>
	 * パラメーター= parameteres[0] = 火<br>
	 *               parameteres[1] = 鈴木<br>
	 * <br>
	 * 上記の場合、返されるメッセージは<br>
	 * <br>
	 * このメッセージは火曜日に鈴木さんから送信されました。<br>
	 * <br>
	 * となる<br>
	 * <br>
	 * @param	key			メッセージキーコード
	 * @param	parameters	メッセージに埋め込むパラメーター配列
	 * @return	String		メッセージ
	 * **/
    public static String get(String key,Object[]parameters)
    {
        if(parameters==null || parameters.length < 1 )
        {
        	return get(key);
        }
        
        for(int cnt=0;cnt<parameters.length;cnt++)
        {
            if(parameters[cnt]==null)
            {
            	get(key);
            }
            parameters[cnt]=stuffingGetMessageFinalizer(parameters[cnt]+"",true); // DECODING 時の誤判定の回避
        }
        String message;

        message = get(key);
        message=java.text.MessageFormat.format(message,parameters); // 基本的に NO-EXCEPTION and NO-ERROR か？

        return getMessageFinalizer(message);
    }
	
    private static String getMessageFinalizer(String message)
    {
        return stuffingGetMessageFinalizer(message,false);
    }
    
    private static String stuffingGetMessageFinalizer(String message,boolean encoding)
    {
        return stuffing(message,'\\',"S\\"," \\",encoding);
    }
    
    private static String stuffing(String string,char controlChr,String encodedChrs,String decodedChrs,boolean encoding) // ROUTINE
    {
        StringBuffer buffer=new StringBuffer();

        try {

        for(int stringIndex=0;stringIndex<string.length();stringIndex++)
        {
            char chr=string.charAt(stringIndex);

            if(encoding) // ENCODING
            {
                int cnt;

                for(cnt=0;cnt<decodedChrs.length();cnt++)
                    if(decodedChrs.charAt(cnt)==chr)
                        break;

                if(cnt<decodedChrs.length())
                {
                    buffer.append(controlChr);
                    buffer.append(encodedChrs.charAt(cnt));
                }
                else
                {
                    buffer.append(chr);
                }
            }
            else // DECODING
            {
                if(chr==controlChr)
                {
                    int cnt;

                    stringIndex++;

                    if(stringIndex==string.length())
                        throw new Error(string);

                    chr=string.charAt(stringIndex);

                    for(cnt=0;cnt<encodedChrs.length();cnt++)
                        if(encodedChrs.charAt(cnt)==chr)
                            break;

                    if(cnt==encodedChrs.length())
                        throw new Error(string);

                    buffer.append(decodedChrs.charAt(cnt));
                }
                else
                {
                    buffer.append(chr);
                }
            }
        }
        }
        catch(Error e)
        {
            e.printStackTrace();
            throw e;
        }
        return buffer+"";
    }
}
