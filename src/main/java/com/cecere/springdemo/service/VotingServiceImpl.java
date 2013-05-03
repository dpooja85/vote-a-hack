package com.cecere.springdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cecere.springdemo.domain.Project;
import com.cecere.springdemo.domain.Tally;
import com.cecere.springdemo.domain.Vote;
import com.cecere.springdemo.repository.ProjectRepository;
import com.cecere.springdemo.repository.VoteRepository;

public class VotingServiceImpl implements VotingService {

    private ProjectRepository projectRepository;
    private VoteRepository voteRepository;
    
    public VotingServiceImpl(ProjectRepository pRep, VoteRepository vRep){
        projectRepository = pRep;
        voteRepository = vRep;
    }
    
    @Transactional
    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    @Override
    public Vote createVote(Vote v) {
        if(v.getValue() > 10)
            v.setValue(10L);
        if(v.getUsername() == null)
            v.setUsername("");
        List<Vote> votes = voteRepository.findByUsername(v.getUsername());
        for(Vote userVote: votes){
            //update previous vote
            if(userVote.getProjectId() == v.getProjectId()){
                userVote.setValue(v.getValue());
                voteRepository.save(userVote);
                return userVote;
            }
        }
        return voteRepository.save(v);
    }

    @Transactional
    @Override
    public List<Tally> getTallies() {
        List<Tally> tallies = new ArrayList<Tally>();
        List<Project> projects = projectRepository.findAll();
        for(Project p: projects){
            List<Vote> votes = voteRepository.findByProjectId(p.getId());
            long numVotes = votes.size();
            long total = 0;
            long bobvote =0;
            long adamvote = 0;
            for(Vote v: votes){
                if(v.getUsername().equals("bob")){
                    bobvote = v.getValue();
                    numVotes--;
                }else if(v.getUsername().equals("adam")){
                    adamvote = v.getValue();
                    numVotes--;
                }else{ 
                    total += v.getValue();
                }
            }
            Tally t = new Tally();
            t.setAveragePopularVote(Float.valueOf(total/numVotes));
            t.setAdamVote(Float.valueOf(adamvote));
            t.setBobVote(Float.valueOf(bobvote));
            t.setFinalScore(Float.valueOf((t.getAveragePopularVote()+adamvote+bobvote)));
            t.setName(p.getName());
            tallies.add(t);
        }
        
        //sort?
        return tallies;
    }

}
