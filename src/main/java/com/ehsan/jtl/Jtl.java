package com.ehsan.jtl;

public class Jtl {
	
	private static final String OUTPUT_FILENAME = "output.txt";
	
	public void run () {
		System.out.println("Java Tranformer Tool Started");
		
		InputStateDiagram inputStateDiagram = new InputStateDiagram();
		NusmvTranslationTool translator = new NusmvTranslationTool();
		
		// Building the module from console input
		StateDiagram stateDiagram = inputStateDiagram.getStateDiagram();
		
		// Doing the translation on state diagram
		translator.generateNusvmLang(stateDiagram, OUTPUT_FILENAME);
		
		System.out.println("Java Tranformer Tool Finished.");
	}
}
