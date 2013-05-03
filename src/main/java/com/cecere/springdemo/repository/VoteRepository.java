package com.cecere.springdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cecere.springdemo.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    
    //@Query("select projectid,SUM(value)/count(*) from Vote where username !='adam' AND username !='bob' group by projectid")
    List<Vote> findByProjectId(Long projectId);
    

    List<Vote> findByUsername(String username);
}
