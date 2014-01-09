package de.kreth.telegrammmanipulation;

public interface TestStrings {

	static String lf = "\n";

	public static String testXML = "<wurzel>" + "<element zeit=\"+10000\"/>" + "<anderesElement zeit=\"-10000\">-5000</anderesElement>" + "<element zeit=\"+10000\"/>"
			+ "<anderesElement zeit=\"-10000\">-5000</anderesElement>" + "<element zeit=\"+10000\"/>" + "<anderesElement zeit=\"-10000\">-5000</anderesElement>" + "</wurzel>";
	public static String testXMLTagNums = "<wurzel>" + "<element zeit1=\"+10\"/>" + "<anderesElement zeit2=\"-20\">-5000</anderesElement>" + "<element zeit2=\"+20\"/>"
			+ "<anderesElement zeit1=\"-10\">-5000</anderesElement>" + "<element zeit3=\"+30\"/>" + "<anderesElement zeit3=\"-30\">-5000</anderesElement>" + "</wurzel>";
	public static String testXMLTagNumsReplacable = "<wurzel>" + "<element zeit1=\"%s\"/>" + "<anderesElement zeit2=\"%s\">-5000</anderesElement>" + "<element zeit2=\"%s\"/>"
			+ "<anderesElement zeit1=\"%s\">-5000</anderesElement>" + "<element zeit3=\"%s\"/>" + "<anderesElement zeit3=\"%s\">-5000</anderesElement>" + "</wurzel>";

	public static String testXMLReplacable = "<wurzel>" + "<element zeit=\"%s\"/>" + "<anderesElement zeit=\"%s\">-5000</anderesElement>" + "<element zeit=\"%s\"/>"
			+ "<anderesElement zeit=\"%s\">-5000</anderesElement>" + "<element zeit=\"%s\"/>" + "<anderesElement zeit=\"%s\">-5000</anderesElement>" + "</wurzel>";

	public static String testXMLLineBreaks = "<wurzel>" + lf + "<element zeit=\"+10000\"/>" + lf + "<anderesElement zeit=\"-10000\">-5000</anderesElement>" + lf
			+ "<element zeit=\"+10000\"/>" + lf + "<anderesElement zeit=\"-10000\">-5000</anderesElement>" + lf + "<element zeit=\"+10000\"/>" + lf
			+ "<anderesElement zeit=\"-10000\">-5000</anderesElement>" + lf + "</wurzel>";

	public static String testXml1Element = "<wurzel><element zeit=\"+10000\"/></wurzel>";

	public static String testXml2Elements = "<wurzel><element zeit=\"+10000\"/><element zeit=\"+20000\"/></wurzel>";
	public static String testXml2ElementsReplacable = "<wurzel><element zeit=\"%s\"/><element zeit=\"%s\"/></wurzel>";

	public static String testXml2ElementsMinus = "<wurzel><element zeit=\"-10000\"/><element zeit=\"-20000\"/></wurzel>";
	public static String testXml2ElementsMinusReplacable = "<wurzel><element zeit=\"%s\"/><element zeit=\"%s\"/></wurzel>";
}
