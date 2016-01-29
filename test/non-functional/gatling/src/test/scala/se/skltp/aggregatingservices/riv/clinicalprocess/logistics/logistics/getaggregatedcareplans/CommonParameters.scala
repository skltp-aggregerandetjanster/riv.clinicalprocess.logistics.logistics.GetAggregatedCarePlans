package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans

trait CommonParameters {
  val serviceName:String     = "CarePlans"
  val urn:String             = "urn:riv:clinicalprocess:logistics:GetCarePlansResponder:2"
  val responseElement:String = "GetCarePlansResponse"
  val responseItem:String    = "carePlan"
  val responseItemUrn:String = "urn:riv:clinicalprocess:logistics:logistics:GetCarePlansResponder:2"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl")
                               } else {
                                   "http://33.33.33.33:8081/GetAggregatedCarePlans/service/v2"
                               }
}
