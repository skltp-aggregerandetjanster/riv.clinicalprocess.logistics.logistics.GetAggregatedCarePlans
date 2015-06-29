package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.ThreadSafeSimpleDateFormat;

import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.riv.itintegration.engagementindex.v1.EngagementType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.RequestListFactory;

public class RequestListFactoryImpl implements RequestListFactory {

    private static final Logger log = LoggerFactory.getLogger(RequestListFactoryImpl.class);
    private static final ThreadSafeSimpleDateFormat df = new ThreadSafeSimpleDateFormat("yyyyMMddhhmmss");

    /**
     * Filtrera svarsposter från i EI (ei-engagement) baserat parametrar i GetCarePlans requestet (req). Följande villkor 
     * måste vara sanna för att en svarspost från EI skall tas med i svaret:
     * 
     * 1. req.fromDate <= ei-engagement.mostRecentContent <= req.toDate 
     * 2. req.careUnitId.size == 0 or req.careUnitId.contains(ei-engagement.logicalAddress)
     * 
     * Svarsposter från EI som passerat filtreringen grupperas på fältet sourceSystem samt postens fält logicalAddress (= PDL-enhet) 
     * samlas i listan careUnitId per varje sourceSystem
     * 
     * Ett anrop görs per funnet sourceSystem med följande värden i anropet:
     * 
     * 1. logicalAddress  = sourceSystem (systemadressering) 
     * 2. subjectOfCareId = original-request.subjectOfCareId 
     * 3. careUnitId      = listan av PDL-enheter som returnerats från EI för aktuellt source system) 
     * 4. fromDate        = original-request.fromDate 
     * 5. toDate          = original-request.toDate
     */
    public List<Object[]> createRequestList(QueryObject qo, FindContentResponseType findContentResponse) {

        GetCarePlansType originalGetCarePlansRequest = (GetCarePlansType) qo.getExtraArg();

        Date reqFrom = null;
        Date reqTo = null;
        if (originalGetCarePlansRequest.getDatePeriod() != null) {
            reqFrom = parseTs(originalGetCarePlansRequest.getDatePeriod().getStart());
            reqTo = parseTs(originalGetCarePlansRequest.getDatePeriod().getEnd());
        }

        FindContentResponseType eiResp = (FindContentResponseType) findContentResponse;
        List<EngagementType> inEngagements = eiResp.getEngagement();

        log.info("Got {} hits in the engagement index", inEngagements.size());

        Map<String, List<String>> sourceSystem_pdlUnitList_map = new HashMap<String, List<String>>();

        List<String> originalRequestCareUnitHSAIds = originalGetCarePlansRequest.getCareUnitHSAId();
        for (EngagementType inEng : inEngagements) {
            // Filter
            if (mostRecentContentIsBetween(reqFrom, reqTo, inEng.getMostRecentContent()) 
                    && 
                isPartOf(originalRequestCareUnitHSAIds, inEng.getLogicalAddress())) {
                // Add pdlUnit to source system
                log.debug("Add source system: {} for PDL unit: {}", inEng.getSourceSystem(), inEng.getLogicalAddress());
                addPdlUnitToSourceSystem(sourceSystem_pdlUnitList_map, inEng.getSourceSystem(), inEng.getLogicalAddress());
            }
        }

        // Prepare the result of the transformation as a list of request-payloads,
        // one payload for each unique logical-address (e.g. source system since we are using system addressing),
        // each payload built up as an object-array according to the JAX-WS signature for the method in the service interface
        List<Object[]> requestList = new ArrayList<Object[]>();

        for (Entry<String, List<String>> entry : sourceSystem_pdlUnitList_map.entrySet()) {

            String sourceSystem = entry.getKey();
            List<String> sourceSystemLogicalAddressList = entry.getValue();

            GetCarePlansType newGetCarePlansRequest = originalGetCarePlansRequest;
            newGetCarePlansRequest.setSourceSystemHSAId(null);
            newGetCarePlansRequest.getCareUnitHSAId().clear();
            newGetCarePlansRequest.getCareUnitHSAId().addAll(sourceSystemLogicalAddressList);

            Object[] reqArr = new Object[] { sourceSystem, newGetCarePlansRequest };

            log.info("Calling source system using logical address {} for subject of care id {}", 
                    sourceSystem, originalGetCarePlansRequest.getPatientId().getId());
            requestList.add(reqArr);
        }

        log.debug("Transformed payload: {}", requestList);
        return requestList;
    }

    Date parseTs(String ts) {
        try {
            if (ts == null || ts.length() == 0) {
                return null;
            } else {
                return df.parse(ts);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    boolean mostRecentContentIsBetween(Date from, Date to, String mostRecentContentTimestamp) {
        if (mostRecentContentTimestamp == null) {
            log.error("mostRecentContent - timestamp string is null");
            return true;
        }
        if (StringUtils.isBlank(mostRecentContentTimestamp)) {
            log.error("mostRecentContent - timestamp string is blank");
            return true;
        }
        log.debug("Is {} between {} and ", new Object[] { mostRecentContentTimestamp, from, to });
        try {
            Date ts = df.parse(mostRecentContentTimestamp);
            if (from != null && from.after(ts))
                return false;
            if (to != null && to.before(ts))
                return false;
            return true;
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse most recent content timestamp " + mostRecentContentTimestamp,e);
        }
    }

    boolean isPartOf(List<String> careUnitIdList, String careUnit) {
        log.debug("Check presence of {} in {}", careUnit, careUnitIdList);
        if (careUnitIdList == null || careUnitIdList.size() == 0)
            return true;
        return careUnitIdList.contains(careUnit);
    }

    void addPdlUnitToSourceSystem(Map<String, List<String>> sourceSystem_pdlUnitList_map, String sourceSystem, String pdlUnitId) {
        List<String> careUnitList = sourceSystem_pdlUnitList_map.get(sourceSystem);
        if (careUnitList == null) {
            careUnitList = new ArrayList<String>();
            sourceSystem_pdlUnitList_map.put(sourceSystem, careUnitList);
        }
        careUnitList.add(pdlUnitId);
    }
}