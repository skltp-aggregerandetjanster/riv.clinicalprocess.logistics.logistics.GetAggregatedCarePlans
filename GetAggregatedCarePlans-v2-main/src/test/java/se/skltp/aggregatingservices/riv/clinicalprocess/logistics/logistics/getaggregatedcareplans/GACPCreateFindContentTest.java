package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateFindContentTest;
import se.skltp.aggregatingservices.data.TestDataGenerator;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)

public class GACPCreateFindContentTest extends CreateFindContentTest {

  private static GACPAgpServiceConfiguration configuration = new GACPAgpServiceConfiguration();
  private static AgpServiceFactory<GetCarePlansResponseType> agpServiceFactory = new GACPAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GACPCreateFindContentTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }

  @BeforeAll
  public static void before() {
    configuration = new GACPAgpServiceConfiguration();
    agpServiceFactory = new GACPAgpServiceFactoryImpl();
    agpServiceFactory.setAgpServiceConfiguration(configuration);
  }


}
