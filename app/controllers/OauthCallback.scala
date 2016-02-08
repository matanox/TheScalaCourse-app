package controllers

import play.api._
import play.api.mvc._
import javax.inject.Inject
import scala.concurrent.Future
import play.api.libs.ws._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import scala.util.Random

object HttpClient {
  
  def requestAccessToken(tmpCode: String) = {
    
    val csrfProtection = Random.alphanumeric.take(16).mkString
           
    val accessTokenRequestUrl = "https://github.com/login/oauth/access_token"
    val request = WS.url(accessTokenRequestUrl).withQueryString(
      "client_id" -> sys.env("GITHUB_APP_CLIENT_ID"),
      "client_secret" -> sys.env("GITHUB_APP_CLIENT_SECRET"),
      "code" -> tmpCode)
    
    request.get map { jsResponse =>
      
      /*
       * At present time, response for this call should always be 200, 
       * whereas error details would be in the response body. 
       */
      if (jsResponse.status != 200) {
        println(s"Github returned an unexpected http response code (${jsResponse.status}) for our access token request. Request was:\n$request")
        throw new Exception("Github returned an unexpected http response code")
      }
      
      println(jsResponse.body)
      jsResponse.body.startsWith("error=") match {
        case true  => println(s"Github refused access token request ― returned error information: \n${jsResponse.body}")
        case false => 
          /*
           * TODO (1): take the access token from the response, per https://developer.github.com/v3/oauth/#response,
           * and do something useful with it. Right now it is only printed to the console. 
           */ 
          // 
          println(println(jsResponse.body))
      }
    }
  }
}

object OauthCallback extends Controller {
  
  /*
   * We arrive here when a user clicks a link beginning the github application authorization 
   * dance for their profile, e.g. the link included in https://github.com/matanster/TheScalaCourse-app.
   *  
   * We pluck the information from query parameters, whereas documentation seems to suggest
   * this should be coming in the body (?!). anyway it works for now.
   */
  def apply(tmpCode: Option[String], error: Option[String]) = Action {
    tmpCode match {
      case Some(tmpCode) => HttpClient.requestAccessToken(tmpCode)
      case None => 
        error match {
          case Some(error) => 
            error match {
              case s if s.contains("access_denied") => println(s"oauth callback endpoint received report of a user rejecting access for the application. nothing to be done about it (https://developer.github.com/v3/oauth/#access-denied). Raw message received:\n${error}")
              case s if s.contains("application_suspended") => println(s"oauth callback endpoint received report of this application being suspended by Github. (https://developer.github.com/v3/oauth/#application-suspended). Raw message received:\n${error}")
              case _ => println(s"oauth callback endpoint received error report:\n${error}")
            }
          case None => println(s"unexpected call received on oauth callback endpoint (is it really coming from Github?). Raw message received:\n${error}")
        }
    }
    
    /* 
     * TODO: When github refuses the request due to authentication error, and/or after the request is
     * approved by Github, the user is directed here again, or they say our response below (which is quite,
     * the same thing..). Provide something nicer for an "after authorization page" as per 
     * https://developer.github.com/v3/oauth/#parameters-1
     */
    Ok("Thanks for calling our oauth callback. You should be a Github server now authorizing our app for a Github user, if you call in here now.")
  }
}