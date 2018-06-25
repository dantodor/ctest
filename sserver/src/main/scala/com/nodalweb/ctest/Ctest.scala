package com.nodalweb.ctest

import java.util.Properties

import org.slf4j.LoggerFactory
import com.ericsson.otp.erlang._


object Ctest extends App {

  def logger = LoggerFactory.getLogger(this.getClass)



  //logger.info("vikas")
  val toAnalyze ="blah"

  //println(SentimentAnalyzer.mainSentiment(toAnalyze))

  logger.info(args.toList.mkString(","))

  val properties = new Properties()

  // load startup arguments into properties configuration
  properties.put("node", if ((args.length >= 1)) {
    args(0)
  }
  else {
    "loco@macdan"
  })
  properties.put("self", if ((args.length >= 2)) {
    args(1)
  }
  else {
    "__ctest__loco@macdan"
  })
  properties.put("cookie", if ((args.length >= 3)) {
    args(2)
  }
  else {
    "secret"
  })
  properties.put("registered_proc_name", if ((args.length >= 4)) {
    args(3)
  }
  else {
    "ctest_java_server"
  })

//  val node = GetOtpNode(properties.getProperty("erlang.self"),properties.getProperty("erlang.cookie"))
//  val mbox = node.createMbox(properties.getProperty("erlang.registered_proc_name"))

  setupMBox()

//  def GetOtpNode(self:String, cookie: String) = {
//
//      new OtpNode(self, cookie)
//
//  }

    def setupMBox() {
      val myOtpNode = new OtpNode(properties.getProperty("self"))
      myOtpNode.setCookie(properties.getProperty("cookie"))

      val myOtpMbox = myOtpNode.createMbox(properties.getProperty( "registered_proc_name"))

      logger.info("Here")
      println("READY")

      while (true) {
        val tuple = myOtpMbox.receive().asInstanceOf[OtpErlangTuple]
        logger.info("Received : "+tuple.toString)
        val lastPid = (tuple.elementAt(1).asInstanceOf[OtpErlangTuple]).elementAt(0).asInstanceOf[OtpErlangPid]
        val lastPid2 = (tuple.elementAt(1).asInstanceOf[OtpErlangTuple]).elementAt(1)
        val di = tuple.elementAt(2).asInstanceOf[OtpErlangTuple]
        val dispatch = di.elementAt(0).asInstanceOf[OtpErlangAtom]

        logger.info("Got dispatch "+dispatch.toString)

        if (dispatch.toString().equals("pid")) {
          logger.info("Got pid request from "+lastPid.toString)
          //val message = tuple.elementAt(2).asInstanceOf[OtpErlangBinary];
          val answer = new OtpErlangTuple(List(new OtpErlangAtom("ok"),myOtpMbox.self()).toArray)
          val answer2 = new OtpErlangTuple(List(lastPid2,answer).toArray)
          myOtpMbox.send(lastPid,answer2)
          //println(new String(message.binaryValue()))

        } else if (dispatch.toString().equals("analyze")) {
          val message = new String(di.elementAt(1).asInstanceOf[OtpErlangBinary].binaryValue())
          //println("Got it")
          val sendBack = new OtpErlangString(
            SentimentAnalyzer.toStringSentiment(SentimentAnalyzer.mainSentiment(message)))
          val answer = new OtpErlangTuple(List(lastPid2,sendBack).toArray)
          myOtpMbox.send(lastPid,new OtpErlangTuple(List(lastPid2,answer).toArray) )

        }
      }
    }


}
