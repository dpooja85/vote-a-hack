package com.cecere.springdemo.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.cecere.config.RepositoryConfig;
import com.cecere.springdemo.config.ControllerConfig;
import com.cecere.springdemo.config.ServiceConfig;
import com.cecere.springdemo.domain.DemoObject;
import com.cecere.springdemo.domain.Project;
import com.cecere.springdemo.domain.Tally;
import com.cecere.springdemo.domain.Vote;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ControllerConfig.class,RepositoryConfig.class,ServiceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class VotingControllerIT extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private VotingController controller;

    @Test
    public void testVote(){
        Vote v = buildVote();
        controller.createVote(null, v);
    }
    
    @Test
    public void testGetProjects(){
        List<Project> projects = controller.getProjects(null);
    }
    
    @Test
    public void testGetTallies(){
        List<Tally> tallies = controller.getTallies(null);
        for(Tally t: tallies){
            System.out.println(t.getName());
            System.out.println(t.getAdamVote());
            System.out.println(t.getAveragePopularVote());
            System.out.println(t.getBobVote());
            System.out.println(t.getFinalScore());
        }
    }
    
    private Vote buildVote(){
        Vote v = new Vote();
        v.setProjectId(1L);
        v.setValue(5L);
        v.setUsername("");
        return v;
    }

}
