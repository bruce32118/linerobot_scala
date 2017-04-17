package com.bruce.robotservice
import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonAST
import net.liftweb.json.Printer._
import net.liftweb.json.JsonAST._
import scala.util.Try
import collection.mutable.Map
import scalaj.http._
import scalaj.http.Http

object tojson{
  //call pttBeautyCrawler
  private lazy val BeautyMap = pttBeautyCrawler()
  private lazy val BeautyMapsize = BeautyMap.size - 1

  def apply(replyToken: String, user_text: String) : Option[String] = {
  
  var reply_text = ""

  var reply_value : List[net.liftweb.json.JsonAST.JObject] = List(("" -> ""))  

  val robottext = Map("指令1" -> "回答1", "指令2" -> "回答2")
  

  user_text match {

   case user_text if(robottext.contains(user_text)) => {
      //配對在map裡有的指令並回覆其values值
      reply_text = robottext(user_text)
      reply_value = List(("type" -> "text") ~ ("text" -> reply_text))

   }

   case user_text if(user_text == "!指令") => {
      //列出有的指令
      reply_text = robottext.keySet.toString.split("Set\\(")(1).split("\\)")(0)     
      reply_value = List(("type" -> "text") ~ ("text" -> reply_text))
   
   }
   
   case user_text if(user_text == "Hello,world") => {
      //一開始linebot api 會 send a dummy webhook to this webhook URL. You can verify that the SSL certificate is correct.
      reply_text = "check"

   }

   case user_text if(user_text.contains("!正妹")) => {
      //撈ptt 表特版 圖片原圖連結的形式回覆
      reply_text = "image"
      val beautyUrl = BeautyMap(scala.util.Random.nextInt(BeautyMapsize))
      reply_value = List(("type" -> "text") ~ ("text" -> beautyUrl.split(",")(0)))
                          
                          }
                          
   case user_text if(user_text == "!test正妹") => {
      //撈ptt 表特版 呈現照片 表特連結 圖片原圖連結
      reply_text = "image"
      val beautyUrl = BeautyMap(scala.util.Random.nextInt(BeautyMapsize))
      val actions = List((("type" -> "uri") ~ ("label" -> "原圖連結") ~ ("uri" -> beautyUrl.split(",")(0))) , (("type" -> "uri") ~ ("label" -> "到ptt觀看此正妹~") ~ ("uri" -> beautyUrl.split(",")(1)))) 
      val text_uri_image =
                  ("type" -> "template") ~ ("altText" -> "this is a buttons template") ~ ("template" -> ("type" -> "buttons") ~ ("thumbnailImageUrl" -> beautyUrl.split(",")(0)) ~ ("text" -> "一天一妹子，煩惱遠離你") ~ ("actions" -> actions))

      reply_value = List(text_uri_image)
  }
  

   case user_text if(user_text.contains("!點歌-") && user_text.split("-").length > 1 ) => {
      //串youtube api 實作youtube 搜尋功能 返回搜尋列的第一筆結果
      reply_text = "song"
      val songtext = user_text.split("-")(1)

      val songMap =  scala.collection.immutable.Map("part"->"snippet","q"->songtext,"key"->"yourappkey")

      //根據youtube所需要驗證的key，發出get request
      val result = Http("https://www.googleapis.com/youtube/v3/search").params(songMap).asString

      val title = result.toString.split("title\": \"")(1).split("\"")(0)
      val songid = result.toString.split("videoId\": \"")(1).split("\"")(0)

      val youtube_url = title + ": https://www.youtube.com/watch?v=" + songid
   
      reply_value = List(("type" -> "text") ~ ("text" -> youtube_url))
  
   }

  }

  val replyString = compact(render(("replyToken" -> replyToken) ~ ("messages" -> reply_value)))

  reply_text match {
    case reply_text if(reply_text == "") => Try(null).toOption.flatMap{Option(_)}
    case reply_text if(reply_text == "check") => Try("check").toOption
    case _ => Try(replyString).toOption    
  }
}
	


}
