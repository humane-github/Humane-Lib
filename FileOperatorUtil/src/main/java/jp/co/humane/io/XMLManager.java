package jp.co.humane.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.DOMSerializer;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * XML文書を操作するクラス
 *
 * @author T.Ichikawa
 *
 */
public class XMLManager{

	/** 操作対象のXML文書 **/
	private Document document = null;

	/** ルート要素 **/
	private Element root = null;

	/** 検索結果の要素 **/
	private Element findResultElement = null;

	/** 検索結果の親要素 **/
	private Element findResultParentElement = null;

	/**
	 * カレント要素の状態
	 * 	true=カレント要素なし、false=カレント要素あり
	 * 	find系メソッドで検索結果が無い場合はtrue
	 * 	moveFirst系メソッド発行時にfalseが設定される
	 * 	moveNext系メソッドで次の要素が無かったらtrue
	 */
	private boolean EOF = false;

	/**
	 * カレント要素の状態を設定する
	 * 	値は検索／移動系メソッドで設定する
	 *
	 * @param b カレント要素の状態
	 */
	private void setEOF(boolean b){
		EOF = b;
	}

	/**
	 * カレント要素の有無を返す
	 * @return true=カレント要素なし、false=カレント要素あり
	 */
	public boolean EOF(){return EOF;}

	/**
	 * コンストラクタ
	 */
	public XMLManager(){

	}

	/**
	 * XMLをシリアライズする
	 * @return	シリアライズしたＸＭＬ文字列
	 */
	public String serialize(){
    	String ret = null;
    	try {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		OutputFormat format = new OutputFormat(document, "UTF-8", true);
    		format.setLineWidth(256);
    		DOMSerializer serializer = new XMLSerializer(baos, format).asDOMSerializer();
    		serializer.serialize(document);
    		ret = baos.toString("UTF-8");
    	} catch (Exception e) {
    		//e.printStackTrace();
    	}
        return ret;
	}

	/**
	 * 現在取得しているXML文書を破棄する
	 *
	 */
	private void closeDocument(){
		document = null;
		root = null;
	}

	/**
	 * XMLファイルを読み込む
	 *
	 * @param filePath 読み込むXMLファイル
	 * @return true:成功、false:失敗
	 */
	public boolean openXMLFile(String filePath)
	{
		try{
			//ドキュメントビルダーファクトリを生成
		    DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();

		    //ドキュメントビルダーを生成
		    DocumentBuilder builder = dbfactory.newDocumentBuilder();

		    //パースを実行してDocumentオブジェクトを取得
		    document = builder.parse(new File(filePath));

		    //ルート要素を取得
		    root = document.getDocumentElement();

		}catch(Exception ex){
			//AppLog.writeError(ex.toString());
			closeDocument();
			return false;
		}

		return true;
	}

	/**
	 * XML文字列を設定する
	 *
	 * @param xmlText XML文字列
	 * @return true:成功、false:失敗
	 */
	public boolean setXMLText(String xmlText)
	{
		try{
			InputSource source = new InputSource(new StringReader(xmlText));
			source.setEncoding("UTF-8");

			//ドキュメントビルダーファクトリを生成
		    DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();

		    //ドキュメントビルダーを生成
		    DocumentBuilder builder = dbfactory.newDocumentBuilder();

		    //パースを実行してDocumentオブジェクトを取得
		    document = builder.parse(source);

		    //ルート要素を取得
		    root = document.getDocumentElement();

		}catch(Exception ex){
			//AppLog.writeError(ex);
			closeDocument();
			return false;
		}

		return true;
	}


	/**
	 *タグ名をキーにしてルート要素配下を再帰的に検索する
	 *
	 * @param tagName 検索するタグ名
	 * @return true:該当要素検出、false:該当要素なし
	 */
	public boolean findTagFromRoot(String tagName)
	{
		if ( root == null ) {
			return false;
		}
		try {
			String name = root.getTagName();
			if ( name == null || name.trim().equals("")) {
				return false;
			}

			if (name.toUpperCase().equals(tagName.toUpperCase())){
				findResultElement = root;
			}else{
				NodeList children = root.getChildNodes();
				findResultElement = findTag_recurs(children, tagName);
			}

			if (findResultElement!=null){
				setEOF(false);
				if (findResultElement.getParentNode().getNodeType()==Node.ELEMENT_NODE){
					findResultParentElement = (Element)findResultElement.getParentNode();
				}else{
					findResultParentElement = null;
				}
				return true;
			}else{
				setEOF(true);
				findResultParentElement = null;
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 *タグ名をキーにして指定要素群を再帰的に検索する
	 *
	 * @param elements 検索する要素群
	 * @param tagName 検索するタグ名
	 * @return 見つかった要素（見つからなかった場合はnull）
	 */
	private Element findTag_recurs(NodeList elements, String tagName)
	{
		try {
			for(int i=0; i<elements.getLength(); i++){
				Node node = elements.item(i);
				if (node.getNodeType()!=Node.ELEMENT_NODE) continue;

				Element elem = (Element)node;
				if (elem.getTagName().toUpperCase().equals(tagName.toUpperCase())){
					return elem;
				}else{
					NodeList children = elem.getChildNodes();
					if (children!=null && children.getLength()>0){
						Element result = findTag_recurs(children, tagName);
						if (result!=null){
							return result;
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 検索結果のカレント要素のタグ名を返す
	 *
	 * @return タグ名（検索結果が存在しない場合はnull）
	 */
	public String getTagName(){
		if (findResultElement!=null){
			return findResultElement.getTagName();
		}else{
			return null;
		}
	}

	/**
	 * 検索結果のカレント要素の指定した属性の値を返す
	 *
	 * @param	attrName 属性名
	 * @return 属性の値（検索結果が存在しない場合はnull）
	 */
	public String getAttribute(String attrName){
		if (findResultElement!=null){
			return findResultElement.getAttribute(attrName);
		}else{
			return null;
		}
	}

	/**
	 * 検索結果のカレント要素の属性を設定する
	 *
	 * @param AttributeName	属性名
	 * @param attributeValue	属性値
	 */
	public void setAttribute(String AttributeName, String attributeValue)
	{
		if (findResultElement!=null){
			findResultElement.setAttribute(AttributeName, attributeValue);
		}
	}

	/**
	 * 検索結果のカレント要素のタグ値を返す
	 *
	 * @return タグ値（検索結果が存在しない場合はnull）
	 */
	public String getTagValue(){
		if (findResultElement!=null){
			String ret = "";
			NodeList list = findResultElement.getChildNodes();
			for(int i=0; i < list.getLength(); i++)
			{
				if (list.item(i).getNodeType()==Node.TEXT_NODE){
					ret += list.item(i).getNodeValue();

				}else if (list.item(i).getNodeType()==Node.CDATA_SECTION_NODE){
					ret += list.item(i).getNodeValue();
				}
			}
			return ret;
		}else{
			return null;
		}
	}

	/**
	 * カレント要素を次の兄弟要素に移動する
	 * @return true:次の兄弟要素が存在する、false:次の兄弟要素が存在しない
	 */
	public boolean moveNextSibling(){
		return moveSibling(true);
	}

	/**
	 * カレント要素を前の兄弟要素に移動する
	 * @return true:前の兄弟要素が存在する、false:前の兄弟要素が存在しない
	 */
	public boolean movePreviousSibling(){
		return moveSibling(false);
	}

	/**
	 * カレント要素を前後の兄弟要素に移動する
	 * @param moveNext true:次の兄弟要素に移動、false:前の兄弟要素に移動
	 * @return true:移動成功、false:移動先要素なし
	 */
	private boolean moveSibling(boolean moveNext)
	{
		Node siblingNode = findResultElement.getNextSibling();
		while(siblingNode!=null){
			if (siblingNode.getNodeType()==Node.ELEMENT_NODE){
				break;
			}else{
				if (moveNext){
					siblingNode = siblingNode.getNextSibling();
				}else{
					siblingNode = siblingNode.getPreviousSibling();
				}
			}
		}

		if (siblingNode!=null){
			findResultElement = (Element)siblingNode;
			setEOF(false);
			return true;
		}else{
			findResultElement = null;
			setEOF(true);
			return false;
		}
	}

	/**
	 * カレント要素を先頭の子要素に移動する
	 * @return true:子要素が存在する、false:子要素が存在しない
	 */
	public boolean moveFirstChild(){
		return moveChild(true);
	}

	/**
	 * カレント要素を末尾の子要素に移動する
	 * @return true:子要素が存在する、false:子要素が存在しない
	 */
	public boolean moveLastChild(){
		return moveChild(false);
	}

	/**
	 * カレント要素を先頭または末尾の子要素に移動する
	 *
	 * @param moveFirstChild	true:先頭の子要素に移動、false:末尾の子要素に移動
	 * @return true:子要素が存在する、false:子要素が存在しない
	 */
	private boolean moveChild(boolean moveFirstChild){
		NodeList list = findResultElement.getChildNodes();
		Node childNode = null;
		if (moveFirstChild){
			for(int i=0; i<list.getLength(); i++){
				Node node = list.item(i);
				if (node.getNodeType()==Node.ELEMENT_NODE){
					childNode = node;
					break;
				}
			}
		}else{
			for(int i=list.getLength()-1; i>=0; i--){
				Node node = list.item(i);
				if (node.getNodeType()==Node.ELEMENT_NODE){
					childNode = node;
					break;
				}
			}
		}

		if (childNode!=null){
			findResultElement = (Element)childNode;
			findResultParentElement = (Element)findResultElement.getParentNode();
			setEOF(false);
			return true;
		}else{
//			findResultElement = null;
//			findResultParentElement = null;
			setEOF(true);
			return false;
		}
	}

	/**
	 * カレント要素を親要素に移動する
	 * @return true:親要素が存在する、false:親要素が存在しない
	 */
	public boolean moveParent()
	{
		if (findResultParentElement!=null){
			findResultElement = findResultParentElement;
			findResultParentElement = (Element)findResultElement.getParentNode();
			return true;

		}else{
			findResultElement = null;
			findResultParentElement = null;
			setEOF(true);
			return false;
		}


	}

	/**
	 * カレント要素の前の兄弟要素として新規要素を追加する
	 *
	 * @param tagName 新規要素のタグ名
	 * @return true:成功、false:失敗
	 */
	public boolean addSiblingTag(String tagName)
	{
		if (EOF()) return false;
		Element newChild = document.createElement(tagName);
		root.insertBefore(newChild, findResultElement);
		findResultElement = newChild;
		return true;
	}

	/**
	 * カレント要素の子要素として新規要素を追加する
	 *
	 * @param tagName 		新規要素のタグ名
	 * @param moveCurrent	true:新規要素をカレント要素に変更する、false:カレント要素は変更しない
	 * @return true:成功、false:失敗
	 */
	public boolean addChildTag(String tagName, boolean moveCurrent)
	{
		if (EOF()) return false;
		Element newChild = document.createElement(tagName);
		findResultParentElement.appendChild(newChild);
		if (moveCurrent){
			findResultParentElement = newChild;
		}
		return true;
	}

	/**
	 * 指定した文書のカレント要素をこの文書のカレント要素配下にコピーする
	 *
	 * @param source_xml コピーするカレント要素を持つ文書
	 * @return true:成功、false:失敗
	 */
	public boolean copyChild(XMLManager source_xml)
	{
		//if ( source_xml==null && source_xml.EOF() ) return false;
		if ( source_xml==null ) return false;
		if ( source_xml.EOF() ) return false;

		if ( EOF() ) return false;

		//コピー元ノードの複製を取得
		Element srcChild = source_xml.getCurrentClone();

		//要素を再帰コピーする
		ArrayList<Node> childNodes = new ArrayList<Node>();
		childNodes.add(srcChild);
		_copyChild(findResultElement, childNodes);

		return true;
	}

	/**
	 * カレント要素の複製を返す
	 *
	 * @return カレント要素の複製
	 */
	private Element getCurrentClone(){
		return (Element)findResultElement.cloneNode(true);
	}

	/**
	 * 要素を再帰コピーする
	 *
	 * @param target		コピー先要素（この要素の直下にコピーする）
	 * @param sourceList	コピー元要素のリスト
	 */
	private void _copyChild(Element target, ArrayList<Node> sourceList)
	{
		for(Node srcNode : sourceList){
			if (srcNode.getNodeType()!=Node.ELEMENT_NODE) continue;

			//新規要素作成
			Element srcElem = (Element)srcNode;
			String tagName = srcElem.getTagName();
			Element newChild = document.createElement(tagName);
			//要素の属性を設定
			NamedNodeMap nnMap = srcElem.getAttributes();
			for(int index = 0; index<nnMap.getLength(); index++)
			{
				Node attribute = nnMap.item(index);
				if(attribute.getNodeType()==Node.ATTRIBUTE_NODE){
					String name  = attribute.getNodeName();
					String value = attribute.getNodeValue();
					newChild.setAttribute(name, value);
				}
			}
			//作成した要素をカレント要素配下に追加する
			target.appendChild(newChild);

			//子要素を再帰コピーする
			if (srcNode.getChildNodes() != null && srcNode.getChildNodes().getLength() > 0){
				if(srcNode.getNodeType() == Node.ELEMENT_NODE){
					ArrayList<Node> childNodes = new ArrayList<Node>();
					NodeList nodeList = srcNode.getChildNodes();
					for(int index = 0; index < nodeList.getLength(); index ++){
						childNodes.add(nodeList.item(index));
					}
					if (childNodes.size()>0){
						_copyChild(newChild, childNodes);
					}
				}
			}
		}
	}


    public static String convEscape( String data )
    {
    	return convLR(convCRLF(data));
    }
    private static String convCRLF( String data )
	{
		String d = data.replaceAll("_modelgen:crlf_","\n");
		return d.replaceAll("_modelgen:tab_","\t");
	}
    private static String convLR( String data )
	{
		String d = data.replaceAll("_modelgen:L_","<");
		d = d.replaceAll("_modelgen:leftt_","<");
		d = d.replaceAll("_modelgen:rightt_",">");
		return d.replaceAll("_modelgen:R_",">");
	}
    public static String convCdataTag(String strData) {
		String repStr = strData;
		repStr = repStr.replaceAll("<!&\\[&CDATA&\\[&","<!\\[CDATA\\[");
		repStr = repStr.replaceAll("&\\]&\\]&","\\]\\]");
		return repStr;
	}
    /**
     * svgデータの先頭の改行／タブを除去
     * @param data
     * @return
     */
    public static String removeCtrlCharAtFirst(String data){
		String ret = null;
		if (data!=null && data.length()>0) {
			for (int i = 0; i < data.length(); i++){
				char c = data.charAt(i);
				if (c!='\n' && c!='\t'){
					ret = data.substring(i);
					break;
				}
			}
		}
		return ret;
    }

}
