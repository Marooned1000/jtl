package com.ehsan.jtl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ehsan.jtl.model.State;
import com.ehsan.jtl.model.StateDiagram;
import com.ehsan.jtl.util.Constants;

public class InputStateDiagram {
	public List<StateDiagram> getStateDiagrams() {
		List<StateDiagram> stateDiagrams = new ArrayList<StateDiagram>();
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

						stateDiagram.addTransitionState(state.getName(), "Alpha_"+stateDiagram.getArgument(), Constants.DEFAULT_AGENT_NAME, commitedToState.getName());
						stateDiagram.addTransitionState(state.getName(), "Beta_"+stateDiagram.getArgument(), Constants.DEFAULT_AGENT_NAME, state.getName());
						stateDiagram.addTransitionState(commitedToState.getName(), "Beta_"+stateDiagram.getArgument(), Constants.DEFAULT_AGENT_NAME, commitedToState.getName());
						stateDiagram.addTransitionState(commitedToState.getName(), "Gamma_"+stateDiagram.getArgument(), Constants.DEFAULT_AGENT_NAME, state.getName());
					}
				}

				
				// Asking for tansitions/actions
				while (true) {
					System.out.printf("From state %s, enter the actions and press enter after each, press # to end\n", state.getName());
					String action = scanner.nextLine();
					if (action.equals("#") || action.trim().isEmpty()) break;

					System.out.printf("For State %s, Action %s, Who is performing this action (default %s): \n", 
							state.getName(), action, Constants.DEFAULT_AGENT_NAME);
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

		scanner.close();
		return stateDiagrams;
	}
}
