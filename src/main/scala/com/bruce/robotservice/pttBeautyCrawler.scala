package com.bruce.robotservice
import org.jsoup._
import scala.collection.JavaConverters._

object pttBeautyCrawler {

    private val (ptt,pttBeautyAddress) = ("https://www.ptt.cc/","bbs/Beauty/index.html")
    
    def apply() : Map[Int,String] = {

      val doc = Jsoup.connect( ptt + pttBeautyAddress ).get
      val elements = doc.select("a.btn.wide").asScala
      var main = "" 
      var beautyURL = Map( 1121 -> "test") 
      var count : Int = 0

      for(element <- elements) { 
        if(element.attr("href").contains("index2")) main = element.attr("href")
      }
   
      val ptt_num = main.split("index")(1).split("\\.")(0) 
     
      for(subtraction <- -1 to 30){
          
        val ptt_page = ptt_num.toInt - subtraction
        val address = "https://www.ptt.cc/bbs/Beauty/index" + ptt_page + ".html"
        val doc2 = Jsoup.connect(address).get
        val elementsxs = doc2.select("div.r-ent").asScala 

        for(elementsx <- elementsxs) {

         if(elementsx.select("span.hl.f1").text != "" || (elementsx.select("span.hl.f3").text != "" && elementsx.select("span.hl.f3").text.toInt > 30)){
               
           val big = ptt + elementsx.select("a").attr("href")
           val bightml = Jsoup.connect(big).get
           val bigelements = bightml.select("div.richcontent").select("a").asScala

           for(bigelement <- bigelements){
               
             if(bigelement.attr("href").contains("imgur.com")) {             

                val beauty_image_url = "https:" + bigelement.attr("href") + ".jpg" + "," + big
                beautyURL += (count -> beauty_image_url)
                count = count + 1
                 
             }
           }
         }
        }       
       }
    beautyURL 
    }
}

