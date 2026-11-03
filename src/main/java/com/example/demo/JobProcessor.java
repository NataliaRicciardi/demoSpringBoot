package com.example.demo;

public class JobProcessor {
	
	public void processJob(Job jobTask) throws InterruptedException {
		System.out.println("Starting job " + jobTask.getId() + ": " + jobTask.getName());
		
		Thread.sleep(5000);
		
		System.out.println("Job " + jobTask.getId() + " finished.");
	}
}
