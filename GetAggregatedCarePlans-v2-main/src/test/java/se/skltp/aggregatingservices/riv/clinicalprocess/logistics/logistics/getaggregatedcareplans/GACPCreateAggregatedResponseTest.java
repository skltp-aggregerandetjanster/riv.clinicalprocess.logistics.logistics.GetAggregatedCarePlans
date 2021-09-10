package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)

public class GACPCreateAggregatedResponseTest extends CreateAggregatedResponseTest {

  private static GACPAgpServiceConfiguration configuration = new GACPAgpServiceConfiguration();
  private static AgpServiceFactory<GetCarePlansResponseType> agpServiceFactory = new GACPAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GACPCreateAggregatedResponseTest() {
      super(testDataGenerator, agpServiceFactory, configuration);
  }

  @Override
  public int getResponseSize(Object response) {
        GetCarePlansResponseType responseType = (GetCarePlansResponseType)response;
    return responseType.getCarePlan().size();
  }
}