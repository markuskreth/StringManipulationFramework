package de.kreth.telegrammmanipulation;

import java.util.*;

public class ReplacementBusiness implements ReplaceFunction {

	Collection<ReplaceFunction> functions;
	
	public ReplacementBusiness(ReplaceFunction function) {
		functions = new HashSet<>();
		functions.add(function);
	}

	@Override
	public String replace(String source) {
		String result = source;
		for (Iterator<ReplaceFunction> iterator = functions.iterator(); iterator.hasNext();) {
			result = iterator.next().replace(result);			
		}
		return result;
	}

	public boolean add(ReplaceFunction e) {
		return functions.add(e);
	}

	public boolean addAll(Collection<? extends ReplaceFunction> c) {
		return functions.addAll(c);
	}

}