package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.logistics.logistics.getcareplans.v2.rivtabp21.GetCarePlansResponderInterface;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansResponseType;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import riv.clinicalprocess.logistics.logistics.v3.PersonIdType;
import se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans.GetAggregatedCarePlansMuleServer;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;

public class GetAggregatedCarePlansTestConsumer extends AbstractTestConsumer<GetCarePlansResponderInterface> {

    private static final Logger log = LoggerFactory.getLogger(GetAggregatedCarePlansTestConsumer.class);

    public static void main(String[] args) {
        String serviceAddress = GetAggregatedCarePlansMuleServer.getAddress("SERVICE_INBOUND_URL");
        String personnummer = TEST_RR_ID_ONE_HIT;

        GetAggregatedCarePlansTestConsumer consumer 
           = new GetAggregatedCarePlansTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID);
        Holder<GetCarePlansResponseType> responseHolder = new Holder<GetCarePlansResponseType>();
        Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();
        consumer.callService("logical-address", personnummer, processingStatusHolder, responseHolder);
        log.info("Returned {} care plans ", responseHolder.value.getCarePlan().size());
    }

    public GetAggregatedCarePlansTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId) {
        // Setup a web service proxy for communication using HTTPS with mutual authentication
        super(GetCarePlansResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId);
    }

    public void callService(String logicalAddress, String registeredResidentId, Holder<ProcessingStatusType> processingStatusHolder,
            Holder<GetCarePlansResponseType> responseHolder) {

        log.debug("Calling GetCarePlans-soap-service with registered resident id:{}", registeredResidentId);

        GetCarePlansType request = new GetCarePlansType();

        request.setPatientId(new PersonIdType());
        request.getPatientId().setId(registeredResidentId);

        GetCarePlansResponseType response = _service.getCarePlans(logicalAddress, request);
        responseHolder.value = response;

        processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
    }
}