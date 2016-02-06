package controllers

import play.api._
import play.api.mvc._
import javax.inject.Inject
import scala.concurrent.Future
import play.api.libs.ws._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object HttpClient {
  def requestAccessToken(tmpCode: String) = {
    val accessTokenRequestUrl = "https://github.com/login/oauth/access_token"
    WS.url(accessTokenRequestUrl)
      .withQueryString(
          "client_id" -> "9eb853c68e2e3a7e7cd2",
          "client secret" -> "",
          "code" -> tmpCode).get map { jsResponse => 
            println(jsResponse.body)
          }
  }
}

object OauthCallback extends Controller {
  def apply(tmpCode: String) = Action {
    HttpClient.requestAccessToken(tmpCode)
    Ok("Thanks for calling our oath callback. You should be a Github server now authorizing our app for a user, if you call in here.")
  }
}