package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.ObjectFactory;
import riv.clinicalprocess.logistics.logistics.v3.DatePeriodType;
import riv.clinicalprocess.logistics.logistics.v3.PersonIdType;
import se.skltp.agp.service.api.QueryObject;

public class QueryObjectFactoryTest {

    private static final JaxbUtil ju = new JaxbUtil(GetCarePlansType.class);

    private QueryObjectFactoryImpl objectUnderTest        = new QueryObjectFactoryImpl();
    private ObjectFactory          carePlansObjectFactory = new ObjectFactory();
    
    String getCarePlansString =
            
            "<urn1:GetCarePlans                                                                      " + 
            " xmlns:urn1=\"urn:riv:clinicalprocess:logistics:logistics:GetCarePlansResponder:2\"     " +
            " xmlns:urn2=\"urn:riv:clinicalprocess:logistics:logistics:3\"                           " +
            ">                                                                                       " + 
            "  <urn1:patientId>                                                                      " +
            "    <urn2:id>121212121212</urn2:id>                                                     " +
            "    <urn2:type>1.2.752.129.2.1.3.1</urn2:type>                                          " +
            "  </urn1:patientId>                                                                     " + 
            "  <urn1:datePeriod>                                                                     " +
            "    <urn2:start>20100401063100</urn2:start>                                             " +
            "    <urn2:end>20200331235959</urn2:end>                                                 " +
            "  </urn1:datePeriod>                                                                    " + 
            "</urn1:GetCarePlans>                                                                    ";    

    @Test
    public void testFindContentPatientId() {

        GetCarePlansType getCarePlans = new GetCarePlansType();

        getCarePlans.setDatePeriod(new DatePeriodType());
        getCarePlans.getDatePeriod().setStart("123");
        getCarePlans.getDatePeriod().setEnd("987");
        getCarePlans.setPatientId(new PersonIdType());
        getCarePlans.getPatientId().setId("111");
        getCarePlans.getPatientId().setType("ttt");
        getCarePlans.setSourceSystemHSAId("HSA-ID-987");

        String xmlGetCarePlansType = ju.marshal(carePlansObjectFactory.createGetCarePlans(getCarePlans));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            Element node = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xmlGetCarePlansType.getBytes())).getDocumentElement();
            QueryObject qo = objectUnderTest.createQueryObject(node);
            assertEquals("111", qo.getFindContent().getRegisteredResidentIdentification());
        } catch (SAXException e) {
            fail(e.getLocalizedMessage());
        } catch (IOException e) {
            fail(e.getLocalizedMessage());
        } catch (ParserConfigurationException e) {
            fail(e.getLocalizedMessage());
        }
    }
    
    
    @Test
    public void testFindContentCategorization() {
        
        objectUnderTest.setEiCategorization("cll-cp");
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        
        try {
            Element node = factory
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(getCarePlansString.getBytes()))
                    .getDocumentElement();
            
            QueryObject qo = objectUnderTest.createQueryObject(node);
            assertEquals("cll-cp",qo.getFindContent().getCategorization());
        } catch (SAXException e) {
            fail(e.getLocalizedMessage());
        } catch (IOException e) {
            fail(e.getLocalizedMessage());
        } catch (ParserConfigurationException e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void testFindContentEiServiceDomain() {
        
        objectUnderTest.setEiServiceDomain("clinicalprocess:logistics:logistics");
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        
        try {
            Element node = factory
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(getCarePlansString.getBytes()))
                    .getDocumentElement();
            
            QueryObject qo = objectUnderTest.createQueryObject(node);
            assertEquals("clinicalprocess:logistics:logistics",qo.getFindContent().getServiceDomain());
        } catch (SAXException e) {
            fail(e.getLocalizedMessage());
        } catch (IOException e) {
            fail(e.getLocalizedMessage());
        } catch (ParserConfigurationException e) {
            fail(e.getLocalizedMessage());
        }
    }
}
