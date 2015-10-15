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
