package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import riv.clinicalprocess.logistics.logistics.enums.v3.ResultCodeEnum;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansResponseType;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import riv.clinicalprocess.logistics.logistics.v3.ResultType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;

@Log4j2
public class GACPAgpServiceFactoryImpl extends
    AgServiceFactoryBase<GetCarePlansType, GetCarePlansResponseType>{

@Override
public String getPatientId(GetCarePlansType queryObject){
    return queryObject.getPatientId().getId();
    }

@Override
public String getSourceSystemHsaId(GetCarePlansType queryObject){
    return queryObject.getSourceSystemHSAId();
    }

@Override
public GetCarePlansResponseType aggregateResponse(List<GetCarePlansResponseType> aggregatedResponseList ){

    GetCarePlansResponseType aggregatedResponse=new GetCarePlansResponseType();

    for (GetCarePlansResponseType object : aggregatedResponseList) {
        GetCarePlansResponseType response = object;
        aggregatedResponse.getCarePlan().addAll(response.getCarePlan());
    }

    aggregatedResponse.setResult(new ResultType());
    aggregatedResponse.getResult().setResultCode(ResultCodeEnum.INFO);
    aggregatedResponse.getResult().setLogId("NA");

    return aggregatedResponse;
    }
}

