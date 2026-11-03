package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {
	
	private List<Job> jobs = new ArrayList<>(List.of(
			new Job(1, "Process customer records", "QUEUED"),
			new Job(2, "Generate report", "COMPLETED"),
			new Job(3, "Send email notifications", "QUEUED")
		));
	private int nextJobId = 4;
	private final JobProcessor processor = new JobProcessor();
	
	@GetMapping("/api/jobs")
	public List<Job> getJobs() {
		return jobs;
	}
	
	@PostMapping("/api/jobs")
	public List<Job> postJobs(@RequestBody JobRequest request) {
		
		Job job = new Job(nextJobId, request.getName(), "QUEUED");
		nextJobId++;
		
		jobs.add(job);
		
		Thread thread = new Thread(() -> {
			try {
				processor.processJob(job);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			    System.out.println("Job processing was interrupted.");
			}
		});
		
		thread.start();
		
		return jobs;
	}
}
