package controllers

import play.api._
import play.api.mvc._
import javax.inject.Inject
import scala.concurrent.Future
import play.api.libs.ws._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

object TMP {
  
  def requestAccessToken(tmpCode: String) = {
    val accessTokenRequestUrl = "https://github.com/login/oauth/access_token"
    val request = WS.url(accessTokenRequestUrl).withQueryString(
      "client_id" -> sys.env("GITHUB_APP_CLIENT_ID"),
      "client secret" -> sys.env("GITHUB_APP_CLIENT_SECRET"),
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
          println(println(jsResponse.body))
      }
      
      /*
      (Json.parse(jsResponse.body) \ "error").toOption match {
        case Some(errortitle) => println(s"Github refused access token request ― returned error information: \n${jsResponse.body}")
        case None => println(println(jsResponse.body)) 
      }
      */
    }
  }
}

object WebhookPayload extends Controller {
  
  def apply = Action(parse.json) { request =>
    println(request.body)
    Ok("Thanks for the webhook payload")
  }
}