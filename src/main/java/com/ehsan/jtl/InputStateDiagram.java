package com.ehsan.jtl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ehsan.jtl.model.State;
import com.ehsan.jtl.model.StateDiagram;
import com.ehsan.jtl.util.Constants;
import com.ehsan.jtl.util.FileUtil;

public class InputStateDiagram {
	public List<StateDiagram> getStateDiagrams() {
		List<StateDiagram> stateDiagrams = new ArrayList<StateDiagram>();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		while (true) {

			StateDiagram stateDiagram = new StateDiagram();

			System.out.println("Please insert the Name of the model (# to end): ");
			String module = scanner.nextLine();
			if (module.equals("#") || module.trim().isEmpty()) break;

			stateDiagram.setModule(module);

			System.out.println("What are the arguments of this model: ");
			String argument = scanner.nextLine();
			stateDiagram.setArgument(argument);

			while (true) {
				System.out.println("Please enter the states of the model and press enter after each state"+stateDiagram.getStateNames()+": (# to end)");
				String stateStr = scanner.nextLine();
				if (stateStr.equals("#") || stateStr.trim().isEmpty()) break;

				State state = new State(stateStr);
				stateDiagram.addState(state);

			}

			System.out.println("Please enter the initial state of the model"+stateDiagram.getStateNames()+": ");
			String input = scanner.nextLine();
			if (stateDiagram.getStateWithName(input) != null) {
				stateDiagram.setInitialState(stateDiagram.getStateWithName(input));
			} else {
				System.out.println("Error: State doesnt exist, defaulting initial state to a random state");
				stateDiagram.setInitialState(stateDiagram.getStateDiagram().keySet().iterator().next());
			}

			for (State state: stateDiagram.getStateDiagram().keySet()) {
				// Asking for commitment
				System.out.println("Do you have a commitment in state "+state.getName()+" (yes/no)?");
				String commitment = scanner.nextLine();
				if (commitment.equalsIgnoreCase("yes")) {
					System.out.println("What is the state where this commitment is fulfilled"+stateDiagram.getStateNames()+"?");
					String commitedTo = scanner.nextLine();
					State commitedToState = stateDiagram.getStateWithName(commitedTo);
					if (commitedToState == null) {
						System.out.println("Error: State not found");
					} else { 
						state.setCommitment(true);
						state.setCommitedTo(commitedToState);

						stateDiagram.addTransitionState(state.getName(), "Alpha_"+stateDiagram.getModuleShortName(), Constants.DEFAULT_AGENT_NAME, commitedToState.getName());
						stateDiagram.addTransitionState(state.getName(), "Beta_"+stateDiagram.getModuleShortName(), Constants.DEFAULT_AGENT_NAME, state.getName());
						stateDiagram.addTransitionState(commitedToState.getName(), "Beta_"+stateDiagram.getModuleShortName(), Constants.DEFAULT_AGENT_NAME, commitedToState.getName());
						stateDiagram.addTransitionState(commitedToState.getName(), "Gamma_"+stateDiagram.getModuleShortName(), Constants.DEFAULT_AGENT_NAME, state.getName());
					}
				}

				
				// Asking for tansitions/actions
				while (true) {
					System.out.printf("From state %s, enter the actions and press enter after each, press # to end\n", state.getName());
					String action = scanner.nextLine();
					if (action.equals("#") || action.trim().isEmpty()) break;

					System.out.printf("For state %s, Action %s, who is performing this action (if the current agent performs the action, press enter; otherwise, insert the name of the agent (argument)): \n", 
							state.getName(), action);//, Constants.DEFAULT_AGENT_NAME);
					String type = scanner.nextLine();
					if (type.trim().isEmpty()) type = Constants.DEFAULT_AGENT_NAME;

					System.out.printf("From state %s with Action %s, enter the target state%s: \n", 
							state.getName(), action, stateDiagram.getStateNames());
					String transition = scanner.nextLine();

					if (transition.equals("#") || 
							transition.trim().isEmpty() ||
							stateDiagram.getStateWithName(transition) == null) {
						System.out.println("Error: State " + transition + " doesn't exist");
						continue;
					}
					stateDiagram.addTransitionState(state.getName(), action, type, transition);
				}
			}
			stateDiagrams.add(stateDiagram);
		}

		//scanner.close();
		return stateDiagrams;
	}
	
	
	public String[] getSpecifications() {
		Scanner scanner = new Scanner(System.in);
		String[] specifications = null;

		System.out.println("Specifictions, do you want to enter specs from console or have them read them from a file (file,console)?");
		String input = scanner.nextLine();
		
		if (input.startsWith("f") || input.startsWith("F")) {
			System.out.println("Please enter input filename (default: "+ Constants.FORMULA_INPUT_FILENAME +")?");
			input = scanner.nextLine();
			String filename = Constants.FORMULA_INPUT_FILENAME;
			if (!input.trim().isEmpty()) {
				filename = input;
			}
			try {
				specifications = FileUtil.readLines(filename);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Cannot open input file");
			} finally {
				scanner.close();
			}
		} else {
			List<String> specList = new ArrayList<String>();
			while (true) {
				System.out.println("Please insert the new specefiction(# to end): ");
				String spec = scanner.nextLine();
				if (spec.equals("#") || spec.trim().isEmpty()) break;
				specList.add(spec);
			}
			specifications = specList.toArray(new String[specList.size()]);
		}
		
		//scanner.close();
		return specifications;
	}
}

