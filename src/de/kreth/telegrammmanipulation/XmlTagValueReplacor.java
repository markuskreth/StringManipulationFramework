package de.kreth.telegrammmanipulation;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Durchsucht den XML source-String in allen XML-Tags nach dem konfigurierten Attributnamen und fÃ¼hrt die konfigurierte {@link ReplaceFunction} darauf aus.
 * Der Inhalt (Wert) des Attributes muss den Anforderungen der {@link ReplaceFunction} genÃ¼gen. 
 * @author markus
 *
 */
public class XmlTagValueReplacor implements ReplaceFunction {

	String tagName;
	ReplaceFunction function;
	private int count = -1;
	private OutputFormat format = null;
	
	/**
	 * 
	 * @param tagName	Name des gesuchten Tags
	 * @param function	Funktion, die auf den Wert des gefundenen Tag ausgeführt werden soll.
	 */
	public XmlTagValueReplacor(String tagName, ReplaceFunction function) {
		super();
		this.tagName = tagName;
		this.function = function;
	}

	/**
	 * @param format OutputFormat für die XML-ausgabe setzten.
	 */
	public void setFormat(OutputFormat format) {
		this.format = format;
	}
	
	@Override
	public String replace(String source) {
		Document document = getDocument(source);
		
		count = 0;
		replaceValueOrSearchChildren(document.getDocumentElement());
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
        OutputFormat forma;
        if(this.format != null)
        	forma = this.format;
        else {
	        forma = new OutputFormat(document);
	        forma.setIndenting(true);
	        forma.setIndent(2);
	        forma.setOmitXMLDeclaration(true);
        }
        return forma;
	}
	
	private void replaceValueOrSearchChildren(Node node) {
		if(node.hasChildNodes()){
			NodeList childNodes = node.getChildNodes();
			for(int i=0;i<childNodes.getLength();i++){
				Node item = childNodes.item(i);
				replaceValueOrSearchChildren(item);
			}
		} else
			replaceNodeValue(node);
	}

	private void replaceNodeValue(Node node) {
		if(node.getNodeName().matches(tagName)){
			String value = node.getTextContent();
			value = function.replace(value);
			node.setTextContent(value);
		}		
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
