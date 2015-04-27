@REM Licensed to the soi-toolkit project under one or more
@REM contributor license agreements.  See the NOTICE file distributed with
@REM this work for additional information regarding copyright ownership.
@REM The soi-toolkit project licenses this file to You under the Apache License, Version 2.0
@REM (the "License"); you may not use this file except in compliance with
@REM the License.  You may obtain a copy of the License at
@REM
@REM     http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
	
@REM ---------------------------------------------------------------------------------
@REM Generates c# WCF service contracts (interface), client proxies and wcf config
@REM file for the WSDLs + XML Schemas of the service domain, using .Net WCF tool svcuti.exe
@REM ---------------------------------------------------------------------------------	
	
	CD ..
	
	SET SCHEMADIR=schemas
		
	SET X0=%SCHEMADIR%/td_clinicalprocess_logistics_logistics_3.0_RC3/core_components/clinicalprocess_logistics_logistics_3.0.xsd 	
	SET X1=%SCHEMADIR%/td_clinicalprocess_logistics_logistics_3.0_RC3/core_components/clinicalprocess_logistics_logistics_enum_3.0.xsd 	
	SET X2=%SCHEMADIR%/td_clinicalprocess_logistics_logistics_3.0_RC3/core_components/itintegration_registry_1.0.xsd 	
	SET X3=%SCHEMADIR%/td_clinicalprocess_logistics_logistics_3.0_RC3/interactions/GetCareContactsInteraction/GetCareContactsInteraction_3.0_RIVTABP21.wsdl 	
	SET X4=%SCHEMADIR%/td_clinicalprocess_logistics_logistics_3.0_RC3/interactions/GetCareContactsInteraction/GetCareContactsResponder_3.0.xsd 	
	SET X5=%SCHEMADIR%/td_clinicalprocess_logistics_logistics_3.0_RC3/interactions/GetCarePlansInteraction/GetCarePlansInteraction_2.0_RIVTABP21.wsdl 	
	SET X6=%SCHEMADIR%/td_clinicalprocess_logistics_logistics_3.0_RC3/interactions/GetCarePlansInteraction/GetCarePlansResponder_2.0.xsd 	
	SET X7=%SCHEMADIR%/td_clinicalprocess_logistics_logistics_3.0_RC3/interactions/GetCareServicesInteraction/GetCareServicesInteraction_2.0_RIVTABP21.wsdl 	
	SET X8=%SCHEMADIR%/td_clinicalprocess_logistics_logistics_3.0_RC3/interactions/GetCareServicesInteraction/GetCareServicesResponder_2.0.xsd 
	SET SCHEMAS=%X0% %X1% %X2% %X3% %X4% %X5% %X6% %X7% %X8%  
	SET OUTFILE=/out:wcf/generated-src/GetAggregatedCarePlansClient.cs
	SET APPCONFIG=/config:wcf/generated-src/app.config
	SET NAMESPACE=/namespace:*,GetAggregatedCarePlans.Schemas
	
	@REM ServiceModel Metadata Utility Tool
	SET SVCUTIL="svcutil.exe"
	%SVCUTIL% /language:cs %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%