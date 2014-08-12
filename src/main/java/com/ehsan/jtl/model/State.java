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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}

