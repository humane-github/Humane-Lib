package jp.co.humane.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;

public class TextFileWriter
{

	/**
	 * 書きこみファイル
	 * **/
	private File file = null;

	/**
	 * ファイル書きこみ用オブジェクト

	 * **/
	private PrintWriter pw = null;

	/**
	 * コンストラクタ
	 *
	 * @param	path	出力先ファイルパス
	 * **/
	public TextFileWriter( String path )
	{
		file = new File(path);
		if( !file.exists() )
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

	/**
	 * 書きこみファイルを開く

	 * **/
	public void open() throws IOException
	{
		open("UTF-8");
	}

	/**
	 * 書きこみファイルを文字コード指定で開く
	 *
	 * @param	encoding	文字コード

	 * **/
	public void open(String encoding) throws IOException,IllegalCharsetNameException
	{
		/**
		 * 指定した文字コードがサポートされているかチェック
		 * サポートされていない場合は、IllegalCharsetNameExceptionを投げる
		 * **/
		Charset.isSupported(encoding);
		pw = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(file),encoding)));
	}

	/**
	 * 文字列をファイルに出力する

	 * **/
	public void writeLine( String value )
	{
		pw.println(value);
	}

	/**
	 * ファイルを閉じる
	 * **/
	public void close()
	{
		if( pw != null ){pw.close();}
	}


}
