package de.kreth.stringmanipulation;

import static de.kreth.stringmanipulation.TestStrings.*;
import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;

import de.kreth.stringmanipulation.CalendarReplacor;
import de.kreth.stringmanipulation.XmlAttributeReplacor;

public class XmlAttributeReplacorTest {

	private static DateFormat df;

	@BeforeClass
	public static void initDateformat(){
		df = DateFormat.getDateTimeInstance(Calendar.SHORT, Calendar.LONG);
		
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
		XmlAttributeReplacor repl = new XmlAttributeReplacor("zeit", calReplacor);

		Calendar time10000 = Calendar.getInstance();
		time10000.setTime(time.getTime());
		time10000.add(Calendar.MILLISECOND, -10000);

		Calendar time20000 = Calendar.getInstance();
		time20000.setTime(time.getTime());
		time20000.add(Calendar.MILLISECOND, -20000);
		
		String expected = String.format(testXml2ElementsMinusReplacable, df.format(time10000.getTime()), df.format(time20000.getTime()));
		String actual = repl.replace(testXml2ElementsMinus).replace("\r\n", "").replace("\n", "");
		assertEquals(expected, actual);
		
		time10000 = Calendar.getInstance();
		time10000.setTime(time.getTime());
		time10000.add(Calendar.MILLISECOND, 10000);

		time20000 = Calendar.getInstance();
		time20000.setTime(time.getTime());
		time20000.add(Calendar.MILLISECOND, 20000);
		
		expected = String.format(testXml2ElementsReplacable, df.format(time10000.getTime()), df.format(time20000.getTime()));
		actual = repl.replace(testXml2Elements).replace("\r\n", "").replace("\n", "");
		assertEquals(expected, actual);
	}
}
