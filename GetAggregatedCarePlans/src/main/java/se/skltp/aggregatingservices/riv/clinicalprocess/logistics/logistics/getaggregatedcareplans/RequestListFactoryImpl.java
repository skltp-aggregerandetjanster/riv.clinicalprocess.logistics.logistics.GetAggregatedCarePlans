package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.riv.itintegration.engagementindex.v1.EngagementType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.RequestListFactory;

public class RequestListFactoryImpl implements RequestListFactory {

    private static final Logger log = LoggerFactory.getLogger(RequestListFactoryImpl.class);

    /**
     * Engagement index has responded with a list of engagements.
     * Retrieve source system hsa id from the engagement and prepare a list of source systems together with the original request.
     * This list will be split by mule and sent to each source system.
     */
    public List<Object[]> createRequestList(QueryObject qo, FindContentResponseType src) {
        
        List<Object[]> requestsToBeSentToSourceSystems = new ArrayList<Object[]>();

        FindContentResponseType findContentResponse = (FindContentResponseType) src;
        List<EngagementType> engagements = findContentResponse.getEngagement();
        log.debug("Got {} hits in the engagement index", engagements.size());
        if (!engagements.isEmpty()) {
            Set<String> sourceSystems = new HashSet<String>(); // set of unique source system hsa ids
            
            // remove duplicate sourceSystems
            for (EngagementType engagement : engagements) {
                sourceSystems.add(engagement.getSourceSystem());
            }
    
            log.debug("Preparing to call {} different source systems", sourceSystems.size());
            GetCarePlansType request = (GetCarePlansType) qo.getExtraArg(); // consumer's original request
    
            for (String sourceSystem : sourceSystems) {
                log.info("Preparing to call source system {} for subject of care id {}", sourceSystem, request.getPatientId() == null ? null : request.getPatientId().getId());
                // the original request will sent unchanged to each sourceSystem
                Object[] reqArr = new Object[] { sourceSystem, request };
                requestsToBeSentToSourceSystems.add(reqArr);
            }
    
            log.debug("Transformed payload: {}", requestsToBeSentToSourceSystems);
        }
        return requestsToBeSentToSourceSystems;
    }
    
}