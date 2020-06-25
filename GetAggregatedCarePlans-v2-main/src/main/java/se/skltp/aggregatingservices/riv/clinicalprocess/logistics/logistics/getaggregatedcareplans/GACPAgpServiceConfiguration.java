package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import riv.clinicalprocess.logistics.logistics.getcareplans.v2.rivtabp21.GetCarePlansResponderInterface;
import riv.clinicalprocess.logistics.logistics.getcareplans.v2.rivtabp21.GetCarePlansResponderService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "getaggregatedcareplans.v2")
public class GACPAgpServiceConfiguration extends se.skltp.aggregatingservices.configuration.AgpServiceConfiguration {

public static final String SCHEMA_PATH = "classpath:/schemas/clinicalprocess_logistics_logistics_3.0/interactions/GetCarePlansInteraction/GetCarePlansInteraction_2.0_RIVTABP21.wsdl";

  public GACPAgpServiceConfiguration() {

    setServiceName("GetAggregatedCarePlans-v2");
    setTargetNamespace("urn:riv:clinicalprocess:logistics:logistics:GetCarePlans:2:rivtabp21");

    // Set inbound defaults
    setInboundServiceURL("http://localhost:9019/GetAggregatedCarePlans/service/v2");
    setInboundServiceWsdl(SCHEMA_PATH);
    setInboundServiceClass(GetCarePlansResponderInterface.class.getName());
    setInboundPortName(GetCarePlansResponderService.GetCarePlansResponderPort.toString());

    // Set outbound defaults
    setOutboundServiceWsdl(SCHEMA_PATH);
    setOutboundServiceClass(GetCarePlansResponderInterface.class.getName());
    setOutboundPortName(GetCarePlansResponderService.GetCarePlansResponderPort.toString());

    // FindContent
    setEiServiceDomain("riv:clinicalprocess:logistics:logistics");
    setEiCategorization("cll-cp");

    // TAK
    setTakContract("urn:riv:clinicalprocess:logistics:logistics:GetCarePlansResponder:2");

    // Set service factory
    setServiceFactoryClass(GACPAgpServiceFactoryImpl.class.getName());
    }


}
