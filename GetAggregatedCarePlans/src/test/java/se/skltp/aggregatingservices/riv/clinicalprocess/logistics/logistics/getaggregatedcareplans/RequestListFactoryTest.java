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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.riv.itintegration.engagementindex.v1.EngagementType;
import se.skltp.agp.service.api.QueryObject;

public class RequestListFactoryTest {
	
	private RequestListFactoryImpl objectUnderTest = new RequestListFactoryImpl();

    @Test
    public void testNoEngagements() {
        QueryObject eiQueryObject = new QueryObject(new FindContentType(), new GetCarePlansType());
        assertTrue(eiQueryObject.getExtraArg().getClass() == GetCarePlansType.class);
        FindContentResponseType findContentResponse = new FindContentResponseType();
        List<?> l = objectUnderTest.createRequestList(eiQueryObject, findContentResponse);
        assertEquals(0,l.size());
    }
    
    @Test
    public void testMixedEngagements() {
        QueryObject eiQueryObject = new QueryObject(new FindContentType(), new GetCarePlansType());
        assertTrue(eiQueryObject.getExtraArg().getClass() == GetCarePlansType.class);
        FindContentResponseType findContentResponse = new FindContentResponseType();
        
        EngagementType e = new EngagementType();
        e.setSourceSystem("AAA");
        findContentResponse.getEngagement().add(e);
        
        e = new EngagementType();
        e.setSourceSystem("BBB");
        findContentResponse.getEngagement().add(e);
        
        e = new EngagementType();
        e.setSourceSystem("AAA");
        findContentResponse.getEngagement().add(e);
        
        e = new EngagementType();
        e.setSourceSystem("AAA");
        findContentResponse.getEngagement().add(e);
        
        e = new EngagementType();
        e.setSourceSystem("AAA");
        findContentResponse.getEngagement().add(e);
        
        e = new EngagementType();
        e.setSourceSystem("BBB");
        findContentResponse.getEngagement().add(e);
        
        e = new EngagementType();
        e.setSourceSystem("CCC");
        findContentResponse.getEngagement().add(e);
        
        List<?> l = objectUnderTest.createRequestList(eiQueryObject, findContentResponse);
        assertEquals(3,l.size());
    }
	
}
