package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Node;

import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.QueryObjectFactory;

public class QueryObjectFactoryImpl implements QueryObjectFactory {

    private static final Logger log = LoggerFactory.getLogger(QueryObjectFactoryImpl.class);
    private static final JaxbUtil ju = new JaxbUtil(GetCarePlansType.class);

    private String eiServiceDomain;

    public void setEiServiceDomain(String eiServiceDomain) {
        this.eiServiceDomain = eiServiceDomain;
    }

    private String eiCategorization;

    public void setEiCategorization(String eiCategorization) {
        this.eiCategorization = eiCategorization;
    }

    /**
     * Transformerar GetCarePlans request till EI FindContent request enligt:
     * 
     * 1. subjectOfCareId --> registeredResidentIdentification 
     * 2. "riv:clinicalprocess:logistics:logistics" --> serviceDomain
     */
    public QueryObject createQueryObject(Node node) {

        GetCarePlansType request = (GetCarePlansType) ju.unmarshal(node);
        log.debug("Transformed payload for pid: {}", request.getPatientId().getId());

        FindContentType fc = new FindContentType();
        fc.setRegisteredResidentIdentification(request.getPatientId().getId());
        fc.setServiceDomain(eiServiceDomain);
        fc.setCategorization(eiCategorization);
        QueryObject qo = new QueryObject(fc, request);
        return qo;
    }
}
