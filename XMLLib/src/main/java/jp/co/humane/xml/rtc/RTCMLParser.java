package jp.co.humane.xml.rtc;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RTCMLParser extends DefaultHandler
{
	private static final String ROOT_TAG_NAME = "rtcml";
	private static final String ROOT_TAG_CMD_ATTR = "command";
	private static final String COMPONENT_TAG_NAME = "component";
	private static final String COMPONENT_TAG_ATTR_NAME = "name";
	private static final String TAG_NAME = "attribute";
	private static final String ATTR_NAME = "name";
	//StartElements実行後に真となる
	private boolean m_ready = false;
	//指定されたコマンド。コマンドは以下を指定可能
	//GET_PROPERTY:コンポーネントのコンフィグレーション要求
	//SET_POPERTY:コンポーネントのコンフィグレーション設定要求
	private String m_command = null;
	public String getCommand(){return m_command;}
	public void setCommand(String c){m_command = c;}
	//処理中の属性名
	private String m_processingConfigname = null;
	//処理中の属性値
	private String m_processingConfigvalue = null;
	//ターゲット名
	private String m_targetComponentName = null;
	public String getTargetComponent(){return m_targetComponentName;}
	public void setTargetComponent(String c){m_targetComponentName = c;}
	
	//XMLの属性値を持つ
	private HashMap<String,String> m_attr = new HashMap<String,String>();
	public HashMap<String,String> getAttributeMap(){return m_attr;}
	
	/**
	 * コンストラクタ
	 * 
	 * **/
	public RTCMLParser(){}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		if( qName.equals(ROOT_TAG_NAME))
		{
			m_command = attributes.getValue(ROOT_TAG_CMD_ATTR);
		}
		if( qName.equals(COMPONENT_TAG_NAME))
		{
			m_targetComponentName = attributes.getValue(COMPONENT_TAG_ATTR_NAME);
		}
		if( qName.equals(TAG_NAME) )
		{
			m_processingConfigname = attributes.getValue(ATTR_NAME);
			m_ready = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		m_attr.put(m_processingConfigname, m_processingConfigvalue);
		m_ready = false;
		m_processingConfigname = null;
		m_processingConfigvalue = null;
	}


	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException
	{
		if( m_ready )
		{
			m_processingConfigvalue = new String(ch, start, length);			
		}
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
