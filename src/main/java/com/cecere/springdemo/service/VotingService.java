package com.cecere.springdemo.service;

import java.util.List;

import com.cecere.springdemo.domain.Project;
import com.cecere.springdemo.domain.Tally;
import com.cecere.springdemo.domain.Vote;

public interface VotingService {
    List<Project> getProjects();
    Vote createVote(Vote v);
    List<Tally> getTallies();
}
