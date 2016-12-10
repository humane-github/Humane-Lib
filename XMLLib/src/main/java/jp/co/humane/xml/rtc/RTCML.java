package jp.co.humane.xml.rtc;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RTCML
{
	//コマンド一覧
	public static String CMD_GET_PROPERTY = "GET_PROPERTY";
	public static String CMD_SEND_PROPERTY = "SEND_PROPERTY";
	public static String CMD_RESPONSE = "RESPONSE";
	
	//
	public static String RTCML_GETPROPERTY = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
												"<rtcml command=\"GET_PROPERTY\"/>";
	
	public static String ELEMENT_FORMAT = "<attribute name=\"%s\">%s</attribute>\n";
	public static String COMP_FORMAT = "<component name=\"%s\">%s</component>\n";
	public static String FORMAT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
									"<rtcml command=\"%s\">%s</rtcml>\n";

	//RTCML解析パーサー
	private RTCMLParser m_parser = null;
	
	/**
	 * コンストラクタ
	 * **/
	public RTCML(){}
	
	/**
	 * RTCMLをXMLにシリアライズする
	 * **/
	public String serialize()
	{
		if( m_parser == null ){return null;}
		else{return serialize(m_parser.getTargetComponent(),m_parser.getCommand(),m_parser.getAttributeMap());}
	}
	/**
	 * RTCMLをXMLにシリアライズする
	 * 
	 * @param	replaceCommand rtcmlタグのcommand属性値を置き換える
	 * **/
	public String serialize(String target,String command,HashMap<String,String> params)
	{
		String components = serializeComponentTag(target, command, params);
		String result = String.format(FORMAT,command, components);
		return result;
	}
		
	/**
	 * <component>タグのみシリアライズする
	 * **/
	public String serializeComponentTag()
	{
		if( m_parser == null ){return null;}
		else{return serializeComponentTag(m_parser.getTargetComponent(),m_parser.getCommand(),m_parser.getAttributeMap());}
	}
	
	/**
	 * <component>タグのみシリアライズする
	 * **/
	public String serializeComponentTag(String target,String command,HashMap<String,String> params)
	{
		StringBuffer elements = new StringBuffer();
		for( String key : params.keySet() )
		{
			String val = params.get(key);
			String element = String.format(ELEMENT_FORMAT, key,val);
			elements.append(element);
		}
		String components = String.format(COMP_FORMAT, target,elements.toString());
		return components;
	}
	
	/**
	 * RTCMLを解析する
	 * 
	 * @param	xml	XML文字列
	 * **/
	public void parse(String xml) throws ParserConfigurationException,SAXException,IOException
	{
		//パーサーインスタンス生成
		m_parser = new RTCMLParser();
		//SAXパーサーの初期化
		SAXParserFactory spfactory = SAXParserFactory.newInstance();
		SAXParser parser = spfactory.newSAXParser();
		//解析開始
		parser.parse(new InputSource(new StringReader(xml)), m_parser);
	}
	
	public void parse(String componentName,String command,HashMap<String,String> map) throws ParserConfigurationException,SAXException,IOException
	{
		String xml = serialize(componentName,command, map);
		parse(xml);
	}
	
	/**
	 * コマンドを取得する
	 * **/
	public boolean isGetProperty(){return CMD_GET_PROPERTY.equals(m_parser.getCommand());}
	public boolean isSetProperty(){return CMD_SEND_PROPERTY.equals(m_parser.getCommand());}
	public boolean isResponse(){return CMD_RESPONSE.equals(m_parser.getCommand());}
	
	/**
	 * RTCMLを適用するコンポーネント名を取得する
	 * 
	 * @return	String
	 * **/
	public String getTargetComponent()
	{
		return m_parser.getTargetComponent();
	}
	
	public void setTargetComponent(String c)
	{
		m_parser.setTargetComponent(c);
	}
	
	public void setCommand(String c)
	{
		m_parser.setCommand(c);
	}
	
	/**
	 * RTCMLの属性値を取得する
	 * 
	 * @param	name	属性名
	 * @return	String
	 * **/
	public String getString(String name) throws AttributeNotFoundException
	{
		if( !m_parser.getAttributeMap().containsKey(name) )
		{
			throw new AttributeNotFoundException(name+" not found.");
		}
		return m_parser.getAttributeMap().get(name);
	}
	
	/**
	 * RTCMLの属性値を取得する
	 * 
	 * @param	name	属性名
	 * @return	int
	 * **/
	public int getInt(String name) throws AttributeNotFoundException
	{
		int result = 0;
		String val = getString(name);
		if( val == null || val.length() < 1 ){return result;}
		try
		{
			result = Integer.parseInt(val);
		}
		catch( NumberFormatException ex )
		{
			result = 0;
		}
		return result;
	}
	
	/**
	 * RTCMLの属性値を取得する
	 * 
	 * @param	name	属性名
	 * @return	int
	 * **/
	public double getDouble(String name) throws AttributeNotFoundException
	{
		double result = 0;
		String val = getString(name);
		if( val == null || val.length() < 1 ){return result;}
		try
		{
			result = Double.parseDouble(val);
		}
		catch( NumberFormatException ex )
		{
			result = 0;
		}
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<rtcml command=\"SET_PROPERTY\">"+
				"\t<component name=\"FaceDetect\">\n"+
				"\t\t<attribute name=\"DETECT_THRESHOLD\">10</attribute>\n" +
				"\t\t<attribute name=\"FEATURES_MAX_CORNERS\">50</attribute>\n" +
				"\t\t<attribute name=\"FEATURES_MIN_DISTANCE\">5</attribute>\n" +
				"\t\t<attribute name=\"FEATURES_QUALITY_LEVEL\">0.01</attribute>\n" +
				"\t\t<attribute name=\"ShowPreviewDialog\">1</attribute>\n" +
				"\t\t<attribute name=\"teststring\">テスト</attribute>\n" +
				"\t</component>\n"+
				"</rtcml>";
				
		RTCML rtcml = new RTCML();
		try
		{
			rtcml.parse(xml);
			System.out.println("DETECT_THRESHOLD="+rtcml.getInt("DETECT_THRESHOLD"));
			System.out.println("FEATURES_QUALITY_LEVEL="+rtcml.getDouble("FEATURES_QUALITY_LEVEL"));
			System.out.println("teststring="+rtcml.getString("teststring"));
			System.out.println("target="+rtcml.getTargetComponent());
			
			//System.out.println("serialize="+rtcml.serialize());
			System.out.println("serialize="+rtcml.serializeComponentTag());
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	
	}

}
