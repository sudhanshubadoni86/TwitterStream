//Author :Sudhanshu Badoni
//Application : Twitter Streaming Application to print top 10 hastags last 60 seconds
//Date : 22-JUL-2017

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import org.apache.spark.{SparkConf, SparkContext}
import twitter4j._

object TwitterAPI {
def main(args:Array[String]){

  val consumerKey="123"
  val consumerSecret="123"
  val accessToken="123"
  val accessTokenSecret="123"
  System.setProperty("twitter4j.oauth.consumerKey",consumerKey)
  System.setProperty("twitter4j.oauth.consumerSecret",consumerSecret)
  System.setProperty("twitter4j.oauth.accessToken",accessToken)
  System.setProperty("twitter4j.oauth.accessTokenSecret",accessTokenSecret)

  val spconf=new SparkConf()
    .setAppName("myTwitterApp")
    .setMaster("local[2]").set("spark.driver.allowMultipleContexts","True");
  val sc=new SparkContext(spconf);
  sc.setLogLevel("ERROR")

  val ssc=new StreamingContext(spconf,Seconds(10))
  ssc.checkpoint("/Users/sudhanshubadoni/Documents/")
  val stream = TwitterUtils.createStream(ssc,None).window(Seconds(60),Seconds(60));
  val Top10hasttagLast60sec=stream.flatMap(a=>a.getText.split(" ")).filter(_.startsWith("#")).map(s=>(s,1)).transform(_.sortByKey(false));
  val Top10hasttagLast60secRDD=Top10hasttagLast60sec.reduceByKey(_+_)
  println("\nPopular Hashtags in last 60 seconds")
  Top10hasttagLast60secRDD.foreachRDD(rdd=>rdd.take(10).foreach(println))
  ssc.start();
  ssc.awaitTermination();
}
}
