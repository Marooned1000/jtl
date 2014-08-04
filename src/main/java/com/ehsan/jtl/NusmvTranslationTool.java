package com.ehsan.jtl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.ehsan.jtl.model.Action;
import com.ehsan.jtl.util.Constants;
import com.ehsan.jtl.util.TextUtils;

public class NusmvTranslationTool {

	public void generateNusvmLang (StateDiagram stateDiagram, String filename) {
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(new File(filename));

			// This one to put output in file
			generateNusvmLang(stateDiagram, pw);

			// This one to put output in console
			generateNusvmLang(stateDiagram, new PrintWriter(System.out, true));

		} catch (FileNotFoundException e) {
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

		pw.printf("VAR state: {%s};\n",TextUtils.concatCollection(stateDiagram.getStateDiagram().keySet()));
		pw.printf("IVAR action : {%s};\n",TextUtils.concatCollection(stateDiagram.getActionListOfType(Constants.DEFAULT_AGENT_NAME)));

		pw.printf("INIT (state = %s)\n", stateDiagram.getInitialState());

		pw.printf("\tTRANS(next(state)= case\n");

		for (String state: stateDiagram.getStateDiagram().keySet()) {
			if (stateDiagram.getStateDiagram().get(state) != null) {
				for (Action action: stateDiagram.getStateDiagram().get(state).keySet()) {
					String actionTypePrefix = (action.getType().equals(Constants.DEFAULT_AGENT_NAME)?"":(action.getType()+"."));
					pw.printf("\t\t(state = %s & action = %s%s) : %s;\n", 
							state, 
							actionTypePrefix,
							action.getName(), 
							stateDiagram.getStateDiagram().get(state).get(action));
				}
			}
		}
		pw.printf("\tesac)\n");
	}

}
