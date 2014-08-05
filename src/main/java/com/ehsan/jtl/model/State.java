package com.ehsan.jtl.model;

public class State {
	String name;
	boolean commitment;
	State commitedTo;
	
	public State(String name) {
		super();
		this.name = name;
	}
	
	public State(String name, boolean commitment, State commitedTo) {
		super();
		this.name = name;
		this.commitment = commitment;
		this.commitedTo = commitedTo;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isCommitment() {
		return commitment;
	}
	public void setCommitment(boolean commitment) {
		this.commitment = commitment;
	}
	public State getCommitedTo() {
		return commitedTo;
	}
	public void setCommitedTo(State commitedTo) {
		this.commitedTo = commitedTo;
	}
	
	
}
