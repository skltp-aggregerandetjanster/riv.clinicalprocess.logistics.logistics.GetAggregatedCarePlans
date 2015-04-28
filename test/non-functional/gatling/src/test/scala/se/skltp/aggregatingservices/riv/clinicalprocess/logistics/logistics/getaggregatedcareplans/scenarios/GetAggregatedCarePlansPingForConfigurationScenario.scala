package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck

object GetAggregatedCarePlansPingForConfigurationScenario {

  val headers = Map(
    "Accept-Encoding" -> "gzip,deflate",
    "Content-Type"    -> "text/xml;charset=UTF-8",
    "SOAPAction"      -> "urn:riv:itintegration:monitoring:PingForConfigurationResponder:1:PingForConfiguration",
    "Keep-Alive"      -> "115")

  val request = exec(
        http("GetAggregatedCarePlansPingForConfiguration")
          .post("")
          .headers(headers)
          .body(RawFileBody("GetCarePlansPingForConfiguration.xml"))
          .check(status.is(200))
          .check(substring("Applikation"))
          .check(substring("GetAggregatedCarePlans"))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(regex("GetAggregatedCarePlans").exists)
      )
}
