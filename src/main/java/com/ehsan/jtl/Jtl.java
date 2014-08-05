package com.ehsan.jtl;

import com.ehsan.jtl.model.StateDiagram;

public class Jtl {
	
	private static final String OUTPUT_FILENAME = "output.txt";
	
	public void run () {
		System.out.println("***Java Tranformer Tool Started***\n");
		
		InputStateDiagram inputStateDiagram = new InputStateDiagram();
		NusmvTranslationTool translator = new NusmvTranslationTool();
		
		// Building the module from console input
		StateDiagram stateDiagram = inputStateDiagram.getStateDiagram();
		
		// Doing the translation on state diagram
		translator.generateNusvmLang(stateDiagram, OUTPUT_FILENAME);
		
		System.out.println("\n***Java Tranformer Tool Finished.***");
	}
}
