package de.kreth.stringmanipulation;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Ersetzt einen String, der das im Konstruktor übergebene DatumFormat hat durch einen neuen Zeitwert im selben Format. Dabei wird beim ersten Aufruf die Quelle als Basiszeit festgelegt und die im Konstruktor übergebene neue Zeit gesetzt.
 * Alle weiteren Ersetzungen werden relativ zu der ersten Ersetzung vorgenommen, d.h. ein zweiter Zeitpunkt basiszeit + 2 Minuten wird ersetzt durch neue Zeit + 2 Minuten.
 * 
 * <p /> die {@link #replace(String)} Methode wirft exception, wenn {@link DateFormat} eine Exception wirft.
 * @author mkreth
 *
 */
public class DateTimeReplacor implements ReplaceFunction {

	Date baseTime = null;
	Date replacementBase;
	DateFormat df; 
	

	/**
	 * 
	 * Ersetzt einen String, der das im Konstruktor übergebene DatumFormat hat durch einen neuen Zeitwert im selben Format. 
	 * <br />Dabei wird beim ersten Aufruf die Quelle als Basiszeit festgelegt und die im Konstruktor übergebene neue Basiszeit gesetzt.
	 * <br />Alle weiteren Ersetzungen werden relativ zu der ersten Ersetzung vorgenommen, d.h. ein zweiter Zeitpunkt basiszeit + 2 Minuten wird ersetzt durch neue Zeit + 2 Minuten.
	 * 
	 * <p /> die {@link #replace(String)} Methode wirft exception, wenn {@link DateFormat} eine Exception wirft.
	 * @param df
	 * @param replacementBase	Neue Basiszeit 
	 */
	public DateTimeReplacor(DateFormat df, Date replacementBase) {
		super();
		this.df = df;
		this.replacementBase = replacementBase;
				
	}

	@Override
	public String replace(String source) {
		try {
			Date baseT = df.parse(source);
			
			if(baseTime == null)
				baseTime = baseT;
			
			long difference = baseTime.getTime()-baseT.getTime();
			Date replacement = new Date(replacementBase.getTime() - difference);
			return df.format(replacement);
					
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
