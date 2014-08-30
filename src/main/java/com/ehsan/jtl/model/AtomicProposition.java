package com.ehsan.jtl.model;

public class AtomicProposition {
	String moduleAtomicProposition;
	String argumentAtomicProposition;
	State stateAtomicProposition;
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((argumentAtomicProposition == null) ? 0
						: argumentAtomicProposition.hashCode());
		result = prime
				* result
				+ ((moduleAtomicProposition == null) ? 0
						: moduleAtomicProposition.hashCode());
		result = prime
				* result
				+ ((stateAtomicProposition == null) ? 0
						: stateAtomicProposition.hashCode());
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
		AtomicProposition other = (AtomicProposition) obj;
		if (argumentAtomicProposition == null) {
			if (other.argumentAtomicProposition != null)
				return false;
		} else if (!argumentAtomicProposition
				.equals(other.argumentAtomicProposition))
			return false;
		if (moduleAtomicProposition == null) {
			if (other.moduleAtomicProposition != null)
				return false;
		} else if (!moduleAtomicProposition
				.equals(other.moduleAtomicProposition))
			return false;
		if (stateAtomicProposition == null) {
			if (other.stateAtomicProposition != null)
				return false;
		} else if (!stateAtomicProposition.equals(other.stateAtomicProposition))
			return false;
		return true;
	}	
	
	
}
