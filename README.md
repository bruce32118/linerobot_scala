LineBot: Using scala to implement LineBot
=============
## Introduction
### 主要使用 Heroku + Scala + Line api 去實作

#### 根據上圖用Heroku去架設機器人服務系統，然後將scala code部屬到Heroku上

#### scala 所使用到的套件
* akka http 處理http request response 
* jsoup 處理html解析 
* net.liftweb 處理request裡的json格式

#### 實作功能
* 一般簡易答覆
* 串youtube api 實作點歌功能
* 撈ptt表特版 並回傳圖片

How to Use
=============

* 創建Line develop account(https://business.line.me/zh-hant/)
* 創建Heroku account(https://www.heroku.com/) 
* Download Heroku cli to your work enviroment(https://devcenter.heroku.com/articles/heroku-cli) 
* 在line develop的服務上建立一個官方帳號，並設定啟用message api服務&允許使用webhook傳遞訊息，然後取得Channel ID、Channel Secret、Webhook URL(預設是callback)、Channel Access Token的資料(先記下來等等會用到)
* git clone https://github.com/bruce32118/linerobot_scala.git 
* 將Channel ID、Channel Secret、Channel Access Token加到程式裡面

#### 部屬code到heroku上

* heroku login(輸入帳密)
* git init.....就是git的一些操作，commit完後
* heroku create
* git push heroku master(將code部屬到heroku)

#### 實測Line robot
![Imgur](http://i.imgur.com/1wpw9Es.png)<br>
![Imgur](http://i.imgur.com/r53P973.png)<br>
![Imgur](http://i.imgur.com/AEwZBFc.png)
