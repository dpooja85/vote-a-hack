package com.cecere.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cecere.springdemo.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
