package com.ehsan.jtl;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.ehsan.jtl.model.StateDiagram;
import com.ehsan.jtl.util.Constants;

public class Jtl {
	
	private static final String OUTPUT_FILENAME = "output.txt";
	
	public void run () {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("***Java Transformation Tool Started***\n");				
		
		InputStateDiagram inputStateDiagram = new InputStateDiagram();
		NusmvTranslationTool translator = new NusmvTranslationTool();
		
		// Building the module from console input
		List<StateDiagram> stateDiagrams = inputStateDiagram.getStateDiagrams(scanner);
				
		System.out.println("Fully or Purely(Purely default):" );
		String input = scanner.nextLine();
		
		long starttime = new Date().getTime();
		
		String[] formulas = inputStateDiagram.getSpecifications();
		
		// Doing the translation on state diagram
		translator.generateNusvmLang(stateDiagrams, formulas, OUTPUT_FILENAME, Constants.FORMULA_INPUT_FILENAME, input);
		
		long endtime =  new Date().getTime();		
	
		System.out.println(String.format("Time Took: %.5f \n", (endtime - starttime)/1000.0));
		
		System.out.println("\n***Java Transformation Tool Finished.***");
	}
}

