package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.logistics.logistics.enums.v3.ResultCodeEnum;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansResponseType;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.ObjectFactory;
import riv.clinicalprocess.logistics.logistics.v3.ResultType;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;

public class ResponseListFactoryImpl implements ResponseListFactory {

    private static final Logger log = LoggerFactory.getLogger(ResponseListFactoryImpl.class);
    private static final JaxbUtil jaxbUtil = new JaxbUtil(GetCarePlansResponseType.class, ProcessingStatusType.class);
    private static final ObjectFactory OF = new ObjectFactory();

    @Override
    public String getXmlFromAggregatedResponse(QueryObject queryObject, List<Object> aggregatedResponseList) {
        GetCarePlansResponseType aggregatedResponse = new GetCarePlansResponseType();

        for (Object object : aggregatedResponseList) {
            GetCarePlansResponseType response = (GetCarePlansResponseType) object;
            aggregatedResponse.getCarePlan().addAll(response.getCarePlan());
        }

        aggregatedResponse.setResult(new ResultType());
        aggregatedResponse.getResult().setResultCode(ResultCodeEnum.INFO);
        aggregatedResponse.getResult().setLogId("NA");
        
        String subjectOfCareId = queryObject.getFindContent().getRegisteredResidentIdentification();
        log.info("Returning {} aggregated care plans for subject of care id {}", aggregatedResponse.getCarePlan().size(), subjectOfCareId);

        // Since the class GetCarePlansResponseType doesn't have an @XmlRootElement annotation we need to use the ObjectFactory to add it.
        return jaxbUtil.marshal(OF.createGetCarePlansResponse(aggregatedResponse));
    }
}