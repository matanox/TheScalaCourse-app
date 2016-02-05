/*

package com.thescalacourse

import scala.concurrent.Future
import scala.concurrent.duration._

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.pattern.ask
import akka.io.IO

import spray.can.Http
import spray.http._
import HttpMethods._

object OauthCallback {
  
  implicit val system: ActorSystem = ActorSystem()
  implicit val timeout: Timeout = Timeout(15.seconds)
  import system.dispatcher // implicit execution context
  
  def handle = {
  
    val response: Future[HttpResponse] =
      (IO(Http) ? HttpRequest(POST, Uri("https://github.com/login/oauth/access_token"))).mapTo[HttpResponse] 
  }
  
  /* chains another request  
  // or, with making use of spray-httpx
  import spray.httpx.RequestBuilding._
  val response2: Future[HttpResponse] =
    (IO(Http) ? Get("http://spray.io")).mapTo[HttpResponse]
  */
}

*/