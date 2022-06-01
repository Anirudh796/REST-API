package com.restapi;

import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.restapi.timertask.CustomTimertask;

@SpringBootApplication
@ComponentScan({"com.*"})
@EnableScheduling
public class RestApiApplication {
	
	@Autowired
    public Timer timer;
	
	@Autowired
    public CustomTimertask task;

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
    public void startScheduler()
    {
       
        timer.schedule(task,1000,30000);

    }

}
