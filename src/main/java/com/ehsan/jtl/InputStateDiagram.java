package com.ehsan.jtl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jdk.nashorn.internal.runtime.FindProperty;

import com.ehsan.jtl.model.State;
import com.ehsan.jtl.model.StateDiagram;
import com.ehsan.jtl.util.Constants;

public class InputStateDiagram {
	public List<StateDiagram> getStateDiagrams() {
		List<StateDiagram> stateDiagrams = new ArrayList<StateDiagram>();
		Scanner scanner = new Scanner(System.in);

		while (true) {

			StateDiagram stateDiagram = new StateDiagram();

			System.out.println("Enter New Module Name (# to end): ");
			String module = scanner.nextLine();
			if (module.equals("#") || module.trim().isEmpty()) break;

			stateDiagram.setModule(module);

			System.out.println("Argument: ");
			String argument = scanner.nextLine();
			stateDiagram.setArgument(argument);

			while (true) {
				System.out.println("Enter New State Name"+stateDiagram.getStateNames()+": (# to end)");
				String stateStr = scanner.nextLine();
				if (stateStr.equals("#") || stateStr.trim().isEmpty()) break;

				State state = new State(stateStr);
				stateDiagram.addState(state);

				System.out.println("Do you have a commitment in that state(yes/no)?");
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

						stateDiagram.addTransitionState(state.getName(), "Alpha_cus", Constants.DEFAULT_AGENT_NAME, commitedToState.getName());
						stateDiagram.addTransitionState(state.getName(), "Beta_cus", Constants.DEFAULT_AGENT_NAME, state.getName());
						stateDiagram.addTransitionState(commitedToState.getName(), "Beta_cus", Constants.DEFAULT_AGENT_NAME, commitedToState.getName());
						stateDiagram.addTransitionState(commitedToState.getName(), "Gamma_cus", Constants.DEFAULT_AGENT_NAME, commitedToState.getName());
					}
				}
			}

			System.out.println("Initial State"+stateDiagram.getStateNames()+": ");
			String input = scanner.nextLine();
			if (stateDiagram.getStateWithName(input) != null) {
				stateDiagram.setInitialState(stateDiagram.getStateWithName(input));
			} else {
				System.out.println("Error: State doesnt exist, defaulting initial state to a random state");
				stateDiagram.setInitialState(stateDiagram.getStateDiagram().keySet().iterator().next());
			}

			for (State state: stateDiagram.getStateDiagram().keySet()) {
				while (true) {

					System.out.printf("For State %s, Enter Action: (# to end)\n", state.getName());
					String action = scanner.nextLine();
					if (action.equals("#") || action.trim().isEmpty()) break;

					System.out.printf("For State %s, Action %s, Enter Action Type(default %s): \n", 
							state.getName(), action, Constants.DEFAULT_AGENT_NAME);
					String type = scanner.nextLine();
					if (type.trim().isEmpty()) type = Constants.DEFAULT_AGENT_NAME;

					System.out.printf("For State %s, Action %s, Enter Transition State%s: \n", 
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
