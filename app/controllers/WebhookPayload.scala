package controllers

import play.api._
import play.api.mvc._
import javax.inject.Inject
import scala.concurrent.Future
import play.api.libs.ws._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

/*
 * TODO: set up github web hooks security per https://developer.github.com/webhooks/securing/
 */

object WebhookPayload extends Controller {
  def apply = Action(parse.json) { request =>
    
    /* TODO: use proper play json (or my own macro powered) validations rather than merely .get */
    
    val body = request.body
    val sender = (body \ "sender" \ "login").get
    val organization = (body \ "organization").get

    (body \ "zen").toOption match {
      
      case Some(_) => 
        println(s"Webhook ping event received (this is a synthetic request typically only used for validating connectivity from Github, as per https://developer.github.com/webhooks/#ping-event). Raw request:\n$body")
        
      case None =>
        val repositoryName = (body \ "repository" \ "full_name").get
        val headCommit = (body \ "head_commit" \ "message").get
        println(s"""$sender pushed to $repositoryName with commit message $headCommit""")
        
    }
    Ok("Thanks for the webhook payload")
  }
}