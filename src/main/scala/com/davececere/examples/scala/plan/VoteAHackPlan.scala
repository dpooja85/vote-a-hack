package com.davececere.examples.scala.plan

import unfiltered.filter.Plan
import javax.servlet.ServletRequest
import org.clapper.avsl.Logger
import unfiltered.request._
import unfiltered.response._
import net.liftweb.json._
import org.squeryl.Schema
import org.squeryl.KeyedEntity
import com.davececere.examples.scala.service.DatabaseService
import com.davececere.examples.scala.service.ObjectService
import com.davececere.examples.scala.service.Project
import com.davececere.examples.scala.service.Vote
import com.davececere.examples.scala.service.Tally

class AssembledPlan extends VoteAHackPlan(DatabaseService) {
  
}

//A Plan that has a dependency on ObjectService
class VoteAHackPlan(service:ObjectService) extends Plan {
  val logger = Logger(classOf[VoteAHackPlan])
  //used implicitly when translating our objects to json
  implicit val formats=Serialization.formats(ShortTypeHints(List(classOf[Project],classOf[Vote],classOf[Tally])))
 
  /*
   * We first match the request by path using match{case} expressions.
   * Then we match again by HttpMethod to figure out the action. These can be combined but it results
   * in lots of repeated Path() matches
   */
  def intent ={
     // match /project
     case req @ Path(Seg("project" :: Nil)) => req match {
       case req @ GET(_) =>
       	 logger.debug("GET /project/")
       	 val projects = service.findAllProjects
       	 JsonContent ~> Ok ~> ResponseString(Serialization.write(projects))
       case _ => Pass //not interested. let a different plan deal with this
    }
    case req @ Path(Seg("vote" :: Nil)) => req match {
       // req @ binds whatever matched to the req variable so it can be used later
       case req @ POST(_) => 
         logger.debug("POST /vote/")
         // you can't get the request body from the match because it gets consumed and would only match once.
         // we use liftweb to read the body and deserialize the json now that we know we need it
         val vote = Serialization.read[Vote](Body.reader(req))
         // we build the response saying it will contain JsonContent, 201 http code (Created), and 
         // then the serialized object using liftweb again
         JsonContent ~> Created ~> ResponseString(Serialization.write(service.createVote(vote)))
       case _ => Pass //not interested. let a different plan deal with this
    }
    case req @ Path(Seg("tally" :: Nil)) => req match {
       case req @ GET(_) =>
       	 logger.debug("GET /tally/")
       	 val tallies = service.findAllTallies
       	 JsonContent ~> Ok ~> ResponseString(Serialization.write(tallies))
       // req @ binds whatever matched to the req variable so it can be used later
      case _ => Pass //not interested. let a different plan deal with this
    }
  }
}
