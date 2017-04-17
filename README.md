LineBot: Using scala to implement LineBot
=============
## Introduction
### 主要使用 Heroku + Scala + Line api 去實作
#### Line官方文件的流程圖
![Alt text](https://developers.line.me/wp-content/uploads/2016/09/bottrial-fig1.png)

#### 根據上圖用Heroku去架設機器人服務系統，然後將scala code部屬到Heroku上

#### scala 所使用到的套件
* akka http 處理http request response 
* jsoup 處理html解析 
* net.liftweb 處理request裡的json格式

#### 實作功能
* 一般簡易答覆
* 串youtube api 實作點歌功能
* 撈ptt表特版 並回傳圖片
