package com.ehsan.jtl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StateDiagram {
	
	String module;
	String argument;
	Set<String> actions = new HashSet<String>();
	
	// Map <State, Map<Action,State>>
	private Map<String, Map<String, String>> stateDiagram = new HashMap<String, Map<String, String>>();

	public void addState (String state) {
		if (state.trim().isEmpty()) return;
		if (stateDiagram.get(state) == null)
			stateDiagram.put(state, null);
	}
	
	public void addTransitionState(String state, String action,	String transition) {
		if (state.trim().isEmpty() || action.trim().isEmpty()) return;
		Map<String, String> transitionState; 
		if (stateDiagram.get(state) != null) 
			transitionState = stateDiagram.get(state);
		else 
			transitionState = new HashMap<String, String>();
		
		transitionState.put(action, transition);
	}
	public Set<String> getActionList () {
		Set<String> result = new HashSet<String>();
		for (Map<String, String> transitionState: stateDiagram.values()) {
			result.addAll(transitionState.keySet());
		}
		return result;
	}

	// Getters and Setters
	public Map<String, Map<String, String>> getStateDiagram() {
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
}
