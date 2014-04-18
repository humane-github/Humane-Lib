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
 * XML�����𑀍삷��N���X
 *
 * @author T.Ichikawa
 *
 */
public class XMLManager{

	/** ����Ώۂ�XML���� **/
	private Document document = null;

	/** ���[�g�v�f **/
	private Element root = null;

	/** �������ʂ̗v�f **/
	private Element findResultElement = null;

	/** �������ʂ̐e�v�f **/
	private Element findResultParentElement = null;

	/**
	 * �J�����g�v�f�̏��
	 * 	true=�J�����g�v�f�Ȃ��Afalse=�J�����g�v�f����
	 * 	find�n���\�b�h�Ō������ʂ������ꍇ��true
	 * 	moveFirst�n���\�b�h���s����false���ݒ肳���
	 * 	moveNext�n���\�b�h�Ŏ��̗v�f������������true
	 */
	private boolean EOF = false;

	/**
	 * �J�����g�v�f�̏�Ԃ�ݒ肷��
	 * 	�l�͌����^�ړ��n���\�b�h�Őݒ肷��
	 *
	 * @param b �J�����g�v�f�̏��
	 */
	private void setEOF(boolean b){
		EOF = b;
	}

	/**
	 * �J�����g�v�f�̗L����Ԃ�
	 * @return true=�J�����g�v�f�Ȃ��Afalse=�J�����g�v�f����
	 */
	public boolean EOF(){return EOF;}

	/**
	 * �R���X�g���N�^
	 */
	public XMLManager(){

	}

	/**
	 * XML���V���A���C�Y����
	 * @return	�V���A���C�Y�����w�l�k������
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
	 * ���ݎ擾���Ă���XML������j������
	 *
	 */
	private void closeDocument(){
		document = null;
		root = null;
	}

	/**
	 * XML�t�@�C����ǂݍ���
	 *
	 * @param filePath �ǂݍ���XML�t�@�C��
	 * @return true:�����Afalse:���s
	 */
	public boolean openXMLFile(String filePath)
	{
		try{
			//�h�L�������g�r���_�[�t�@�N�g���𐶐�
		    DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();

		    //�h�L�������g�r���_�[�𐶐�
		    DocumentBuilder builder = dbfactory.newDocumentBuilder();

		    //�p�[�X�����s����Document�I�u�W�F�N�g���擾
		    document = builder.parse(new File(filePath));

		    //���[�g�v�f���擾
		    root = document.getDocumentElement();

		}catch(Exception ex){
			//AppLog.writeError(ex.toString());
			closeDocument();
			return false;
		}

		return true;
	}

	/**
	 * XML�������ݒ肷��
	 *
	 * @param xmlText XML������
	 * @return true:�����Afalse:���s
	 */
	public boolean setXMLText(String xmlText)
	{
		try{
			InputSource source = new InputSource(new StringReader(xmlText));
			source.setEncoding("UTF-8");

			//�h�L�������g�r���_�[�t�@�N�g���𐶐�
		    DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();

		    //�h�L�������g�r���_�[�𐶐�
		    DocumentBuilder builder = dbfactory.newDocumentBuilder();

		    //�p�[�X�����s����Document�I�u�W�F�N�g���擾
		    document = builder.parse(source);

		    //���[�g�v�f���擾
		    root = document.getDocumentElement();

		}catch(Exception ex){
			//AppLog.writeError(ex);
			closeDocument();
			return false;
		}

		return true;
	}


	/**
	 *�^�O�����L�[�ɂ��ă��[�g�v�f�z�����ċA�I�Ɍ�������
	 *
	 * @param tagName ��������^�O��
	 * @return true:�Y���v�f���o�Afalse:�Y���v�f�Ȃ�
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
	 *�^�O�����L�[�ɂ��Ďw��v�f�Q���ċA�I�Ɍ�������
	 *
	 * @param elements ��������v�f�Q
	 * @param tagName ��������^�O��
	 * @return ���������v�f�i������Ȃ������ꍇ��null�j
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
	 * �������ʂ̃J�����g�v�f�̃^�O����Ԃ�
	 *
	 * @return �^�O���i�������ʂ����݂��Ȃ��ꍇ��null�j
	 */
	public String getTagName(){
		if (findResultElement!=null){
			return findResultElement.getTagName();
		}else{
			return null;
		}
	}

	/**
	 * �������ʂ̃J�����g�v�f�̎w�肵�������̒l��Ԃ�
	 *
	 * @param	attrName ������
	 * @return �����̒l�i�������ʂ����݂��Ȃ��ꍇ��null�j
	 */
	public String getAttribute(String attrName){
		if (findResultElement!=null){
			return findResultElement.getAttribute(attrName);
		}else{
			return null;
		}
	}

	/**
	 * �������ʂ̃J�����g�v�f�̑�����ݒ肷��
	 *
	 * @param AttributeName	������
	 * @param attributeValue	�����l
	 */
	public void setAttribute(String AttributeName, String attributeValue)
	{
		if (findResultElement!=null){
			findResultElement.setAttribute(AttributeName, attributeValue);
		}
	}

	/**
	 * �������ʂ̃J�����g�v�f�̃^�O�l��Ԃ�
	 *
	 * @return �^�O�l�i�������ʂ����݂��Ȃ��ꍇ��null�j
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
	 * �J�����g�v�f�����̌Z��v�f�Ɉړ�����
	 * @return true:���̌Z��v�f�����݂���Afalse:���̌Z��v�f�����݂��Ȃ�
	 */
	public boolean moveNextSibling(){
		return moveSibling(true);
	}

	/**
	 * �J�����g�v�f��O�̌Z��v�f�Ɉړ�����
	 * @return true:�O�̌Z��v�f�����݂���Afalse:�O�̌Z��v�f�����݂��Ȃ�
	 */
	public boolean movePreviousSibling(){
		return moveSibling(false);
	}

	/**
	 * �J�����g�v�f��O��̌Z��v�f�Ɉړ�����
	 * @param moveNext true:���̌Z��v�f�Ɉړ��Afalse:�O�̌Z��v�f�Ɉړ�
	 * @return true:�ړ������Afalse:�ړ���v�f�Ȃ�
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
	 * �J�����g�v�f��擪�̎q�v�f�Ɉړ�����
	 * @return true:�q�v�f�����݂���Afalse:�q�v�f�����݂��Ȃ�
	 */
	public boolean moveFirstChild(){
		return moveChild(true);
	}

	/**
	 * �J�����g�v�f�𖖔��̎q�v�f�Ɉړ�����
	 * @return true:�q�v�f�����݂���Afalse:�q�v�f�����݂��Ȃ�
	 */
	public boolean moveLastChild(){
		return moveChild(false);
	}

	/**
	 * �J�����g�v�f��擪�܂��͖����̎q�v�f�Ɉړ�����
	 *
	 * @param moveFirstChild	true:�擪�̎q�v�f�Ɉړ��Afalse:�����̎q�v�f�Ɉړ�
	 * @return true:�q�v�f�����݂���Afalse:�q�v�f�����݂��Ȃ�
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
	 * �J�����g�v�f��e�v�f�Ɉړ�����
	 * @return true:�e�v�f�����݂���Afalse:�e�v�f�����݂��Ȃ�
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
	 * �J�����g�v�f�̑O�̌Z��v�f�Ƃ��ĐV�K�v�f��ǉ�����
	 *
	 * @param tagName �V�K�v�f�̃^�O��
	 * @return true:�����Afalse:���s
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
	 * �J�����g�v�f�̎q�v�f�Ƃ��ĐV�K�v�f��ǉ�����
	 *
	 * @param tagName 		�V�K�v�f�̃^�O��
	 * @param moveCurrent	true:�V�K�v�f���J�����g�v�f�ɕύX����Afalse:�J�����g�v�f�͕ύX���Ȃ�
	 * @return true:�����Afalse:���s
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
	 * �w�肵�������̃J�����g�v�f�����̕����̃J�����g�v�f�z���ɃR�s�[����
	 *
	 * @param source_xml �R�s�[����J�����g�v�f��������
	 * @return true:�����Afalse:���s
	 */
	public boolean copyChild(XMLManager source_xml)
	{
		//if ( source_xml==null && source_xml.EOF() ) return false;
		if ( source_xml==null ) return false;
		if ( source_xml.EOF() ) return false;

		if ( EOF() ) return false;

		//�R�s�[���m�[�h�̕������擾
		Element srcChild = source_xml.getCurrentClone();

		//�v�f���ċA�R�s�[����
		ArrayList<Node> childNodes = new ArrayList<Node>();
		childNodes.add(srcChild);
		_copyChild(findResultElement, childNodes);

		return true;
	}

	/**
	 * �J�����g�v�f�̕�����Ԃ�
	 *
	 * @return �J�����g�v�f�̕���
	 */
	private Element getCurrentClone(){
		return (Element)findResultElement.cloneNode(true);
	}

	/**
	 * �v�f���ċA�R�s�[����
	 *
	 * @param target		�R�s�[��v�f�i���̗v�f�̒����ɃR�s�[����j
	 * @param sourceList	�R�s�[���v�f�̃��X�g
	 */
	private void _copyChild(Element target, ArrayList<Node> sourceList)
	{
		for(Node srcNode : sourceList){
			if (srcNode.getNodeType()!=Node.ELEMENT_NODE) continue;

			//�V�K�v�f�쐬
			Element srcElem = (Element)srcNode;
			String tagName = srcElem.getTagName();
			Element newChild = document.createElement(tagName);
			//�v�f�̑�����ݒ�
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
			//�쐬�����v�f���J�����g�v�f�z���ɒǉ�����
			target.appendChild(newChild);

			//�q�v�f���ċA�R�s�[����
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
     * svg�f�[�^�̐擪�̉��s�^�^�u������
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
