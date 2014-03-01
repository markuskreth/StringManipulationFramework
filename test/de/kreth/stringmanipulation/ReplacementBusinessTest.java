package de.kreth.stringmanipulation;

import static de.kreth.stringmanipulation.TestStrings.testXMLTagNums;
import static de.kreth.stringmanipulation.TestStrings.testXMLTagNumsReplacable;
import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReplacementBusinessTest {

   private static DateFormat df1;
   private static DateFormat df2;

   @BeforeClass
   public static void initDateformat(){
      df1 = DateFormat.getDateTimeInstance(Calendar.SHORT, Calendar.LONG);
      df2 = DateFormat.getDateTimeInstance(Calendar.LONG, Calendar.LONG);
   }

   private GregorianCalendar time;
   private CalendarReplacor calReplacorSecond;
   private CalendarReplacor calReplacorMinute;
   private CalendarReplacor calReplacorHour;
   
   @Before
   public void setUp() throws Exception {

      time = new GregorianCalendar();
      calReplacorSecond = new CalendarReplacor(time, df1, Calendar.SECOND);
      calReplacorMinute = new CalendarReplacor(time, df1, Calendar.MINUTE);
      calReplacorHour = new CalendarReplacor(time, df2, Calendar.HOUR);
   }

   @Test
   public void testAdd() {
      XmlAttributeReplacor xmlAttributeReplacor1 = getXmlAttributeReplacor("zeit1", calReplacorSecond);
      XmlAttributeReplacor xmlAttributeReplacor2 = getXmlAttributeReplacor("zeit2", calReplacorMinute);
      XmlAttributeReplacor xmlAttributeReplacor3 = getXmlAttributeReplacor("zeit3", calReplacorHour);
      
      Calendar zeitPlus10Sek = Calendar.getInstance();
      zeitPlus10Sek.setTime(time.getTime());
      zeitPlus10Sek.add(Calendar.SECOND, 10);
      String time1Plus = df1.format(zeitPlus10Sek.getTime());

      Calendar zeitMinus10Sek = Calendar.getInstance();
      zeitMinus10Sek.setTime(time.getTime());
      zeitMinus10Sek.add(Calendar.SECOND, -10);
      String time1Minus = df1.format(zeitMinus10Sek.getTime());

      Calendar zeitPlus20Min = Calendar.getInstance();
      zeitPlus20Min.setTime(time.getTime());
      zeitPlus20Min.add(Calendar.MINUTE, 20);
      String time2Plus = df1.format(zeitPlus20Min.getTime());

      Calendar zeitMinus20Min = Calendar.getInstance();
      zeitMinus20Min.setTime(time.getTime());
      zeitMinus20Min.add(Calendar.MINUTE, -20);
      String time2Minus = df1.format(zeitMinus20Min.getTime());

      Calendar zeitPlus30Hour = Calendar.getInstance();
      zeitPlus30Hour.setTime(time.getTime());
      zeitPlus30Hour.add(Calendar.HOUR, 30);
      String time3Plus = df2.format(zeitPlus30Hour.getTime());

      Calendar zeitMinus30Hour = Calendar.getInstance();
      zeitMinus30Hour.setTime(time.getTime());
      zeitMinus30Hour.add(Calendar.HOUR, -30);
      String time3Minus = df2.format(zeitMinus30Hour.getTime());
      
      String expected = String.format(testXMLTagNumsReplacable, time1Plus, time2Minus, time2Plus, time1Minus, time3Plus, time3Minus);

      ReplacementBusiness replacer = new ReplacementBusiness(xmlAttributeReplacor1);
      replacer.add(xmlAttributeReplacor2);
      replacer.add(xmlAttributeReplacor3);

      String actual = replacer.replace(testXMLTagNums);
      assertEquals(expected, actual);
   }

   private XmlAttributeReplacor getXmlAttributeReplacor(String attrName, ReplaceFunction calReplacor){
      return new XmlAttributeReplacor(attrName, calReplacor);

   }
}
