package de.kreth.stringmanipulation;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateTimeReplacorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOneValueSameTime() {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.GERMANY);
		
		Date replaceTime = new GregorianCalendar(2014, Calendar.AUGUST, 21, 8, 30, 0).getTime();
		Date sourceTime = new GregorianCalendar(2014, Calendar.MARCH, 21, 8, 30, 0).getTime();
		
		DateTimeReplacor replacor = new DateTimeReplacor(df, replaceTime);

		String expected = df.format(replaceTime);
		String actual = replacor.replace(df.format(sourceTime));
		assertEquals(expected, actual);
	}

	@Test
	public void testOneValueReplacementInFuture() {

		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.GERMANY);
		
		Date replaceTime = new GregorianCalendar(2014, Calendar.AUGUST, 21, 9, 30, 0).getTime();
		Date sourceTime = new GregorianCalendar(2014, Calendar.MARCH, 21, 8, 40, 0).getTime();
		
		DateTimeReplacor replacor = new DateTimeReplacor(df, replaceTime);

		String expected = df.format(replaceTime);
		
		String actual = replacor.replace(df.format(sourceTime));
		assertEquals(expected, actual);
	}

	@Test
	public void testTwoValuesPositiveDifference() {

		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.GERMANY);
		
		Date baseTime = new GregorianCalendar(2014, Calendar.AUGUST, 21, 9, 30, 0).getTime();
		DateTimeReplacor replacor = new DateTimeReplacor(df, baseTime);
		
		Date expectedTime = new GregorianCalendar(2014, Calendar.AUGUST, 21, 9, 35, 0).getTime();
		String expected = df.format(expectedTime);
		
		Date sourceTime = new GregorianCalendar(2014, Calendar.MARCH, 21, 8, 40, 0).getTime();
		replacor.replace(df.format(sourceTime));
		
		sourceTime = new GregorianCalendar(2014, Calendar.MARCH, 21, 8, 45, 0).getTime();

		String actual = replacor.replace(df.format(sourceTime));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testThreeValuesNegativeDifference() {

		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.GERMANY);
		
		Date baseTime = new GregorianCalendar(2014, Calendar.AUGUST, 21, 9, 30, 0).getTime();
		DateTimeReplacor replacor = new DateTimeReplacor(df, baseTime);

		Date expectedTime1 = new GregorianCalendar(2014, Calendar.AUGUST, 21, 9, 20, 0).getTime();
		String expected1 = df.format(expectedTime1);

		Date expectedTime2 = new GregorianCalendar(2014, Calendar.AUGUST, 21, 9, 15, 0).getTime();
		String expected2 = df.format(expectedTime2);
		
		Date sourceTime = new GregorianCalendar(2014, Calendar.MARCH, 21, 8, 40, 0).getTime();
		replacor.replace(df.format(sourceTime));

		sourceTime = new GregorianCalendar(2014, Calendar.MARCH, 21, 8, 30, 0).getTime();

		String actual = replacor.replace(df.format(sourceTime));
		assertEquals(expected1, actual);
		sourceTime = new GregorianCalendar(2014, Calendar.MARCH, 21, 8, 25, 0).getTime();

		actual = replacor.replace(df.format(sourceTime));
		assertEquals(expected2, actual);
	}
}
