package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.Ignore;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.ObjectFactory;
import riv.clinicalprocess.logistics.logistics.v3.DatePeriodType;
import riv.clinicalprocess.logistics.logistics.v3.PersonIdType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.QueryObjectFactory;

public class QueryObjectFactoryTest {

    private static final JaxbUtil ju = new JaxbUtil(GetCarePlansType.class);

    private QueryObjectFactory objectUnderTest = new QueryObjectFactoryImpl();
    private ObjectFactory carePlansObjectFactory = new ObjectFactory();

    @Test
    @Ignore
    public void testQueryObjectFactory() {
        Node node = null;
        objectUnderTest.createQueryObject(node);
        assertEquals("expected", "actual");
    }

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
}
