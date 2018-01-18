/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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