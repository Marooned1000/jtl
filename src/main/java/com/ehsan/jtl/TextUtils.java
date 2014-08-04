package com.ehsan.jtl;

import java.util.Collection;

public class TextUtils {
	public static <T> String concatCollection (Collection<T> col) {
		StringBuilder sb = new StringBuilder();
		String delim = "";
	    for (T t : col) {
	        sb.append(delim).append(t);
	        delim = ",";
	    }
	    return sb.toString();
	}
}
