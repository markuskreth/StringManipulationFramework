package de.stringmanipulation;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Durchsucht den XML source-String in allen XML-Tags nach dem konfigurierten Attributnamen und führt die konfigurierte {@link ReplaceFunction} darauf aus.
 * Der Inhalt (Wert) des Attributes muss den Anforderungen der {@link ReplaceFunction} genügen. 
 * @author markus
 *
 */
public class XmlAttributeReplacor implements ReplaceFunction {

	String attibuteName;
	ReplaceFunction function;
	private int count = -1;
	
	public XmlAttributeReplacor(String attibuteName, ReplaceFunction function) {
		super();
		this.attibuteName = attibuteName;
		this.function = function;
	}

	@Override
	public String replace(String source) {
		Document document = getDocument(source);
		
		count = 0;
		replaceAttributeInElementsAndChildren(document.getDocumentElement());

	    try {
	       DOMSource domSource = new DOMSource(document);
	       StringWriter writer = new StringWriter();
	       StreamResult result = new StreamResult(writer);
	       TransformerFactory tf = TransformerFactory.newInstance();
	       Transformer transformer = tf.newTransformer();
	       transformer.transform(domSource, result);
	       return writer.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
	    } catch(TransformerException ex) {
	       ex.printStackTrace();
	       return null;
	    }
	}
	
	private void replaceAttributeInElementsAndChildren(Node node) {
		findAndReplaceAttributeInNode(node);
		NodeList childNodes = node.getChildNodes();
		for(int i=0;i<childNodes.getLength();i++){
			Node item = childNodes.item(i);
			replaceAttributeInElementsAndChildren(item);
		}
	}

	private void findAndReplaceAttributeInNode(Node node) {
		if(node.hasAttributes()){
			NamedNodeMap attributes = node.getAttributes();
			Node namedItem = attributes.getNamedItem(attibuteName);
			if(namedItem != null) {
				String textContent = namedItem.getTextContent();
				textContent = function.replace(textContent);
				namedItem.setTextContent(textContent);
				count++;
			}
		}
		
	}

	/**
	 * Liefert die Anzahl der gefundenen und ersetzten XmlAttribute
	 * @return Anzahl der Ersetzungen.
	 */
	public int getAttributeCount() {
		return this.count ;
	}

	public Document getDocument(String source){
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(source)));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}
