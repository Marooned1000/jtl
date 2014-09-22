package com.ehsan.jtl;

import java.util.List;

import com.ehsan.jtl.model.StateDiagram;
import com.ehsan.jtl.util.Constants;

public class Jtl {
	
	private static final String OUTPUT_FILENAME = "output.txt";
	
	public void run () {
		System.out.println("***Java Transformation Tool Started***\n");
		
		InputStateDiagram inputStateDiagram = new InputStateDiagram();
		NusmvTranslationTool translator = new NusmvTranslationTool();
		
		// Building the module from console input
		List<StateDiagram> stateDiagrams = inputStateDiagram.getStateDiagrams();
		String[] formulas = inputStateDiagram.getSpecifications();
		
		// Doing the translation on state diagram
		translator.generateNusvmLang(stateDiagrams, formulas, OUTPUT_FILENAME, Constants.FORMULA_INPUT_FILENAME);
		
		System.out.println("\n***Java Transformation Tool Finished.***");
	}
}

