package de.stringmanipulation;

import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ CalendarReplacorTest.class, XmlAttributeReplacorTest.class, ReplacementBusinessTest.class })
public class AllTests extends TestSuite {
   // Ist schon funktional - Klassen aus Annotation
}
