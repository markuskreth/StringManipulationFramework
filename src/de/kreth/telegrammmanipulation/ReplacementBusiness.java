package de.kreth.telegrammmanipulation;

import java.util.Collection;
import java.util.HashSet;

import de.kreth.stringmanipulation.ReplaceFunction;

public class ReplacementBusiness implements ReplaceFunction {

	Collection<ReplaceFunction> functions;
	
	public ReplacementBusiness(ReplaceFunction function) {
		functions = new HashSet<ReplaceFunction>();
		functions.add(function);
	}

	@Override
	public String replace(String source) {
		String result = source;
		for (ReplaceFunction replacor : functions) {
			result = replacor.replace(result);			
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
