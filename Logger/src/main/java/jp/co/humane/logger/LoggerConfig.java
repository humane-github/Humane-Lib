package jp.co.humane.logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class LoggerConfig
{
	private Properties m_prop = null;
	
	public LoggerConfig()
	{
		
	}
	
	public void load(String file) throws IOException
	{
		File f = new File(file);
		if(!f.exists()){throw new IOException(file+" not found");}
		if( !f.canRead() ){throw new IOException(file+" can not read");}
		
		//InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
		//InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
		m_prop = new Properties();
		m_prop.load(new BufferedReader(new FileReader(f)));
		//isr.close();
		//is.close();	
	}
	
	public String getString(String key)
	{
		return m_prop.getProperty(key);
	}
	
	public int getInt(String key)
	{
		return Integer.parseInt(getString(key));
	}
}
