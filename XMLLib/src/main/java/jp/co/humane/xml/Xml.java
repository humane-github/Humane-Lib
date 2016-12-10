package jp.co.humane.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.DOMSerializer;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class Xml
{
	// XMLファイルの文字コード
	public final static String ENCODING = "utf-8";

	private Document ownerDocument = null;
	private Element rootElement = null;

	public Xml(){}

	/**
	 * XMLを解析してDOMを作る
	 * **/
	public void parse( File file ) throws XMLException
	{
		try
		{
			ownerDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new FileReader(file)));
			rootElement = ownerDocument.getDocumentElement();
		}
		catch( IOException e)
		{
			throw new XMLException(e.getMessage(),e);
		} catch (SAXException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * XMLを解析してDOMを作る
	 * **/
	public void parse(String xml) throws XMLException
	{
		try
		{
			ownerDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
			rootElement = ownerDocument.getDocumentElement();
		}
		catch( IOException e)
		{
			throw new XMLException(e.getMessage(),e);
		} catch (SAXException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 指定したタグを検索する
	 *
	 * @param	tagName	検索するタグ名
	 * @return	Element
	 * **/
	public List<Element> searchElement(String tagName )
	{
		ArrayList<Element> result = new ArrayList<Element>();
		searchElement(rootElement,tagName,null,null,result);
		return result;
	}

	/**
	 * タグ名と属性値の組み合わせで検索する
	 *
	 * @param	String tagName		タグ名
	 * @param	String attrName		属性名
	 * @param	String	attrValue	属性値
	 * @return	Element
	 * **/
	public List<Element> searchElement( String tagName, String attrName, String attrValue)
	{
		ArrayList<Element> result = new ArrayList<Element>();
		searchElement(rootElement,tagName,attrName,attrValue,result);
		return result;
	}

	/**
	 * タグ名と属性値の組み合わせで検索する
	 *
	 * @param	String tagName		タグ名
	 * @param	String attrName		属性名
	 * @param	String	attrValue	属性値
	 * @return	Element
	 * **/

	public void searchElement( Element parent,String tagName,String attrName,String attrValue,List<Element> result )
	{
		if( parent == null ){parent = rootElement;}
		NodeList nodeList = parent.getChildNodes();
		searchElement(nodeList,tagName,attrName,attrValue,result,true);
	}

	/**
	 * タグ名と属性値の組み合わせで検索する
	 *
	 * @param	String tagName		タグ名
	 * @param	String attrName		属性名
	 * @param	String	attrValue	属性値
	 * @param	deep				下の階層まで探す場合,true
	 * @return	Element
	 * **/
	public void searchElement( Element parent,String tagName,String attrName,String attrValue,List<Element> result , boolean deep )
	{
		if( parent == null ){parent = rootElement;}
		NodeList nodeList = parent.getChildNodes();
		searchElement(nodeList,tagName,attrName,attrValue,result,deep);
	}

	/**
	 * タグのテキスト値を取得する
	 * **/
	public String getText(Node node)
	{
		Text text = getTextNode(node);
		if( text != null )
		{
			return text.getNodeValue();
		}
		return null;
	}

	/**
	 * タグのテキスト値を更新する
	 * **/
	public void setText(Node node,String value)
	{
		Text text = getTextNode(node);
		if( text != null )
		{
			text.setNodeValue(value);
		}
	}

	/**
	 * タグのTextノードを取得する
	 * **/
	public Text getTextNode(Node node)
	{
		NodeList list = node.getChildNodes();
		for( int i = 0 ; i < list.getLength() ; i++ )
		{
			Node child = list.item(i);
			if( child != null && child.getNodeType() == Node.TEXT_NODE )
			{
				return ((Text)child);
			}
		}
		return null;
	}

	/**
	 * 指定した名称のタグを再帰的に検索する
	 *
	 *
	 * **/
	private void searchElement(NodeList nodeList, String tagName, String attrName, String attrValue,List<Element> result,boolean deep)
	{
		for(int i = 0 ; i < nodeList.getLength() ; i++ )
		{
			Node node = nodeList.item(i);
			if( node == null ||	node.getNodeType() != Node.ELEMENT_NODE ){continue;}

			if ( tagName == null || ((Element)node).getTagName().equals(tagName) )
			{
				//systemOutNode(node);
				// 指定した属性が存在する場合
				if( attrValue == null && attrName != null && (((Element)node).getAttribute(attrName) != null))
				{

					result.add((Element)node);
				}
				// 指定した属性値が存在する場合
				else if( (attrName == null) || (attrName != null &&
					(((Element)node).getAttribute(attrName).equals(attrValue))))
				{
					result.add((Element)node);
				}
				else if( node.hasChildNodes() && deep )
				{
					searchElement(node.getChildNodes(),tagName,attrName,attrValue,result,deep);
				}
			}
			else if( node.hasChildNodes() && deep )
			{
				searchElement(node.getChildNodes(),tagName,attrName,attrValue,result,deep);
			}
		}
		return;
	}

	/**
	 * 指定したDocumentを使用して自身のコピーを作成する
	 * （importNodeを利用すると、キャンバスへの描画が正しく行われない不具合のため、createElementNSでElementを新規作成している）
	 *
	 * @param	doc	Elementのコピーを作成するDocument
	 *
	 * **/
	protected Element copy( Xml svgXml, String nameSpace, Element rootElement )
	{
		Element element = rootElement==null?getRootElement():rootElement;

		// Elementのコピーを生成
		Element copyElement = null;
		if( nameSpace == null )
		{
			copyElement = svgXml.getOwnerDocument().createElement(element.getTagName());
		}
		else
		{
			copyElement = svgXml.getOwnerDocument().createElementNS(nameSpace, element.getTagName());
		}

		// 属性をコピーエレメントに追加する
		NamedNodeMap attrs = element.getAttributes();
		for( int i = 0 ; i < attrs.getLength() ; i++ )
		{
			Node node = attrs.item(i);
			if( node.getNodeType() != Node.ATTRIBUTE_NODE ){continue;}

			String attrName = ((Attr)node).getName();
			String attrValue = ((Attr)node).getValue();
			String ns = ((Attr)node).getNamespaceURI();
			if( attrName.startsWith("c:"))
			{
				copyElement.setAttributeNS(ns,attrName, attrValue);
			}
			else
			{
				copyElement.setAttributeNS(ns, attrName, attrValue);
			}

		}

		// 子エレメントもコピーする
		for( int i = 0 ; i < element.getChildNodes().getLength() ; i++ )
		{
			Node node = element.getChildNodes().item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE )
			{
				copyElement.appendChild(copy(svgXml,nameSpace,((Element)node)));
			}
			else if( node.getNodeType() == Node.TEXT_NODE )
			{
				String tt=node.getNodeValue();
				//Text textNode = ((Text)node);
				//String tt = textNode.getTextContent();
				Text newText = svgXml.getOwnerDocument().createTextNode(tt);
				copyElement.appendChild(newText);
			}
		}

		return copyElement;
	}

	/**
	 * ノードのノード値のCDATA要素を取り出す
	 * @param node ノード
	 * @return &ltXX&gt???&lt/XXt&gtの???のTEXTノード
	 */
	public CDATASection getChildCDATANode(Node node)
	{
		NodeList childlen = node.getChildNodes();
		for (int m=childlen.getLength(),k=0; k<m; k++) {
			Node child = childlen.item(k);
			if (child.getNodeType() == Node.CDATA_SECTION_NODE)
				return (CDATASection)child;
		}
		return null;
	}

	/**
	 * エレメントを追加する
	 * **/
	public void add( Element e)
	{
		add(getRootElement(),e);
	}

	/**
	 * 指定したエレメント下に追加する
	 * **/
	public void add( Element root,Element e)
	{
		root.appendChild(e);
	}

	/**
	 * エレメントを作成する
	 *
	 * @param	ns			名前空間
	 * @param	tagName		タグ名
	 * **/
	public Element createElement( String ns,String tagName )
	{
		return getOwnerDocument().createElementNS(ns, tagName);
	}


	public String toString()
	{
		Transformer transformer = null;
        try
        {
        	TransformerFactory factory = TransformerFactory.newInstance();
        	transformer = factory.newTransformer();
        }
        catch (TransformerConfigurationException e)
        {
            e.printStackTrace();
            return null;
        }

        transformer.setOutputProperty("indent",   "yes");
        transformer.setOutputProperty("encoding", ENCODING);

        try
        {
        	StringWriter writer = new StringWriter();
        	transformer.transform(new DOMSource( ownerDocument ),
                                  new StreamResult( writer ) );
        	return writer.toString();

        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
        return null;
	}

    /**
     * SVGDocumentからSVG文字列を取り出す。
     * @param doc SVGDocument
     * @return 取り出したSVG文字列
     */
    public String serialize() {
    	String svg = null;
    	try {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		OutputFormat format = new OutputFormat(getOwnerDocument(), "UTF-8", true);
    		format.setLineWidth(256);
    		DOMSerializer serializer = new XMLSerializer(baos, format).asDOMSerializer();
    		serializer.serialize(getOwnerDocument());
    		svg = baos.toString("UTF-8");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        //return removeRedundancy(svg);
        return svg;
    }
    private static int tab=0;
    /**
     * ノードの出力
     * @param node	出力ノード
     */
    public static void systemOutNode(Node node){
    	tab=0;
	    systemOutNodeWalk(node);
    }
    private static void systemOutNodeWalk(Node node){
    	putNodeInfo(node);
		tab++;
		for(Node child=node.getFirstChild();child!=null;child=child.getNextSibling()){
			systemOutNodeWalk(child);
		}
		tab--;
    }
    private static void putNodeInfo(Node node){
    	if(node.getNodeType()==Node.TEXT_NODE && node.getNodeValue() != null &&
    			node.getNodeValue().trim().equals("")){
    		return;
    	}
		if(node.getNodeType()==Node.ELEMENT_NODE){
	    	for(int i=0;i<tab;i++){
			    System.out.print(" ");
			}
			System.out.print("<"+node.getNodeName());
			if(node.getAttributes() != null){
				for(int i = 0; i < node.getAttributes().getLength(); i ++){
					System.out.print(" "+node.getAttributes().item(i).getNodeName());
					System.out.print("=\""+node.getAttributes().item(i).getNodeValue()+"\"");
				}
			}
			System.out.print(">");
			if(!node.getNodeName().toLowerCase().equals("text")){
				System.out.println("");
			}
		}
		if(node.getNodeType()==Node.TEXT_NODE){
			if(node.getNodeValue() != null){
				if(!node.getNodeValue().trim().equals("")){
					System.out.println(node.getNodeValue());
				}
			}
		}
    }

	public Element getRootElement() {return rootElement;}
	public void setRootElement(Element root)
	{
		this.rootElement = root;
		setOwnerDocument(root.getOwnerDocument());
	}
	public Document getOwnerDocument() {return ownerDocument;}
	public void setOwnerDocument(Document dom) {this.ownerDocument = dom;}


}
