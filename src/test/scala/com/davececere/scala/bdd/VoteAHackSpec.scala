package com.davececere.examples.scala

import org.specs._
import dispatch._
import net.liftweb.json.Serialization
import net.liftweb.json.ShortTypeHints
import java.io.InputStreamReader
import org.apache.http.client.methods.HttpPost
import unfiltered.request.QParams.Fail
import com.davececere.examples.scala.plan.AssembledPlan
import com.davececere.examples.scala.plan.Project
import com.davececere.examples.scala.plan.Vote
import com.davececere.examples.scala.plan.Tally

//<< = post
//<<< = put
object VoteAHackPlanSpec extends Specification 
	with unfiltered.spec.jetty.Served {
  
  import dispatch._
  
  implicit val formats=Serialization.formats(ShortTypeHints(List(classOf[Project],classOf[Vote],classOf[Tally])))

  
  def setup = { _.filter(new AssembledPlan) } //use wired object for test since that is the plan that is really used in web.xml
  
  val http = new Http
  
  "rest api" should {
    "serve list of projects" in {
      val status = http x (host / "project" as_str) {
        case (code, _, _, _) => code
        case _ => Fail
      }
      status must_== 200
    }
  }
  
  "rest api" should {
    "serve list of tallies" in {
      val status = http x (host / "tally" as_str) {
        case (code, _, _, _) => code
        case _ => Fail
      }
      status must_== 200
    }
  }
  
  "rest api" should {
    "create single vote" in {
      val newObj:Vote = Vote(0L,5L,1,"testuser")
      http x (((host / "vote") << Serialization.write(newObj))as_str) {
        case (code, _, Some(json), _) => {
        	code must_== 201 
        	val retObj:Vote = Serialization.read(new InputStreamReader(json.getContent()))
        	retObj.value must_== 5L
        	retObj.id must_!= 0
        }
        case _ => Fail
      }
    }
  }
  
}
