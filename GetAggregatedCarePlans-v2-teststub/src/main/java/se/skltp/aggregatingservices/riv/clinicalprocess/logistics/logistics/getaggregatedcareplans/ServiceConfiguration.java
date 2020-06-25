package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import riv.clinicalprocess.logistics.logistics.getcareplans.v2.rivtabp21.GetCarePlansResponderInterface;
import riv.clinicalprocess.logistics.logistics.getcareplans.v2.rivtabp21.GetCarePlansResponderService;
import se.skltp.aggregatingservices.config.TestProducerConfiguration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="getaggregatedcareplans.v2.teststub")
public class ServiceConfiguration extends TestProducerConfiguration {

  public static final String SCHEMA_PATH = "classpath:/schemas/clinicalprocess_logistics_logistics_3.0/interactions/GetCarePlansInteraction/GetCarePlansInteraction_2.0_RIVTABP21.wsdl";

  public ServiceConfiguration() {
    setProducerAddress("http://localhost:8083/vp");
    setServiceClass(GetCarePlansResponderInterface.class.getName());
    setServiceNamespace("urn:riv:clinicalprocess:logistics:logistics:GetCarePlansResponder:2");
    setPortName(GetCarePlansResponderService.GetCarePlansResponderPort.toString());
    setWsdlPath(SCHEMA_PATH);
    setTestDataGeneratorClass(ServiceTestDataGenerator.class.getName());
    setServiceTimeout(27000);
  }

}
