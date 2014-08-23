package com.ehsan.jtl.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StateDiagram {
	
	String module;
	String argument;
	Set<String> actions = new HashSet<String>();
	State initialState;
	
	// Map <State, Map<Action,State>>
	private Map<State, Map<Action, State>> stateDiagram = new HashMap<State, Map<Action, State>>();
	
	// Atomic Proposition
	String moduleAtomicProposition;
	String argumentAtomicProposition;
	State stateAtomicProposition;

	public void addState (String stateStr) {
		if (stateStr.trim().isEmpty()) return;
		State state = new State (stateStr);
		if (stateDiagram.get(state) == null)
			stateDiagram.put(state, null);
	}
	
	public void addState (State state) {
		if (state == null) return;
		if (getStateWithName(state.getName()) == null)  // State needs better equal method
			stateDiagram.put(state, null);
	}
	
	public void addTransitionState(String stateStr, String actionStr, String type, String transition) {
		if (stateStr.trim().isEmpty() || actionStr.trim().isEmpty()) return;
		if (getStateWithName(transition) == null) {
			System.out.println("Error: Cannot find transition state");
		}
		Action action = new Action(actionStr, type);
		State state = new State(stateStr);
		Map<Action, State> transitionState; 
		if (stateDiagram.get(state) != null) {
			transitionState = stateDiagram.get(state);
		} else {
			transitionState = new HashMap<Action, State>();
			stateDiagram.put(state, transitionState);
		}
		transitionState.put(action, getStateWithName(transition));
	}
	
	public State getStateWithName (String stateStr) {
		for (State state: stateDiagram.keySet()) {
			if (state.getName().equals(stateStr)) {
				return state;
			}
		}
		return null;
	}
	
	public Set<String> getStateNames () {
		Set<String> result = new HashSet<String>();
		for (State state: stateDiagram.keySet()) {
			result.add(state.getName());
		}
		return result;
	}
	
	public Set<Action> getActionList () {
		Set<Action> result = new HashSet<Action>();
		for (Map<Action, State> transitionState: stateDiagram.values()) {
			if (transitionState == null) continue;
			result.addAll(transitionState.keySet() != null ? transitionState.keySet() : Collections.<Action>emptySet());
		}
		return result;
	}
	
	public Set<String> getActionListOfType (String type) {
		Set<String> result = new HashSet<String>();
		for (Map<Action, State> transitionState: stateDiagram.values()) {
			if (transitionState == null) continue;
			if (transitionState.keySet() != null) {
				for (Action action: transitionState.keySet()) {
					if (action.getType().equals(type)) {
						result.add(action.getName());
					}
				}
			}
		}
		return result;
	}
	
	public String getModuleShortName () {
		if (module == null) return null;
		if (module.length() < 3) return module.toLowerCase();
		return module.substring(0,3).toLowerCase();
	}

	// Getters and Setters
	public Map<State, Map<Action, State>> getStateDiagram() {
		return stateDiagram;
	}

	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}

	public String getArgument() {
		return argument;
	}
	public void setArgument(String argument) {
		this.argument = argument;
	}

	public Set<String> getActions() {
		return actions;
	}
	public void setActions(Set<String> actions) {
		this.actions = actions;
	}

	public State getInitialState() {
		return initialState;
	}
	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public String getModuleAtomicProposition() {
		return moduleAtomicProposition;
	}

	public void setModuleAtomicProposition(String moduleAtomicProposition) {
		this.moduleAtomicProposition = moduleAtomicProposition;
	}

	public String getArgumentAtomicProposition() {
		return argumentAtomicProposition;
	}

	public void setArgumentAtomicProposition(String argumentAtomicProposition) {
		this.argumentAtomicProposition = argumentAtomicProposition;
	}

	public State getStateAtomicProposition() {
		return stateAtomicProposition;
	}

	public void setStateAtomicProposition(State stateAtomicProposition) {
		this.stateAtomicProposition = stateAtomicProposition;
	}		
	
}

