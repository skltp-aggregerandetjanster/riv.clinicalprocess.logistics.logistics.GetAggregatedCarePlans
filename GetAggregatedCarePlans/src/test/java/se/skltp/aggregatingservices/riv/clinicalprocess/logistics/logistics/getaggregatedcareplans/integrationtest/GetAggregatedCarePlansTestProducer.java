package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans.integrationtest;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.logistics.logistics.getcareplans.v2.rivtabp21.GetCarePlansResponderInterface;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansResponseType;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetCarePlansResponderService", portName = "GetCarePlansResponderPort", targetNamespace = "urn:riv:clinicalprocess:logistics:logistics:GetCarePlans:1:rivtabp21", name = "GetCarePlansInteraction")
public class GetAggregatedCarePlansTestProducer implements GetCarePlansResponderInterface {

    private static final Logger log = LoggerFactory.getLogger(GetAggregatedCarePlansTestProducer.class);

    private TestProducerDb testDb;
    public void setTestDb(TestProducerDb testDb) {
        this.testDb = testDb;
    }

    public GetCarePlansResponseType getCarePlans(String logicalAddress, GetCarePlansType request) {

        log.info("### Virtual service for GetCarePlans - about to call the source system with logical address: {} and patientId: {}", 
                logicalAddress, request.getPatientId().getId());

        GetCarePlansResponseType response = (GetCarePlansResponseType) testDb.processRequest(logicalAddress, request.getPatientId().getId());
        if (response == null) {
            // Return an empty response object instead of null if nothing is found
            response = new GetCarePlansResponseType();
        }

        log.info("### Virtual service got {} care plans in reply from the source system with logical address: {} and patientId: {}", new Object[] {
                response.getCarePlan().size(), logicalAddress, request.getPatientId().getId() });

        return response;
    }
}