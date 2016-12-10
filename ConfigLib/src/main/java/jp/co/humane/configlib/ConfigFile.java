package jp.co.humane.configlib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigFile
{
	private Properties m_prop = null;
	private ConfigSerializer m_serializer = null;
	
	/**
	 * コンストラクタ
	 * **/
	public ConfigFile()
	{
		
	}
	
	/**
	 * 設定ファイル読み込み
	 * **/
	public void load(String file) throws IOException
	{
		File f = new File(file);
		if(!f.exists()){throw new IOException(file+" not found");}
		if( !f.canRead() ){throw new IOException(file+" can not read");}
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
		InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
		//InputStreamReader isr = new InputStreamReader(is);
		m_prop = new Properties();
		m_prop.load(isr);
		isr.close();
		is.close();
	}

	/**
	 * 設定情報を文字列化する
	 * 
	 * **/
	public String serialize(ConfigSerializer serializer)
	{
		if( serializer == null ){return "";}
		return serializer.serialize(m_prop);
	}
	
	/**
	 * 設定情報のキー一覧を取得する
	 * **/
	public List<String> getAllKeys()
	{
		ArrayList<String> keys = new ArrayList<String>();
		while( m_prop.keys().hasMoreElements() )
		{
			String key = (String)m_prop.keys().nextElement();
			keys.add(key);
		}
		return keys;
	}
	
	/**
	 * Float型で設定取得
	 * **/
	public float getFloat(String key)
	{
		float res = 0;
		try
		{
			res = Float.parseFloat(getString(key));			
		}
		catch( Exception ex ){}
		return res;
	}
	
	/**
	 * String型で設定取得
	 * **/
	public String getString(String key)
	{
		return m_prop.getProperty(key);
	}
	
	/**
	 * Int型で設定取得
	 * **/
	public int getInt(String key)
	{
		return Integer.parseInt(getString(key));
	}
}
