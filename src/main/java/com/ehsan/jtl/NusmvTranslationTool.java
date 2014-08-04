package com.ehsan.jtl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
		pw.printf("IVAR action : {%s};\n",TextUtils.concatCollection(stateDiagram.getActionList()));
	}

}
