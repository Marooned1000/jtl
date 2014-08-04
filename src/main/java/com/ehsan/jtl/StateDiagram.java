package com.ehsan.jtl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ehsan.jtl.model.Action;

public class StateDiagram {
	
	String module;
	String argument;
	Set<String> actions = new HashSet<String>();
	String initialState;
	
	// Map <State, Map<Action,State>>
	private Map<String, Map<Action, String>> stateDiagram = new HashMap<String, Map<Action, String>>();

	public void addState (String state) {
		if (state.trim().isEmpty()) return;
		if (stateDiagram.get(state) == null)
			stateDiagram.put(state, null);
	}
	
	public void addTransitionState(String state, String actionStr,	String type, String transition) {
		if (state.trim().isEmpty() || actionStr.trim().isEmpty()) return;
		Action action = new Action(actionStr, type);
		Map<Action, String> transitionState; 
		if (stateDiagram.get(state) != null) {
			transitionState = stateDiagram.get(state);
		} else {
			transitionState = new HashMap<Action, String>();
			stateDiagram.put(state, transitionState);
		}
		
		transitionState.put(action, transition);
	}
	public Set<Action> getActionList () {
		Set<Action> result = new HashSet<Action>();
		for (Map<Action, String> transitionState: stateDiagram.values()) {
			if (transitionState == null) continue;
			result.addAll(transitionState.keySet() != null ? transitionState.keySet() : Collections.<Action>emptySet());
		}
		return result;
	}
	
	public Set<String> getActionListOfType (String type) {
		Set<String> result = new HashSet<String>();
		for (Map<Action, String> transitionState: stateDiagram.values()) {
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

	// Getters and Setters
	public Map<String, Map<Action, String>> getStateDiagram() {
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

	public String getInitialState() {
		return initialState;
	}
	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}
	
}
