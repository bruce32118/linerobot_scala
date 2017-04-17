package com.bruce.robotservice
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.concurrent.duration._
import scala.io.StdIn
import akka.http.scaladsl.model._
import akka.http.scaladsl.Http.ServerBinding
import scala.concurrent.Future
import util.Properties
import HttpProtocols._
import akka.stream.scaladsl._
import scala.util.{ Failure, Success }
import scalaj.http._
import scalaj.http.Http
import java.net._

object robotstart{
         
	private val (host, port) = (getlocalIp(), Properties.envOrElse("PORT", "8080").toInt)
	private val (channelID, channelSecret) = ("yourchannelID", "yourchannelSecret")
        private val (accesstoken) = ("youraccesstoken")

 	def main(args:Array[String]){

		implicit val system = ActorSystem("robot-system")
		implicit val materializer = ActorMaterializer()
		implicit val executionContext = system.dispatcher


		val route = path("callback")(
                       post(
                        entity(as[String]){
                         json =>
                             println("json :" + json)
				
                             val replyToken = json.split("replyToken")(1).split("\",")(0).split("\":\"")(1)
                             println("token : " + replyToken)
				
                             val user_text = json.split("text")(2).split(":\"")(1).split("\"")(0).replace("！","!")
                             println("user_text :"+ user_text)
				
                             val replydata = tojson(replyToken, user_text)                     
                             println("replydata :" + replydata.get)
				
                              if(replydata != None){

                              val result = scalaj.http.Http("https://api.line.me/v2/bot/message/reply")
                                    .postData(replydata.get)
                                    .headers(Seq("Authorization" -> ("Bearer "+accesstoken)))
                                    .headers(Seq("content-Type" -> "application/json"))   
                             
                               println("result.asString = : " + result.asString)
                              
                              }

                             complete(StatusCodes.OK)
                          }
                       )
                     )

			
		val bindingFuture = akka.http.scaladsl.Http().bindAndHandle(route, host, port)
			
		sys.addShutdownHook(new Thread {
				override
				def run() {
					print("close")
					shutdownHook(system, bindingFuture)
					}
				})
				println(s"Server online at http://$host:$port/\nPress 'Ctrl+C' to stop...")
				while(true){}
			}
			
	def shutdownHook(system: ActorSystem, bindingFuture: Future[ServerBinding]) {
			
		implicit val executionContext = system.dispatcher	
		bindingFuture
			.flatMap(_.unbind()) 
			.onComplete(_ => system.terminate()) 	
		
		bindingFuture
			.onFailure {
				case ex: Exception =>
				println(s"Failed to bind to $host:$port")
						
			}
			
			
		}

    	def getlocalIp() : String = InetAddress.getLocalHost.getHostAddress
		
}


  
	
	


