package com.ehsan.jtl;

import java.util.Scanner;

public class InputStateDiagram {
	public StateDiagram getStateDiagram() {
		StateDiagram stateDiagram = new StateDiagram();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Module: ");
	    String module = scanner.nextLine();
	    stateDiagram.setModule(module);
	    
	    System.out.println("Argument: ");
	    String argument = scanner.nextLine();
	    stateDiagram.setArgument(argument);
	    
	    while (true) {
	    	System.out.println("State"+stateDiagram.getStateDiagram().keySet()+": (# to end)");
	    	String state = scanner.nextLine();
	    	if (state.equals("#") || state.trim().isEmpty()) break;
	    	stateDiagram.addState(state);
	    }
	    
	    for (String state: stateDiagram.getStateDiagram().keySet()) {
	    	while (true) {
		    	
	    		System.out.printf("For State %s, Enter Action: (# to end)\n", state);
		    	String action = scanner.nextLine();
		    	if (action.equals("#") || action.trim().isEmpty()) break;
		    	
		    	System.out.printf("For State %s, Action %s, Enter Transition State%s: (# to end)\n", 
		    			state, action, stateDiagram.getStateDiagram().keySet());
		    	String transition = scanner.nextLine();
		    	if (transition.equals("#") || transition.trim().isEmpty()) break;
		    	
		    	if (!stateDiagram.getStateDiagram().keySet().contains(transition)) {
		    		System.out.println("Error: State " + transition + " doesn't exist");
		    		continue;
		    	}
		    	stateDiagram.addTransitionState(state, action, transition);
		    }
	    }
	    
	    scanner.close();
		return stateDiagram;
	}
}
