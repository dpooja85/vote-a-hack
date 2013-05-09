package com.davececere.examples.scala.service

import org.squeryl.SessionFactory
import org.squeryl.Session
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.Table
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.KeyedEntity
	

//domain objects
case class Project(val id:Long,val name:String,val description:String) extends KeyedEntity[Long]
case class Vote(val id:Long,val value:Long,val projectId:Long,val username:String) extends KeyedEntity[Long]
case class Tally(val name:String, val averagePopularVote:Float, val adamVote:Float,val bobVote:Float,val finalScore:Float)

trait ObjectService {
  def findAllProjects: List[Project]
  def findAllTallies: List[Tally]
  def createVote(vote:Vote): Vote
}


object DatabaseService extends Schema with ObjectService {


	//runs just during initialization of instance. Typical squeryl initialization code
	Class.forName("com.mysql.jdbc.Driver");
	SessionFactory.concreteFactory = Some( ()=>  
		Session.create(java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/voting","root",""),new MySQLAdapter)
    )

    val votes:Table[Vote] = table[Vote]
	val projects:Table[Project] = table[Project]
    
    def findAllProjects:List[Project] = {
      //will create or join current transaction
      inTransaction {
        //the squeryl dsl using scala paradigms to create database queries
        //this is a typical way to apply an anonymous function to a collection
        from(projects)(s => select(s)).toList
      }
    }
	def findAllTallies:List[Tally] = {
      //will create or join current transaction
      inTransaction {
    	for {
    	  project <- findAllProjects
    	} yield buildTally(project,votes.where(v => v.projectId === project.id).elements.toList)
      }
	}
	  
    def createVote(vote:Vote):Vote = {
      //default values and insert
      inTransaction {
        val prevVote = votes.where(v => v.username === vote.username and v.projectId === vote.projectId)
        if(prevVote.isEmpty) {
        	votes.insert(vote.copy(value=defaultVoteValue(vote.value),username=defaultVoteUsername(vote.username)))
        }else{
          val newVote = prevVote.single.copy(value = vote.value)
          votes.update(newVote)
          newVote
        }
        	  
      }
    }
    
    def defaultVoteValue(v:Long):Long = if (v > 10) 10 else v
    
    def defaultVoteUsername(u:String):String = if (u == null) "" else u
    
    def buildTally(project:Project,votes:List[Vote]):Tally = {
      
	  val publicVotes = votes.filter(v => v.username != "bob" && v.username != "adam")
	  val adamVotes = votes.filter(v => v.username=="adam")
	  val adamVoteScore = if (adamVotes.isEmpty) 0 else adamVotes(0).value
	  val bobVotes = votes.filter(v => v.username =="bob")
	  val bobVoteScore = if (bobVotes.isEmpty) 0 else bobVotes(0).value
	  val averagePopularVote = if(publicVotes.isEmpty) 0 else publicVotes.foldLeft(0L)((a,b) => a + b.value) / publicVotes.length
	  val finalScore = averagePopularVote + adamVoteScore + bobVoteScore

	  new Tally(project.name,averagePopularVote,adamVoteScore,bobVoteScore,finalScore)
      
	}
}
