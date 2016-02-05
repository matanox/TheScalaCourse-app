package controllers

import play.api._
import play.api.mvc._

object Root extends Controller {
  def apply = Action {
    Ok("This is the backend server for TheScalaCourse's github application. Probably, you have nothing to do here.")
  }  
}

object OauthCallback extends Controller {
  def apply = Action {
    //https://github.com/login/oauth/access_token
    Ok("Thanks for calling our oath callback. You should be a Github server now authorizing our app for a user, if you call in here.")
  }    
}

