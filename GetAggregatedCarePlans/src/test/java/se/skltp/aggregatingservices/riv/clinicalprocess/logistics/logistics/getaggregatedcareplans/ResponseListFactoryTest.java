package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.Ignore;

import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;


public class ResponseListFactoryTest {

	private ResponseListFactory testObject = new ResponseListFactoryImpl();
	
    // TODO - either implement or delete
	
	@Test
	@Ignore
	public void testQueryObjectFactory() {
		
		QueryObject queryObject = null;
		List<Object> aggregatedResponseList = null;
		testObject.getXmlFromAggregatedResponse(queryObject, aggregatedResponseList);
		assertEquals("expected", "actual");
	}
}
