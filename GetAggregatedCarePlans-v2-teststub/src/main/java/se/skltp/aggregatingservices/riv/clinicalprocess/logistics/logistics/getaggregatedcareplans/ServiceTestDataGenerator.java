package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import lombok.extern.log4j.Log4j2;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansResponseType;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import riv.clinicalprocess.logistics.logistics.v3.CarePlanType;
import riv.clinicalprocess.logistics.logistics.v3.PatientSummaryHeaderType;
import riv.clinicalprocess.logistics.logistics.v3.PersonIdType;

import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Service;
import se.skltp.aggregatingservices.data.TestDataGenerator;

@Log4j2
@Service
public class ServiceTestDataGenerator extends TestDataGenerator {

	@Override
	public String getPatientId(MessageContentsList messageContentsList) {
		GetCarePlansType request = (GetCarePlansType) messageContentsList.get(1);
		String patientId = request.getPatientId().getId();
		return patientId;
	}

	@Override
	public Object createResponse(Object... responseItems) {
		log.info("Creating a response with {} items", responseItems.length);
		GetCarePlansResponseType response = new GetCarePlansResponseType();
		for (int i = 0; i < responseItems.length; i++) {
            response.getCarePlan().add((CarePlanType) responseItems[i]);
		}

		log.info("response.toString:" + response.toString());

		return response;
	}

	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
		log.debug("Created ResponseItem for logical-address {}, registeredResidentId {} and businessObjectId {}",
				new Object[]{logicalAddress, registeredResidentId, businessObjectId});

        CarePlanType response = new CarePlanType();
        response.setCarePlanHeader(new PatientSummaryHeaderType());
        response.getCarePlanHeader().setPatientId(new PersonIdType());
        response.getCarePlanHeader().getPatientId().setId(registeredResidentId);
        response.getCarePlanHeader().getPatientId().setType("1.2.752.129.2.1.3.1");
        response.getCarePlanHeader().setSourceSystemHSAId(logicalAddress);
        response.getCarePlanHeader().setDocumentId(businessObjectId);

        return response;
	}

	public Object createRequest(String patientId, String sourceSystemHSAId){
        GetCarePlansType request = new GetCarePlansType();

        request.setPatientId(new PersonIdType());
        request.getPatientId().setId(patientId);
        request.setSourceSystemHSAId(sourceSystemHSAId);

		return request;
	}
}
