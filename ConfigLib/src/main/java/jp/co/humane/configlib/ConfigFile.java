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
	 * �R���X�g���N�^
	 * **/
	public ConfigFile()
	{
		
	}
	
	/**
	 * �ݒ�t�@�C���ǂݍ���
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
	 * �ݒ���𕶎��񉻂���
	 * 
	 * **/
	public String serialize(ConfigSerializer serializer)
	{
		if( serializer == null ){return "";}
		return serializer.serialize(m_prop);
	}
	
	/**
	 * �ݒ���̃L�[�ꗗ���擾����
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
	 * Float�^�Őݒ�擾
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
	 * String�^�Őݒ�擾
	 * **/
	public String getString(String key)
	{
		return m_prop.getProperty(key);
	}
	
	/**
	 * Int�^�Őݒ�擾
	 * **/
	public int getInt(String key)
	{
		return Integer.parseInt(getString(key));
	}
}
