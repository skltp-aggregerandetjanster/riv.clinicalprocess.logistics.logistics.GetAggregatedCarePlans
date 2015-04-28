package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import scala.util.Random

object GetAggregatedCarePlansScenario {

  val headers = Map(
    "Accept-Encoding"                        -> "gzip,deflate",
    "Content-Type"                           -> "text/xml;charset=UTF-8",
    "SOAPAction"                             -> "urn:riv:clinicalprocess:logistics:logistics:GetCarePlansResponder:2:GetCarePlans",
    "x-vp-sender-id"                         -> "test",
    "x-rivta-original-serviceconsumer-hsaid" -> "test",
    "Keep-Alive"                             -> "115")

  val request = exec(
        http("GetAggregatedCarePlans ${patientid} - ${name}")
          .post("")
          .headers(headers)
          .body(ELFileBody("GetCarePlans.xml"))
          .check(status.is(session => session("status").as[String].toInt))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(substring("GetCarePlansResponse"))
          .check(xpath("//ns2:carePlan", List("ns2" -> "urn:riv:clinicalprocess:logistics:logistics:GetCarePlansResponder:2")).count.is(session => session("count").as[String].toInt))
      )
}
