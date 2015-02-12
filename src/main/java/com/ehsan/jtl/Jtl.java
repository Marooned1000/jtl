package com.ehsan.jtl;

import java.util.Date;
import java.util.List;

import com.ehsan.jtl.model.StateDiagram;
import com.ehsan.jtl.util.Constants;

public class Jtl {
	
	private static final String OUTPUT_FILENAME = "output2.txt";
	
	public void run () {
		System.out.println("***Java Transformation Tool Started***\n");
		
		InputStateDiagram inputStateDiagram = new InputStateDiagram();
		NusmvTranslationTool translator = new NusmvTranslationTool();
		
		// Building the module from console input
		String[] formulas = inputStateDiagram.generateFormulas();

		long starttime = new Date().getTime();
		// Doing the translation on state diagram
		translator.generateNusvmLang2(formulas, OUTPUT_FILENAME);
		long endtime = new Date().getTime();
		
		System.out.println("Time(ms): " + (endtime - starttime));
		
		System.out.println("\n***Java Transformation Tool Finished.***");
	}
}

