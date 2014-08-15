package de.kreth.stringmanipulation;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Ersetzt einen positiven oder negativen long-Wert durch einen Calendar-String.
 * Der source-String (in {@link #replace(String)}) im format [+|-]######
 * entspricht dabei den millisekunden, die dem konfigurierten Calendar-Wert
 * hinzugefügt werden. Beispiel: +10000 fügt dem Calendar 10 Sekunden
 * (10000ms) hinzu. Beispiel: -20000 zieht 20 Sekunden ab. Die Ausgabe wird
 * durch das übergebene DateFormat formatiert.
 * 
 * @author markus
 */
public class CalendarReplacor implements ReplaceFunction {

	private Calendar time;
	private DateFormat dateformat;
	private int field;

	/**
	 * 
	 * @param time
	 *            Zeitpunkt zu dem die Einheiten addiert werden.
	 * @param df
	 *            Ausgabeformat des Calendar
	 * @param field
	 *            Einheit, die addiert werden soll.
	 */
	public CalendarReplacor(Calendar time, DateFormat df, int field) {
		this.time = time;
		this.dateformat = df;
		this.field = field;
	}

	/**
	 * Default für Field ist hier Calendar.MILLISECOND
	 * 
	 * @param time
	 *            Zeitpunkt zu dem die Einheiten addiert werden.
	 * @param df
	 *            Ausgabeformat des Calendar
	 */
	public CalendarReplacor(Calendar time, DateFormat df) {
		this(time, df, Calendar.MILLISECOND);
	}

	@Override
	public String replace(String source) {
		Calendar tmp = Calendar.getInstance();
		tmp.setTime(time.getTime());
		String s;

		if (source.startsWith("+"))
			s = source.substring(1);
		else
			s = source;

		int amount = Integer.parseInt(s);
		tmp.add(field, amount);
		return dateformat.format(tmp.getTime());
	}

}
