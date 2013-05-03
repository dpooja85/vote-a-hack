package com.cecere.springdemo.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cecere.springdemo.domain.DemoObject;
import com.cecere.springdemo.domain.Project;
import com.cecere.springdemo.domain.Tally;
import com.cecere.springdemo.domain.Vote;
import com.cecere.springdemo.service.SpringRestDemoService;
import com.cecere.springdemo.service.VotingService;

@Controller
public class VotingController {
	
	private VotingService service;
	private static final Logger logger = LoggerFactory.getLogger(VotingController.class);
	
	public VotingController(VotingService pService){
		service = pService;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/project", 
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public @ResponseBody List<Project> getProjects(Locale locale) {
		return service.getProjects();
	}

	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(
			value = "/vote", 
			method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public @ResponseBody Vote createVote(Locale locale,@RequestBody Vote newObj) {
		return service.createVote(newObj);
	}
	
        @ResponseStatus(HttpStatus.OK)
        @RequestMapping(
                        value = "/tally", 
                        method = RequestMethod.GET,
                        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
        )
        public @ResponseBody List<Tally> getTallies(Locale locale) {
                return service.getTallies();
        }
	
}
