package com.example.demo;

public class Job {
	private int id;
	private String name;
	private String status;
	
	public Job(int id, String name, String status) {
		this.id = id;
		this.name = name;
		this.status = status;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getStatus() {
		return this.status;
	}
}
