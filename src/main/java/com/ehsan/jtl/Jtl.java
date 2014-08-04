package com.ehsan.jtl;

public class Jtl {
	
	private static final String OUTPUT_FILENAME = "output.txt";
	
	public void run () {
		InputStateDiagram inputStateDiagram = new InputStateDiagram();
		NusmvTranslationTool translator = new NusmvTranslationTool();
		
		StateDiagram stateDiagram = inputStateDiagram.getStateDiagram();
		translator.generateNusvmLang(stateDiagram, OUTPUT_FILENAME);
	}
}
