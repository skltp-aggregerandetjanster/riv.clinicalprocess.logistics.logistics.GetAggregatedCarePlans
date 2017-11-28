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
package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.logistics.logistics.getcareplans.v2.rivtabp21.GetCarePlansResponderInterface;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansResponseType;
import riv.clinicalprocess.logistics.logistics.getcareplansresponder.v2.GetCarePlansType;
import riv.clinicalprocess.logistics.logistics.v3.PersonIdType;
import se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareplans.GetAggregatedCarePlansMuleServer;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;

public class GetAggregatedCarePlansTestConsumer extends AbstractTestConsumer<GetCarePlansResponderInterface> {

    private static final Logger log = LoggerFactory.getLogger(GetAggregatedCarePlansTestConsumer.class);

    public static void main(String[] args) {
        String serviceAddress = GetAggregatedCarePlansMuleServer.getAddress("SERVICE_INBOUND_URL");
        String personnummer = TEST_RR_ID_ONE_HIT;

        GetAggregatedCarePlansTestConsumer consumer 
           = new GetAggregatedCarePlansTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID, SAMPLE_CORRELATION_ID);
        Holder<GetCarePlansResponseType> responseHolder = new Holder<GetCarePlansResponseType>();
        Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();
        consumer.callService("logical-address", personnummer, processingStatusHolder, responseHolder);
        log.info("Returned {} care plans ", responseHolder.value.getCarePlan().size());
    }

    public GetAggregatedCarePlansTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId, String correlationId) {
        // Setup a web service proxy for communication using HTTPS with mutual authentication
        super(GetCarePlansResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId, correlationId);
    }

    public void callService(String logicalAddress, String registeredResidentId, Holder<ProcessingStatusType> processingStatusHolder,
            Holder<GetCarePlansResponseType> responseHolder) {

        log.debug("Calling GetCarePlans-soap-service with registered resident id:{}", registeredResidentId);

        GetCarePlansType request = new GetCarePlansType();

        request.setPatientId(new PersonIdType());
        request.getPatientId().setId(registeredResidentId);

        GetCarePlansResponseType response = _service.getCarePlans(logicalAddress, request);
        responseHolder.value = response;

        processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
    }
}