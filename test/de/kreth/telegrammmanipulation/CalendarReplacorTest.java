package de.kreth.telegrammmanipulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import de.stringmanipulation.CalendarReplacor;
import de.stringmanipulation.ReplaceFunction;
import de.stringmanipulation.TestStrings;

public class CalendarReplacorTest {

	private static String testXML;
		
	@Before
	public void setup(){
		CalendarReplacorTest.testXML = TestStrings.testXML;
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
