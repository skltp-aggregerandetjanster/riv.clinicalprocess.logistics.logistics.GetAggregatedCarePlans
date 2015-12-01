package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans

import se.skltp.agp.testnonfunctional.TPHappyPathAbstract

/**
 * Just test Tolvan Tolvansson - no delays and no error latency
 */
class TPHappyPath extends TPHappyPathAbstract with CommonParameters {
  setUp(setUpAbstract(serviceName, urn, responseElement, responseItem, baseUrl))
}
