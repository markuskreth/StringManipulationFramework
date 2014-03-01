package de.kreth.telegrammmanipulation;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Durchsucht den XML source-String in allen XML-Tags nach dem konfigurierten Attributnamen und führt die konfigurierte {@link ReplaceFunction} darauf aus.
 * Der Inhalt (Wert) des Attributes muss den Anforderungen der {@link ReplaceFunction} genügen. 
 * @author markus
 *
 */
public class XmlTagValueReplacor implements ReplaceFunction {

	String tagName;
	ReplaceFunction function;
	private int count = -1;
	
	/**
	 * 
	 * @param tagName	Name des gesuchten Tags
	 * @param function	Funktion, die auf den Wert des gefundenen Tag ausgef�hrt werden soll.
	 */
	public XmlTagValueReplacor(String tagName, ReplaceFunction function) {
		super();
		this.tagName = tagName;
		this.function = function;
	}

	@Override
	public String replace(String source) {
		Document document = getDocument(source);
		
		count = 0;
		NodeList elementsByTagName = document.getElementsByTagName(tagName);
		for(int i=0;i<elementsByTagName.getLength();i++){
			Node item = elementsByTagName.item(i);
			replaceNodeValue(item);
		}
		return docToString(document);
	}

	/**
	 *
	 * Create at: 01.10.2013 - 09:25:21
	 *
	 * @param document
	 * @return
	 * @remarks NICHT GETESTET
	 */
	public String docToString(Document document) {

        StringWriter out = new StringWriter();
        try {
	        TransformerFactory tf = TransformerFactory.newInstance();  
	        Transformer transformer = tf.newTransformer();  
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(document), new StreamResult(out));
		} catch (TransformerException e) {
			System.err.println(e);
		} 
        return out.toString().replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "").trim();
	}
	
	private void replaceNodeValue(Node node) {

		String value = node.getTextContent();
		value = function.replace(value);
		node.setTextContent(value);
	}

	/**
	 * Liefert die Anzahl der gefundenen und ersetzten XmlAttribute
	 * @return Anzahl der Ersetzungen.
	 */
	public int getTagCount() {
		return this.count ;
	}

	public Document getDocument(String source){
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(source)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
