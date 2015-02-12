package com.ehsan.jtl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ehsan.jtl.model.Action;
import com.ehsan.jtl.model.AtomicProposition;
import com.ehsan.jtl.model.State;
import com.ehsan.jtl.model.StateDiagram;
import com.ehsan.jtl.util.Constants;
import com.ehsan.jtl.util.TextUtil;

public class NusmvTranslationTool {

	public void generateNusvmLang2 (String[] formulas, String filename) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(filename));
			generateNusvmFormula(formulas, pw);
			generateNusvmFormula(formulas, new PrintWriter(System.out, true));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	public void generateNusvmFormula(String[] formulas, PrintWriter pw) {
		pw.println();
		for (String formula: formulas) {
			String spec = formula;

			formula = formula.replaceAll("\\s+","");

			try {
				//formula = formula.substring(formula.indexOf("=")+1);
				//formula = "SPEC " + formula;

//				formula = translateF1 (formula);
//				formula = translateF3 (formula);
//				formula = translateF2 (formula);
				formula = translateF4 (formula);
				
				formula = finalFix (formula);

			} catch (Exception e) {
				System.out.println("Specification line " + spec + " has syntax error");
				e.printStackTrace();
			}
			pw.println(formula);
			
			if (!formulas[formulas.length-1].equals(formula)) {
				pw.println(" &");
			}
		}
	}
	
	private String translateF4(String formula) {
		String result = formula;

		//formula = formula.substring(formula.indexOf("="));
		//formula = "SPEC " + formula;

		int currentIndex = 0;

		while (true) {
			int kIndex = formula.indexOf("CC(", currentIndex);
			if (kIndex < 0) break;

			String allParams = formula.substring(formula.indexOf("(", kIndex)+1,
					formula.indexOf(")", kIndex));
			
			String param1 = allParams.split(",")[0].trim();
			//String param2 = allParams.split(",")[1].trim();
			String param3 = allParams.split(",")[2].trim();
			String param4 = allParams.split(",")[3].trim();
			
			int parameterIndex = formula.indexOf(")", kIndex) + 1;
			String parameter = formula.substring(parameterIndex).split("\\W+")[0];

			String newFormula = " EAX("+param1+".action=Alpha_s_b)("+param3+") & "
					+ "AAX("+param1+".action=Alpha_s_b)("+param3+" -> EF "+param4+")))";					
			formula = formula.substring(0, kIndex) + newFormula + formula.substring(parameterIndex + parameter.length()); 

			//			System.out.println("Formula: " + formula);

			currentIndex = parameterIndex + parameter.length();
		}

		result = formula;

		return result;
	}

	private String finalFix(String formula) {
		String res = formula;
		
		// Adding semicolon
		if (!res.trim().endsWith(";")) res = res + ";";
		
		//Capital DEF
		res = res.replaceAll("Def_", "DEF_");
		res = res.replaceAll("def_", "DEF_");
		
		return res;
	}
	

}

