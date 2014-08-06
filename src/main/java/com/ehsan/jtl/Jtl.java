package com.ehsan.jtl;

import java.util.List;

import com.ehsan.jtl.model.StateDiagram;

public class Jtl {
	
	private static final String OUTPUT_FILENAME = "output.txt";
	
	public void run () {
		System.out.println("***Java Tranformer Tool Started***\n");
		
		InputStateDiagram inputStateDiagram = new InputStateDiagram();
		NusmvTranslationTool translator = new NusmvTranslationTool();
		
		// Building the module from console input
		List<StateDiagram> stateDiagrams = inputStateDiagram.getStateDiagrams();
		
		// Doing the translation on state diagram
		translator.generateNusvmLang(stateDiagrams, OUTPUT_FILENAME);
		
		
		System.out.println("\n***Java Tranformer Tool Finished.***");
	}
}
