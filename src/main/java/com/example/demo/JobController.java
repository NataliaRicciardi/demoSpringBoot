package com.example.demo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {
	
	private final List<Job> jobs = new CopyOnWriteArrayList<>(List.of(
			new Job(1, "Process customer records", "QUEUED"),
			new Job(2, "Generate report", "QUEUED"),
			new Job(3, "Send email notifications", "QUEUED")
		));
	private final AtomicInteger nextJobId = new AtomicInteger(4);
	private final JobProcessor processor = new JobProcessor();
	private final ExecutorService threadpool;
	
	public JobController(ExecutorService jobExecutor) {
		this.threadpool = jobExecutor;
	}
	
	@GetMapping("/api/jobs")
	public List<Job> getJobs() {
		return jobs;
	}
	
	@PostMapping("/api/jobs")
	public List<Job> postJobs(@RequestBody JobRequest request) {
		
		Job job = new Job(nextJobId.getAndIncrement(), request.getName(), "QUEUED");
		
		jobs.add(job);
		
		try {
			threadpool.submit(() -> {
				try {
					processor.processJob(job);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				    System.out.println("Job processing was interrupted.");
				}
			});
		} catch (RejectedExecutionException e) {
			job.setStatus("REJECTED");
			System.out.println("Job could not be submitted. Processor busy!");
		}
		
		
		return jobs;
	}
}
