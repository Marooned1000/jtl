package com.ehsan.jtl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.ehsan.jtl.model.Action;
import com.ehsan.jtl.model.State;
import com.ehsan.jtl.model.StateDiagram;
import com.ehsan.jtl.util.Constants;
import com.ehsan.jtl.util.TextUtil;

public class NusmvTranslationTool {

	public void generateNusvmLang (List<StateDiagram> stateDiagrams, String[] formulas, String filename, String formulaInputFileName) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(filename));
			
			// generate header
			generateHeader (stateDiagrams, pw);
			generateHeader (stateDiagrams, new PrintWriter(System.out, true));
			
			for (StateDiagram stateDiagram: stateDiagrams) {

				// This one to put output in file
				generateNusvmLang(stateDiagram, pw);

				// This one to put output in console
				generateNusvmLang(stateDiagram, new PrintWriter(System.out, true));
			}

			generateNusvmFormula(formulas, pw);
			generateNusvmFormula(formulas, new PrintWriter(System.out, true));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	private void generateHeader(List<StateDiagram> stateDiagrams, PrintWriter pw) {
		pw.println("Module main");
		pw.println("Var");
		
		if (stateDiagrams.size() == 1) {
			pw.printf("%s : process %s(%s);\n",
					stateDiagrams.get(0).getModuleShortName(),
					stateDiagrams.get(0).getModule(),
					stateDiagrams.get(0).getModuleShortName());
		} else if (stateDiagrams.size() > 1){
			pw.printf("%s : process %s(%s);\n",
					stateDiagrams.get(1).getModuleShortName(),
					stateDiagrams.get(0).getModule(),
					stateDiagrams.get(0).getModuleShortName());
			pw.printf("%s : process %s(%s);\n",
					stateDiagrams.get(0).getModuleShortName(),
					stateDiagrams.get(1).getModule(),
					stateDiagrams.get(1).getModuleShortName());
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
		pw.println();
		for (String formula: formulas) {
			String spec = formula;
			try {
				formula = formula.substring(formula.indexOf("="));
				formula = "SPEC " + formula;

				formula = translateF1 (formula);
				formula = translateF3 (formula);
				formula = translateF2 (formula);

			} catch (Exception e) {
				System.out.println("Specification line " + spec + " has syntax error");
				e.printStackTrace();
			}
			pw.println(formula);
		}
	}

	private String translateF3(String formula) {
		String result = formula;

		int currentIndex = 0;

		while (true) {
			int kIndex = formula.indexOf("Fu(C_", currentIndex);
			if (kIndex < 0) break;

			String kSubscript = formula.substring(formula.indexOf("{", kIndex)+1,
					formula.indexOf("}", kIndex));

			String iSubscript = kSubscript.substring(0, kSubscript.indexOf("->"));
			String jSubscript = kSubscript.substring(kSubscript.indexOf("->")+2, kSubscript.length());

			int parameterIndex = formula.indexOf("}", kIndex) + 1;
			String parameter = formula.substring(parameterIndex, formula.indexOf(")", parameterIndex));

			String newFormula = "(EAX("+iSubscript+".action = Gamma_"+iSubscript+")(AAX("+iSubscript+".action = Alpha_"+iSubscript+")(AAX("+iSubscript+".action = Beta_"+iSubscript+")("+parameter+")&" +
					"AAX("+jSubscript+".action = Beta_"+jSubscript+")("+parameter+"))||EAX("+iSubscript+".action = Beta_"+iSubscript+")(EAX("+iSubscript+".action = Gamma_"+iSubscript+")" +
					"(AAX("+iSubscript+".action = Alpha_"+iSubscript+")(AAX("+iSubscript+".action = Beta_"+iSubscript+")("+parameter+")&AAX("+jSubscript+".action = Beta_"+jSubscript+")("+parameter+"))))||" +
					"EAX("+jSubscript+".action = Beta_"+jSubscript+")(EAX("+iSubscript+".action = Gamma_"+iSubscript+")(AAX("+iSubscript+".action = Alpha_"+iSubscript+")" +
					"(AAX("+iSubscript+".action = Beta_"+iSubscript+")("+parameter+")&AAX("+jSubscript+".action = Beta_"+jSubscript+")("+parameter+")))))";


			formula = formula.substring(0, kIndex) + newFormula + formula.substring(parameterIndex + parameter.length()); 

			//			System.out.println("Formula: " + formula);

			currentIndex = parameterIndex + parameter.length();
		}

		result = formula;

		return result;
	}

	private String translateF2(String formula) {
		String result = formula;

		formula = formula.substring(formula.indexOf("="));
		formula = "SPEC " + formula;

		int currentIndex = 0;

		while (true) {
			int kIndex = formula.indexOf("C_", currentIndex);
			if (kIndex < 0) break;

			String kSubscript = formula.substring(formula.indexOf("{", kIndex)+1,
					formula.indexOf("}", kIndex));
			String iSubscript = kSubscript.substring(0, kSubscript.indexOf("->"));
			String jSubscript = kSubscript.substring(kSubscript.indexOf("->")+2, kSubscript.length());

			int parameterIndex = formula.indexOf("}", kIndex) + 1;
			String parameter = formula.substring(parameterIndex).split("\\W+")[0];

			String newFormula = "AAX("+iSubscript+".action = Alpha_"+iSubscript+")(AAX("+jSubscript+".action = Beta_"+jSubscript+")("+parameter+")"
					+ "&AAX("+iSubscript+".action = Beta_"+iSubscript+")("+parameter+"))";
			formula = formula.substring(0, kIndex) + newFormula + formula.substring(parameterIndex + parameter.length()); 

			//			System.out.println("Formula: " + formula);

			currentIndex = parameterIndex + parameter.length();
		}

		result = formula;

		return result;
	}

	//
	private String translateF1(String formula) {
		String result = formula;

		formula = formula.substring(formula.indexOf("="));
		formula = "SPEC " + formula;

		int currentIndex = 0;

		while (true) {
			int kIndex = formula.indexOf("K_", currentIndex);
			if (kIndex < 0) break;

			String kSubscript = formula.substring(formula.indexOf("{", kIndex)+1,
					formula.indexOf("}", kIndex));

			int parameterIndex = formula.indexOf("}", kIndex) + 1;
			String parameter = "";
			if (formula.charAt(parameterIndex) == '(') {
				parameter = formula.substring(parameterIndex+1, formula.indexOf(")", parameterIndex));
				String newFormula = "AAX("+kSubscript+".action = Beta_"+kSubscript+")(" + parameter + ")";
				formula = formula.substring(0, kIndex) + newFormula + formula.substring(parameterIndex + parameter.length()+1); 
			} else {
				parameter = formula.substring(parameterIndex).split("\\W+")[0];
				String newFormula = "AAX("+kSubscript+".action = Beta_"+kSubscript+")(" + parameter + ")";
				formula = formula.substring(0, kIndex) + newFormula + formula.substring(parameterIndex + parameter.length()); 
			}
			
			currentIndex = parameterIndex + parameter.length();
		}

		result = formula;

		return result;
	}

}
