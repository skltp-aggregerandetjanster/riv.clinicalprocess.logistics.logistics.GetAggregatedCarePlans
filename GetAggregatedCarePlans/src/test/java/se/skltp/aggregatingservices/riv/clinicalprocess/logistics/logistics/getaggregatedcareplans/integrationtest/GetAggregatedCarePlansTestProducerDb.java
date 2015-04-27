package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansResponseType;
import riv.clinicalprocess.logistics.logistics.v3.CarePlanType;
import riv.clinicalprocess.logistics.logistics.v3.PatientSummaryHeaderType;
import riv.clinicalprocess.logistics.logistics.v3.PersonIdType;
import se.skltp.agp.test.producer.TestProducerDb;

public class GetAggregatedCarePlansTestProducerDb extends TestProducerDb {

    private static final Logger log = LoggerFactory.getLogger(GetAggregatedCarePlansTestProducerDb.class);

    @Override
    public Object createResponse(Object... responseItems) {
        log.debug("Creates a response with {} items", responseItems);
        GetCarePlansResponseType response = new GetCarePlansResponseType();
        for (int i = 0; i < responseItems.length; i++) {
            response.getCarePlan().add((CarePlanType) responseItems[i]);
        }
        return response;
    }

    @Override
    public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {

        log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}", 
                new Object[] { logicalAddress, registeredResidentId, businessObjectId });
        CarePlanType response = new CarePlanType();
        response.setCarePlanHeader(new PatientSummaryHeaderType());
        response.getCarePlanHeader().setPatientId(new PersonIdType());
        response.getCarePlanHeader().getPatientId().setId(registeredResidentId);
        response.getCarePlanHeader().getPatientId().setType("1.2.752.129.2.1.3.1");
        response.getCarePlanHeader().setSourceSystemHSAId(logicalAddress);
        response.getCarePlanHeader().setDocumentId(businessObjectId);

        return response;
    }
}