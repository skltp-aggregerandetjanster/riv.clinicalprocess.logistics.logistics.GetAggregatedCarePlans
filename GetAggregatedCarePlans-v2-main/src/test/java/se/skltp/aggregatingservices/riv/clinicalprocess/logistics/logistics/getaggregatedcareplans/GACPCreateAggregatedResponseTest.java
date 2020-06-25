package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;
import se.skltp.aggregatingservices.data.TestDataGenerator;


@RunWith(SpringJUnit4ClassRunner.class)
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