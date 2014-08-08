package com.ehsan.jtl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.ehsan.jtl.model.Action;
import com.ehsan.jtl.model.State;
import com.ehsan.jtl.model.StateDiagram;
import com.ehsan.jtl.util.Constants;
import com.ehsan.jtl.util.FileUtil;
import com.ehsan.jtl.util.TextUtil;

public class NusmvTranslationTool {

	public void generateNusvmLang (List<StateDiagram> stateDiagrams, String filename, String formulaInputFileName) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(filename));
			/*
			for (StateDiagram stateDiagram: stateDiagrams) {
			
				// This one to put output in file
				generateNusvmLang(stateDiagram, pw);

				// This one to put output in console
				generateNusvmLang(stateDiagram, new PrintWriter(System.out, true));
			}
			*/

			String[] formulas = FileUtil.readLines(formulaInputFileName);
			generateNusvmFormula(formulas, pw);
			generateNusvmFormula(formulas, new PrintWriter(System.out, true));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	public void generateNusvmLang(StateDiagram stateDiagram, PrintWriter pw) {
		pw.printf("-----------------------------------------\n");
		pw.printf("-- The definition of %s Agent (%s)\n", 
				stateDiagram.getModule(), stateDiagram.getArgument());
		pw.printf("-----------------------------------------\n");
		pw.printf("MODULE %s (arg1)\n", stateDiagram.getModule());

		pw.printf("VAR state: {%s};\n",TextUtil.concatCollection(stateDiagram.getStateNames()));
		pw.printf("IVAR action : {%s};\n",TextUtil.concatCollection(stateDiagram.getActionListOfType(Constants.DEFAULT_AGENT_NAME)));

		pw.printf("INIT (state = %s)\n", stateDiagram.getInitialState().getName());

		pw.printf("\tTRANS(next(state)= case\n");

		for (State state: stateDiagram.getStateDiagram().keySet()) {
			if (stateDiagram.getStateDiagram().get(state) != null) {
				for (Action action: stateDiagram.getStateDiagram().get(state).keySet()) {
					String actionTypePrefix = (action.getType().equals(Constants.DEFAULT_AGENT_NAME)?"":(action.getType()+"."));
					pw.printf("\t\t(state = %s & %saction = %s) : %s;\n", 
							state.getName(), 
							actionTypePrefix,
							action.getName(), 
							stateDiagram.getStateDiagram().get(state).get(action).getName());
				}
			}
		}
		pw.printf("\tesac)\n");
	}
	
	public void generateNusvmFormula(String[] formulas, PrintWriter pw) {
	
		for (String formula: formulas) {
			formula = translateF1 (formula);
			formula = translateF3 (formula);
			formula = translateF2 (formula);
			
			pw.println(formula);
		}
		
	}

	private String translateF3(String formula) {
		// TODO Auto-generated method stub
		return null;
	}

	private String translateF2(String formula) {
		// TODO Auto-generated method stub
		return null;
	}

	//
	private String translateF1(String formula) {
		String result = formula;
		String iter = formula;
		
		formula = formula.substring(formula.indexOf("="));
		formula = "SPEC " + formula;
		
		int currentIndex = 0;
		
		while (true) {
			int kIndex = formula.indexOf("K_", currentIndex);
			if (kIndex < 0) break;
			
			String kSubscript = formula.substring(formula.indexOf("{", kIndex)+1,
					formula.indexOf("}", kIndex));
			//System.out.println("kSub: " + kSubscript);
			
			int parameterIndex = formula.indexOf("}", kIndex) + 1;
			String parameter = formula.substring(parameterIndex).split("\\W+")[0];
			//System.out.println("Parameter: " + parameter);
			
			String newFormula = "AAX("+kSubscript+".action = Beta_"+kSubscript+")(" + parameter + ")";
			formula = formula.substring(0, kIndex) + newFormula + formula.substring(parameterIndex + parameter.length(), formula.length()-1); 
			
			System.out.println("Formula: " + formula);
			
			currentIndex = parameterIndex + parameter.length();
		}
		
		result = formula;
		
		return result;
	}
	
}
