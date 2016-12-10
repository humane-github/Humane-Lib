package jp.co.humane.io;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class TextFileReader {


	private File file = null;
	private FileReader reader = null;
	private BufferedReader br = null;

	private FILE_STATUS lastStatus = FILE_STATUS.SUCCESS;

	public TextFileReader( String path )
	{
      file = new File(path);
	}

	public void open() throws FileNotFoundException
	{
    	reader = new FileReader(file);
    	br = new BufferedReader(reader);
	}

	public String read() throws IOException
	{
		String value = null;

		/**
		 * ファイルを開いていない

		 * **/
		if( reader == null || br == null )
		{
			lastStatus = FILE_STATUS.FILE_CLOSED;
			return null;
		}

    	/**
    	 * 1行読み込み
    	 * **/
    	 value = br.readLine();

    	 /**
    	  * 最後まで読み込み完了

    	  * **/
    	 if (value == null )
    	 {
    		 lastStatus = FILE_STATUS.EOF;
    		 return value;
    	 }

    	 /**
    	  * まだ読み込む行がある
    	  * **/
    	 lastStatus = FILE_STATUS.SUCCESS;
    	 return value;
	}

	// ファイル読み込み時のステータスを返す
	public FILE_STATUS getStatus(){return lastStatus;}

	public void close()
	{
		try
		{
			if( br != null ) br.close();
			if( reader != null ) reader.close();
		}
		catch( Exception e ){}
	}

}
