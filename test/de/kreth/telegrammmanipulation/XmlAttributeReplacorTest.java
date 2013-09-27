package de.kreth.telegrammmanipulation;

import static org.junit.Assert.*;
import static de.kreth.telegrammmanipulation.TestStrings.*;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;

public class XmlAttributeReplacorTest {

	private static DateFormat df;

	@BeforeClass
	public static void initDateformat(){
		df = DateFormat.getDateTimeInstance(Calendar.SHORT, Calendar.LONG);
	}
	
	@Before
	public void setup(){
		
	}
	@Test
	public void findAllAttibutes() {
		Calendar time = new GregorianCalendar();
		CalendarReplacor calReplacor = new CalendarReplacor(time, df);
		XmlAttributeReplacor repl = new XmlAttributeReplacor("zeit", calReplacor);
		repl.replace(testXml1Element);
		int actual = repl.getAttributeCount();
		int expected = 1;
		assertEquals(expected, actual);

		repl.replace(testXml2Elements);
		actual = repl.getAttributeCount();
		expected = 2;
		assertEquals(expected, actual);

		repl.replace(testXML);
		actual = repl.getAttributeCount();
		expected = 6;
		assertEquals(expected, actual);
		
		repl.replace(testXMLLineBreaks);
		actual = repl.getAttributeCount();
		expected = 6;
		assertEquals(expected, actual);
	}

	@Test
	public void testStringResult(){

		Calendar time = new GregorianCalendar();
		CalendarReplacor calReplacor = new CalendarReplacor(time, df);
		XmlAttributeReplacor repl = new XmlAttributeReplacor("zeit", calReplacor){

			public OutputFormat getOutputFormat(Document document){
		        OutputFormat format = new OutputFormat(document);
		        format.setIndenting(false);
		        format.setOmitXMLDeclaration(true);
		        return format;
			}
		};

		Calendar time10000 = Calendar.getInstance();
		time10000.setTime(time.getTime());
		time10000.add(Calendar.MILLISECOND, -10000);

		Calendar time20000 = Calendar.getInstance();
		time20000.setTime(time.getTime());
		time20000.add(Calendar.MILLISECOND, -20000);
		
		String expected = String.format(testXml2ElementsMinusReplacable, df.format(time10000.getTime()), df.format(time20000.getTime()));
		String actual = repl.replace(testXml2ElementsMinus);
		assertEquals(expected, actual);
		
		time10000 = Calendar.getInstance();
		time10000.setTime(time.getTime());
		time10000.add(Calendar.MILLISECOND, 10000);

		time20000 = Calendar.getInstance();
		time20000.setTime(time.getTime());
		time20000.add(Calendar.MILLISECOND, 20000);
		
		expected = String.format(testXml2ElementsReplacable, df.format(time10000.getTime()), df.format(time20000.getTime()));
		actual = repl.replace(testXml2Elements);
		assertEquals(expected, actual);
	}
}
