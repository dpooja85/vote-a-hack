package com.cecere.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cecere.springdemo.controller.SpringRestDemoController;
import com.cecere.springdemo.controller.VotingController;
import com.cecere.springdemo.service.SpringRestDemoService;
import com.cecere.springdemo.service.VotingService;

@Configuration
public class ControllerConfig {
	//dependencies from other configurations
	@Autowired
	private SpringRestDemoService springRestDemoService;
	
	@Autowired
	private VotingService votingService;
	
	
	@Bean
	public SpringRestDemoController getSpringRestDemoController(){
		return new SpringRestDemoController(springRestDemoService);
	}
	
	@Bean
	public VotingController getVotingController(){
	    return new VotingController(votingService);
	}
}
