package de.kreth.telegrammmanipulation;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;


import de.stringmanipulation.ReplaceFunction;
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
	
	/**
	 * 
	 * @param attibuteName	Name des gesuchten Attributs
	 * @param function	Funktion, die auf den Wert des gefundenen Attributs ausgef�hrt werden soll.
	 */
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
		
		return docToString(document);
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * Create at: 01.10.2013 - 09:41:11
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
        return out.toString().replaceAll("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "").trim();
	}
}
