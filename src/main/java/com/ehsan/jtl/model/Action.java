package com.ehsan.jtl.model;

public class Action {
	String name;
	String type;
	
	public Action(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Action [name=" + name + ", type=" + type + "]";
	}
}

