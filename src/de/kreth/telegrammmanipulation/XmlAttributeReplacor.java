package de.kreth.telegrammmanipulation;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

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
		

        OutputFormat format = getOutputFormat(document);
        
        Writer out = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(out, format);
        try {
			serializer.serialize(document);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

        return out.toString();
	}

	public OutputFormat getOutputFormat(Document document){
        OutputFormat format = new OutputFormat(document);
        format.setIndenting(true);
        format.setIndent(2);
        format.setOmitXMLDeclaration(true);
        return format;
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
}
