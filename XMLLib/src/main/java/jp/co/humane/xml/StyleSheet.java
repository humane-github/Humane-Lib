package jp.co.humane.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;

public class StyleSheet extends Xml
{

	private HashMap<String,StyleAttribute> styles = new HashMap<String,StyleAttribute>();
	private String[] styleSheetString = new String[0];

	/**
	 * �R���X�g���N�^
	 *
	 * @param	xml	�X�^�C���V�[�g���擾����XML
	 *
	 * **/
	public StyleSheet( Xml	xml,String[] styleSheet	)
	{
		setOwnerDocument(xml.getOwnerDocument());
		setRootElement(xml.getRootElement());
		this.styleSheetString = styleSheet;
	}

	/**
	 * �w�肵���N���X�̃X�^�C�����擾����
	 * **/
	public StyleAttribute getStyle( String name )
	{
		StyleAttribute attr = styles.get(name);
		if( attr == null ){attr = styles.get("."+name);}
		return attr;
	}

	/**
	 * �w�肵���N���X�̃X�^�C�����X�V����
	 * **/
	public void setStyle( String className, String propertyName, String value )
	{
		StyleAttribute styleAttr = styles.get(className);
		styleAttr.put(propertyName, value);
	}

	/**
	 * �X�^�C���V�[�g�̐ݒ���L�����o�X�ɓK�p����
	 * **/
	public void apply()
	{
		StringBuilder builder = new StringBuilder();

		/**
		 * CDATA�Z�N�V�����ɐݒ肵���X�^�C���V�[�g�̒l���擾
		 * **/
		CDATASection cdata = getChildCDATANode(getRootElement());

		for( String className : styles.keySet() )
		{
			builder.append(className).append(" {");
			StyleAttribute styleAttr = styles.get(className);
			if( styleAttr == null ){ continue; }
			for( String propName : styleAttr.keySet() )
			{
				String value = styleAttr.get(propName);
				builder.append(propName).append(":").append(value).append(";");
			}
			builder.append("}\n");
		}

		cdata.setNodeValue(builder.toString());
	}

	/**
	 * XML�G�������g�����
	 *
	 * **/
	public void parse()
	{
		/**
		 * style�^�O���擾
		 * **/
		List<Element> styleElements = searchElement("style");
		if( styleElements.size() < 1 ){return;}
		setRootElement(styleElements.get(0));

		/**
		 * CDATA�Z�N�V�����ɐݒ肵���X�^�C���V�[�g�̒l���擾
		 * **/
		CDATASection cdata = getChildCDATANode(getRootElement());
		String cdataValue = cdata.getNodeValue();
		/**
		 * �X�^�C���V�[�g�̃N���X���ƁA�l�ɕ�������
		 * **/
		String[] cdataList = cdataValue.split("\t");
		if( this.styleSheetString != null && this.styleSheetString.length > 0 )
		{
			cdataList = this.styleSheetString;
		}
		for( String cdataLine : cdataList )
		{
			String[] tokens = splitCDATA(cdataLine);
			if( tokens == null || tokens.length < 2 ){ continue; }
			String className = tokens[0].trim();
			StyleAttribute values = splitStyleSheet(tokens[1]);
			styles.put(className, values);
		}
	}


	private String[] splitCDATA( String line )
	{
		if( line == null ){ return null; }
		line = line.trim();
		if( line.length() < 1 ){ return null; }
		if( line.charAt(0) != '.' ){ return null; }

		boolean flg = false;
		String className = "";
		String styleValue = "";
		for(int i = 0;;i++)
		{
			if( i >= line.length() ){ break; }

			if( flg == false )
			{
				className = className + line.charAt(i);
			}
			else
			{
				styleValue = styleValue + line.charAt(i);
			}

			if( line.charAt(i) == '{' )
			{
				flg = true;
			}
		}

		className = className.trim();
		className = className.replace("{", "");
		className = className.replace("}", "");
		styleValue = styleValue.trim();
		styleValue = styleValue.replace("{", "");
		styleValue = styleValue.replace("}", "");

		return new String[]{className,styleValue};
	}


	private StyleAttribute splitStyleSheet(String style)
	{
		StyleAttribute result = new StyleAttribute();

		if( style == null || style.length() < 1 ){ return null;}

		String[] style1 = style.split(";");
		if( style1 == null || style1.length < 1 ){ return null;}
		for( String styleToken : style1 )
		{
			String[] tmp = styleToken.split(":");
			if( tmp == null || tmp.length < 2 ){ continue; }
			String attr = tmp[0];
			String val = tmp[1];
			result.put(attr, val);
		}

		return result;
	}

	public class StyleAttribute
	{
		private HashMap<String,String> properties = new HashMap<String,String>();

		/**
		 * �R���X�g���N�^
		 * **/
		public StyleAttribute()
		{
		}

		public void put( String attr, String val)
		{
			properties.put(attr,val);
		}

		public String get( String attr )
		{
			return properties.get(attr);
		}

		public Set<String> keySet()
		{
			return properties.keySet();
		}

	}
}
