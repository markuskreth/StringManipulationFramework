package de.kreth.telegrammmanipulation;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalendarReplacorTest {

	private static String testXML;
	
	@BeforeClass
	public static void loadXmlString(){
		InputStream resourceAsStream = CalendarReplacorTest.class.getClassLoader().getResourceAsStream("/Telegramm1.xml");
		System.out.println(resourceAsStream);
		
	}
	
	@Before
	public void setup(){
		this.testXML = TestStrings.testXML;
	}
	
	@Test
	public void loadXmlTest(){
		assertNotNull(testXML);
		assertFalse(testXML.isEmpty());
	}

	@Test
	public void testDateReplacementforPositivValue() {
		DateFormat df = DateFormat.getDateTimeInstance();
		Calendar now = new GregorianCalendar();
		ReplaceFunction dateRepl = new CalendarReplacor(now, df);
		String source = "+10000";
		String actual = dateRepl.replace(source);
		now.add(Calendar.MILLISECOND, 10000);
		String expected = df.format(now.getTime());
		assertEquals(expected,actual);
		
		expected = df.format(now.getTime());
		assertEquals(expected,actual);
	}

	@Test
	public void testDateReplacementforNegativeValue() {
		DateFormat df = DateFormat.getDateTimeInstance();
		Calendar now = new GregorianCalendar();
		ReplaceFunction dateRepl = new CalendarReplacor(now, df);
		String source = String.valueOf(-1*(10*1000));
		String actual = dateRepl.replace(source);
		now.add(Calendar.MILLISECOND, -10000);
		String expected = df.format(now.getTime());
		assertEquals(expected,actual);
		
		expected = df.format(now.getTime());
		assertEquals(expected,actual);
	}

	
}
