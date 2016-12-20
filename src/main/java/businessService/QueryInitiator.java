package businessService;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import configuration.Settings;
import dao.DMLQuery;
import input.CSVReader;
import input.FolderOperations;
import input.XMLReader;
import javafx.util.Pair;
import util.LogGenerator;
import util.LogInformationRecord;
import util.LogRecordQuery;

public class QueryInitiator {

	public Pair<String,String> queryInitiate()
	{
		//Get object of setting class
		Settings configuration = Settings.getInstance();
		
		//Get mapping class object
		CSVReader csvR = new CSVReader();
		
		//Get mapping for communication file
		
		//Delete
		//System.out.println(configuration.getCOMMUNICATION_Mapping());
		HashMap<String, Pair> Communication_mapper = csvR.readMapping(configuration.getCOMMUNICATION_Mapping());

		//Get mapping for customer file
		HashMap<String, Pair> Customer_mapper = csvR.readMapping(configuration.getCUSTOMER_Mapping());

		//Get mapping for customer file
		HashMap<String, Pair> ImageScore_mapper = csvR.readMapping(configuration.getIMAGE_SCORE_Mapping());

		//Get mapping for customer sales id
		HashMap<String, Pair> CustomerSalesId_mapper = csvR.readMapping(configuration.getCUSTOMER_SALESID_Mapping());

		//Get mapping for customer ship to
		HashMap<String, Pair> CustomerShipTo_mapper = csvR.readMapping(configuration.getCUSTOMER_SHIPTO_Mapping());
		
		//Get mapping for customer ship to
		HashMap<String, Pair> latestOrder_mapper = csvR.readMapping(configuration.getLATEST_ORDER_Mapping());
		
		//Get mapping for customer ship to
		HashMap<String, Pair> product_mapper = csvR.readMapping(configuration.getPRODUCT_Mapping());

		//Get mapping for customer ship to
		HashMap<String, Pair> salesArea_mapper = csvR.readMapping(configuration.getSALES_AREA_Mapping());

		//Get mapping for customer ship to
		HashMap<String, Pair> salesPerson_mapper = csvR.readMapping(configuration.getSALES_PERSON_Mapping());

		//Get mapping for customer ship to
		HashMap<String, Pair> BIBO_mapper = csvR.readMapping(configuration.getBIBO_Mapping());

		FolderOperations fo= new FolderOperations();
		XMLReader xml = new XMLReader();
		
		//Read all files
		File[] allFiles = fo.getAllFiles(configuration.getSAPDataFileFolder());
		
		//Log record for each type of files
		List<LogInformationRecord> logRecordsCommunication = new ArrayList();
		List<LogInformationRecord> logRecordsCustomer = new ArrayList();
		List<LogInformationRecord> logRecordsCustomerSalesID = new ArrayList();
		List<LogInformationRecord> logRecordsShipTo = new ArrayList();
		List<LogInformationRecord> logRecordsLatestOrder = new ArrayList();
		List<LogInformationRecord> logRecordsProduct = new ArrayList();
		List<LogInformationRecord> logRecordsSalesArea = new ArrayList();
		List<LogInformationRecord> logRecordsImageScore = new ArrayList();
		List<LogInformationRecord> logRecordsBIBO = new ArrayList();
		
		
		for(File ff:allFiles)
		{
			//Log records for each file operation
	
			//Get the type of file for processing
			
			if(!FilenameUtils.getExtension(ff.getName()).equals("xml"))
				continue;
		
			String fileName = FilenameUtils.removeExtension(ff.getName());
			String fileNameExtensionRemove = fileName;
			int index = fileName.lastIndexOf("_");
			fileName = fileName.substring(0, index);
			
			
			//Processing file according to mapping
			switch(fileName)
			{
			
			//Each file has different structure. Need to handle separately
			//Communication case
			
			case "COMMUNICATION":
				//Read all the pairs with communication mapper
				Pair<String,String> communicationPairs[][] = xml.readSAP(ff,Communication_mapper);
				
				//Iterate over each recordPair
				for(Pair<String,String> recordPair[]:communicationPairs)
				{
					//Extract status
					String status = getFieldFromPair(recordPair, "Status");
					switch(status)
					{
					case "C":
					case "U":
						//Extract information as logInformationRecord
						LogInformationRecord lir = new LogInformationRecord();
						lir = createRecord(recordPair, "Account", lir,Communication_mapper,configuration.getCOMMUNICATION_Mapping());
						lir.setFILENAME(fileNameExtensionRemove);
						lir.setFLAG(status);
						lir.setRECORD_ID(getFieldFromPair(recordPair, "Customer"));
						
						//Add in the master list
						logRecordsCommunication.add(lir);
					break;
					}
				}
				break;
				
			case "CUSTOMER":
				//Read all the pairs with communication mapper
				Pair<String,String> customerPairs[][] = xml.readSAP(ff,Customer_mapper);
				
				//Iterate over each recordPair
				for(Pair<String,String> recordPair[]:customerPairs)
				{
					//Extract status
					String status = getFieldFromPair(recordPair, "Status");
					LogInformationRecord lirBlock = new LogInformationRecord();
					String fieldName = "";
					String fieldValue ="";
					String searchField = "";
					
					switch(status)
					{
					case "C":
					case "U":
						//Extract information as logInformationRecord
						LogInformationRecord lir = new LogInformationRecord();
						lir = createRecord(recordPair, "Account", lir,Customer_mapper,configuration.getCUSTOMER_Mapping());
						lir.setFILENAME(fileNameExtensionRemove);
						lir.setFLAG(status);
						lir.setRECORD_ID(getFieldFromPair(recordPair, "Customer"));
						
						//Add in the master list
						logRecordsCustomer.add(lir);
					break;
					case "B":
						//Extract information as logInformationRecord
						lirBlock = updateField("CUSTOMER", lirBlock, "B", fieldName, fieldValue, searchField);
						lirBlock.setFILENAME(fileNameExtensionRemove);
						lirBlock.setFLAG(status);
						lirBlock.setRECORD_ID(getFieldFromPair(recordPair, "Customer"));
						
						//Add in the master list
						logRecordsCustomer.add(lirBlock);
						
						break;
					case "A":
						//Extract information as logInformationRecord
						lirBlock = updateField("CUSTOMER", lirBlock, "B", fieldName, fieldValue, searchField);
						lirBlock.setFILENAME(fileNameExtensionRemove);
						lirBlock.setFLAG(status);
						lirBlock.setRECORD_ID(getFieldFromPair(recordPair, "Customer"));
						
						//Add in the master list
						logRecordsCustomer.add(lirBlock);
	
						break;
					}
				}

				break;
			
			case "IMAGE_SCORE":
				//Read all the pairs with communication mapper
				Pair<String,String> imageScorePairs[][] = xml.readSAP(ff,ImageScore_mapper);
				
				//Iterate over each recordPair
				for(Pair<String,String> recordPair[]:imageScorePairs)
				{
					//Extract status
					String status = getFieldFromPair(recordPair, "Status");
					switch(status)
					{
					case "C":
					case "U":
						//Extract information as logInformationRecord
						LogInformationRecord lir = new LogInformationRecord();
						lir = createRecord(recordPair, "Account", lir,ImageScore_mapper,configuration.getIMAGE_SCORE_Mapping());
						lir.setFILENAME(fileNameExtensionRemove);
						lir.setFLAG(status);
						lir.setRECORD_ID(getFieldFromPair(recordPair, "Customer"));
						
						//Add in the master list
						logRecordsImageScore.add(lir);
					break;
					
					}
				}

				
				break;
				
			case "CUSTOMER_SHIPTO":
				//Read all the pairs with communication mapper
				Pair<String,String> customerShipToPairs[][] = xml.readSAP(ff,CustomerShipTo_mapper);
				
				//Iterate over each recordPair
				for(Pair<String,String> recordPair[]:customerShipToPairs)
				{
					//Extract status
					String status = getFieldFromPair(recordPair, "Status");
					switch(status)
					{
					case "C":
					case "U":
						//Extract information as logInformationRecord
						LogInformationRecord lir = new LogInformationRecord();
						lir = createRecord(recordPair, "ShipTo", lir,CustomerShipTo_mapper,configuration.getCUSTOMER_SHIPTO_Mapping());
						lir.setFILENAME(fileNameExtensionRemove);
						lir.setFLAG(status);
						lir.setRECORD_ID(getFieldFromPair(recordPair, "Shipto"));
						
						//Add in the master list
						logRecordsShipTo.add(lir);
					break;
					}
				}

	
				break;
			
			case "CUSTOMER_SALESID":
				break;
				
			case "LATEST_ORDER":
				break;
			
			case "PRODUCT":
				break;
				
			case "SALES_AREA":
				break;
				
			case "SALES_PERSON":
				break;
				
			case "BIBO_XML":
				//Read all the pairs with communication mapper
				Pair<String,String> biboPairs[][] = xml.readSAP(ff,BIBO_mapper);
				
				//Iterate over each recordPair
				for(Pair<String,String> recordPair[]:biboPairs)
				{
					//Extract information as logInformationRecord
					LogInformationRecord lir = new LogInformationRecord();
					lir = createRecord(recordPair, "Account", lir,BIBO_mapper,configuration.getBIBO_Mapping());
					lir.setFILENAME(fileNameExtensionRemove);
					lir.setFLAG("");
					//lir.setRECORD_ID(getFieldFromPair(recordPair, "Customer"));
					lir.setRECORD_ID("");
					
					//Add in the master list
					logRecordsBIBO.add(lir);
					
				}
				break;
				
			
			}

		}
		LogGenerator lg = new LogGenerator();
		
		DateFormat dateTimeFormat = new SimpleDateFormat("YYYYMMdd-HHmmss");
		Date date = new Date();
		String dateString = dateTimeFormat.format(date);
		
		String logFolder = configuration.getLogStore();
		String logFolderDate = logFolder+"/"+dateString.split("-")[0];
		String logFileBody = lg.consolidateRecords(logRecordsCommunication,logFolderDate, logFolderDate+"/"+"Communication_"+dateString+".csv")
							+lg.consolidateRecords(logRecordsCustomer, logFolderDate, logFolderDate+"/"+"Customer_"+dateString+".csv")
							+lg.consolidateRecords(logRecordsImageScore, logFolderDate, logFolderDate+"/"+"ImageScore_"+dateString+".csv")
							+lg.consolidateRecords(logRecordsShipTo, logFolderDate, logFolderDate+"/"+"CustomerShipTo_"+dateString+".csv")
							+lg.consolidateRecords(logRecordsBIBO, logFolderDate, logFolderDate+"/"+"BIBO_"+dateString+".csv")
				;
		System.out.println(logFileBody);
		Pair<String,String> logPair = new Pair<String,String>(logFileBody,dateString);
		return logPair;
	}
	public String getFieldFromPair(Pair<String,String> recordPair[],String field)
	{
		for(Pair record:recordPair)
		{
			if(record!=null && record.getKey()!=null && record.getKey().equals(field))
			{
				return (String) record.getValue();
			}
		}
		return null;
	}
	
	public LogInformationRecord updateField(String fileName, LogInformationRecord lir,String flag,String fieldName,String fieldValue,String searchField)
	{
		QueryBuilder QB = new QueryBuilder();
		Pair<String,String> updatePair = new Pair<String,String>(fieldName,fieldValue);
		Pair<String,String> updatePairGroup[] = new Pair[1];
		updatePairGroup[0] = updatePair;
		String updateBody = QB.createJSONObject(updatePairGroup);
		RESTQuery rest = new RESTQuery();
		RESTResponse resp = null;
		Settings config = Settings.getInstance();
		int numberOfTry = config.getNumberOfRetry();
		int numberOfRetryCount = 0;
		
		switch(fieldName)
		{
			case "CUSTOMER":
				resp = rest.updateRecord("Account", updateBody,searchField, null);
				while(!String.valueOf(resp.getStatusCode()).startsWith("2") && numberOfRetryCount<numberOfTry)
				{
					numberOfRetryCount++;
					resp = rest.updateRecord("Account", updateBody,searchField, null);
				}
			break;
			
			default:
				return null;
					
		}
		
		if(resp.getErrorMessage()!=null && resp.getErrorMessage().length()>0)
		{
			lir.setRESPONSE_OUTPUT(resp.getErrorMessage());
		}
		else
		{
			lir.setRESPONSE_OUTPUT("Updated");
		}
		
		lir.setNUMBER_OF_TRY(String.valueOf(numberOfRetryCount));
		lir.setRESPONSE_STATUS(String.valueOf(resp.getStatusCode()));
		
	return lir;
	}
	
	public LogInformationRecord createRecord(Pair<String,String> recordPair[],String object, LogInformationRecord lir,HashMap<String, Pair> mapper,String configMapping)
	{
		//Instance of require classes
		RESTQuery rest = new RESTQuery();
		QueryBuilder QB = new QueryBuilder();
		CSVReader csv = new CSVReader();
		
		//Number of fields from xml
		int numberOfFields = recordPair.length;
		
		//Handle boundary cases - remaining
		if(recordPair.length<0) return null;
		
		//Get number of retry specify in the setting file
		Settings config = Settings.getInstance();
		
		//Get number of try from configuration file
		int numberOfTry = config.getNumberOfRetry();
		switch(object)
		{
		
		//Handle for Account object
		case "Account":
			//Get partyNumber equivalent from xml file 
			String partyNumber = getFieldFromPair(recordPair, "Customer");
			
			//Check whether record exists or not. If yes do Update otherwise create
			RESTResponse resp = rest.recordExists(object, "PartyNumber", partyNumber);
			
			List<Pair<String,String> > lst = resp.getResponseList();
			
			//Response Object
			RESTResponse restResponse;
			int numberOfRetryCount = 0;
			
			//Record not exists. Create new record
			if(lst.isEmpty())
			{
				System.out.println("Record not exists :"+partyNumber);

				//Pair object that contains only required record
				Pair<String,String> Pair[] = new Pair[csv.returnMappingObjectCount(configMapping, "Account")];
				int PairCounter=0;
				
				//Insert relevant records in Pair
				for(int j=0;j<numberOfFields;j++)
				{
					Pair<String,String> SAPPair = recordPair[j];
					Pair<String,String> mapping = mapper.get(SAPPair.getKey());
						if(mapping.getKey()!=null && mapping.getValue()!=null && mapping.getKey().trim().length()>0 && mapping.getValue().trim().length()>0)
						{
							Pair[PairCounter] = new Pair<String,String>(mapping.getKey().trim(),SAPPair.getValue().trim());
							PairCounter++;
						}								
				}
				
				//Create JSON of body
				String accountBody = QB.createJSONObject(Pair);
				
				//Create record. If not successful try for specified times. Success means response status code starts with 2 (ie. 200,201,204 etc.)
				restResponse = rest.createRecord(object, accountBody, null);
				numberOfRetryCount = 0;
				while(!String.valueOf(restResponse.getStatusCode()).startsWith("2") && numberOfRetryCount<numberOfTry)
				{
					numberOfRetryCount++;
					System.out.println(accountBody);
					restResponse =rest.createRecord("Account", accountBody, null);
				}
				if(restResponse.getErrorMessage()!=null && restResponse.getErrorMessage().length()>0)
					{
						lir.setRESPONSE_OUTPUT(restResponse.getErrorMessage());
					}
				else
				{
					lir.setRESPONSE_OUTPUT("Created");
					System.out.println("Created");
				}

			}
			else
			{
				//Update information
				System.out.println("Record Exists.. Performing update");
				//System.out.println(recordPair.toString());
				numberOfRetryCount = 0;
				//Account key present in SAP XML data
				String accountKeyInXMLData = "Customer";
				
				//Account UNIQUE key for OSC
				String accountKeyInOSC = "PartyNumber";
				
				//Key field is not include in update body
				Pair<String,String> Pair[] = new Pair[csv.returnMappingObjectCount(configMapping, "Account")-1];
				int PairCounter=0;
				String accountKeyValue = null;
				for(int j=0;j<numberOfFields;j++)
				{
					//Accessing each information as pair
					Pair<String,String> SAPPair = recordPair[j];
					Pair<String,String> mapping = mapper.get(SAPPair.getKey());
					if(mapping.getKey()!=null && mapping.getValue()!=null && mapping.getKey().trim().length()>0 && mapping.getValue().trim().length()>0 && !mapping.getKey().trim().equals(accountKeyInOSC))
						{
							Pair[PairCounter] = new Pair<String,String>(mapping.getKey().trim(),SAPPair.getValue());
							PairCounter++;
						}
					else if(mapping.getKey().trim().equals(accountKeyInOSC))
					{
						accountKeyValue = SAPPair.getValue();
					}
						
				}
				//Json object of request body
				String accountBody = QB.createJSONObject(Pair);

				//Update record. If not sucess, try for specified times.
				restResponse = rest.updateRecord(object, accountBody,accountKeyValue,null);
				while(!String.valueOf(restResponse.getStatusCode()).startsWith("2") && numberOfRetryCount<numberOfTry)
				{
					System.out.println(restResponse.getStatusCode());
					numberOfRetryCount++;
					restResponse = rest.updateRecord(object, accountBody,accountKeyValue,null);
				}
				if(restResponse.getErrorMessage()!=null && restResponse.getErrorMessage().length()>0)
				{
					lir.setRESPONSE_OUTPUT(restResponse.getErrorMessage());
				}
				else
				{
					lir.setRESPONSE_OUTPUT("Updated");
				}
			}
			
			//Update in logs
			lir.setNUMBER_OF_TRY(String.valueOf(numberOfRetryCount));
			lir.setRESPONSE_STATUS(String.valueOf(restResponse.getStatusCode()));
			break;

		case "Business":
			break;
			
			
		case "ShipTo":
			//Get partyNumber equivalent from xml file 
			String CustomerCode = getFieldFromPair(recordPair, "Customer");
			
			//Check whether record exists or not. If yes do Update otherwise create
			RESTResponse respShip = rest.recordExists(object, "Id", CustomerCode);
			
			List<Pair<String,String> > lstShipTo = respShip.getResponseList();
			
			//Response Object
			RESTResponse restResponseShip;
			int numberOfRetryCountShip = 0;
			
			//Record not exists. Create new record
			if(lstShipTo.isEmpty())
			{
				System.out.println("Record not exists :"+CustomerCode);

				//Pair object that contains only required record
				Pair<String,String> Pair[] = new Pair[csv.returnMappingObjectCount(configMapping, "ShipTo")];
				int PairCounter=0;
				
				//Insert relevant records in Pair
				for(int j=0;j<numberOfFields;j++)
				{
					Pair<String,String> SAPPair = recordPair[j];
					Pair<String,String> mapping = mapper.get(SAPPair.getKey());
						if(mapping.getKey()!=null && mapping.getValue()!=null && mapping.getKey().trim().length()>0 && mapping.getValue().trim().length()>0)
						{
							Pair[PairCounter] = new Pair<String,String>(mapping.getKey().trim(),SAPPair.getValue().trim());
							PairCounter++;
						}								
				}
				
				//Create JSON of body
				String accountBody = QB.createJSONObject(Pair);
				
				//Create record. If not successful try for specified times. Success means response status code starts with 2 (ie. 200,201,204 etc.)
				restResponse = rest.createRecord(object, accountBody, null);
				numberOfRetryCount = 0;
				while(!String.valueOf(restResponse.getStatusCode()).startsWith("2") && numberOfRetryCount<numberOfTry)
				{
					numberOfRetryCount++;
					restResponse =rest.createRecord("ShipTo", accountBody, null);
				}
				if(restResponse.getErrorMessage()!=null && restResponse.getErrorMessage().length()>0)
					lir.setRESPONSE_OUTPUT(restResponse.getErrorMessage());
				else
					lir.setRESPONSE_OUTPUT("Created");

			}
			else
			{
				//Update information
				System.out.println("Record Exists.. Performing update");
				//System.out.println(recordPair.toString());
				numberOfRetryCount = 0;
				
				
				
				//Account key present in SAP XML data
				String accountKeyInXMLData = "Shipto";
				
				//Account UNIQUE key for OSC
				String accountKeyInOSC = "RecordName";
				
				//Get the id to update
				String recordName = getFieldFromPair(recordPair, "Shipto");
				long custId = rest.getLongFieldValue("ShipTo", accountKeyInOSC,recordName, "Id");

				//Key field is not include in update body
				Pair<String,String> Pair[] = new Pair[csv.returnMappingObjectCount(configMapping, "ShipTo")-1];
				int PairCounter=0;
				String accountKeyValue = null;
				for(int j=0;j<numberOfFields;j++)
				{
					//Accessing each information as pair
					Pair<String,String> SAPPair = recordPair[j];
					Pair<String,String> mapping = mapper.get(SAPPair.getKey());
					if(mapping.getKey()!=null && mapping.getValue()!=null && mapping.getKey().trim().length()>0 && mapping.getValue().trim().length()>0 && !mapping.getKey().trim().equals(accountKeyInOSC))
						{
							Pair[PairCounter] = new Pair<String,String>(mapping.getKey().trim(),SAPPair.getValue());
							PairCounter++;
						}
					else if(mapping.getKey().trim().equals(accountKeyInOSC))
					{
						accountKeyValue = SAPPair.getValue();
					}
						
				}
				//Json object of request body
				String accountBody = QB.createJSONObject(Pair);

				//Update record. If not sucess, try for specified times.
				restResponse = rest.updateRecord(object, accountBody,accountKeyValue,null);
				while(!String.valueOf(restResponse.getStatusCode()).startsWith("2") && numberOfRetryCount<numberOfTry)
				{
					System.out.println(restResponse.getStatusCode());
					numberOfRetryCount++;
					restResponse = rest.updateRecord(object, accountBody,accountKeyValue,null);
				}
				if(restResponse.getErrorMessage()!=null && restResponse.getErrorMessage().length()>0)
				{
					lir.setRESPONSE_OUTPUT(restResponse.getErrorMessage());
				}
				else
				{
					lir.setRESPONSE_OUTPUT("Updated");
				}
			}
			
			//Update in logs
			lir.setNUMBER_OF_TRY(String.valueOf(numberOfRetryCount));
			lir.setRESPONSE_STATUS(String.valueOf(restResponse.getStatusCode()));
			break;
		}
		return lir;
	}
	public LogRecordQuery fileToOSCQuery(Pair<String,String> infoPairs[][],HashMap<String, Pair> mapper)
	{
		try{
		int numberOfRecords = infoPairs.length;
		LogRecordQuery lrq = new LogRecordQuery();
		lrq.setRECORD_COUNT(numberOfRecords);
		int numberOfSucess = 0;
		int status[][] = new int[numberOfRecords][3];
		RESTResponse restResponseAccount[][] = new RESTResponse[numberOfRecords][3];
		DMLQuery dml = new DMLQuery();
		if(numberOfRecords>0)
		{
			int numberOfFields = infoPairs[0].length;
			Settings set = Settings.getInstance();
			CSVReader csv = new CSVReader();
			Pair<String,String> accountPair[] = new Pair[csv.returnMappingObjectCount(set.getMapping(), "Account")];
			Pair<String,String> BusinessPair[] = new Pair[csv.returnMappingObjectCount(set.getMapping(), "Business")];
			Pair<String,String> ShipToPair[] = new Pair[csv.returnMappingObjectCount(set.getMapping(), "ShipTo")];
			int accountPairCounter=0;
			int businessPairCounter=0;
			int shiptoPairCounter=0;
			
			
			for(int i=0;i<numberOfRecords;i++)
			{
				accountPairCounter=0;
				businessPairCounter=0;
				shiptoPairCounter=0;
				
				//Search for customer id
				String custID="";
				Pair<String,String> records[] = infoPairs[i];
				for(Pair<String,String> record:records)
				{
					if(record.getKey()!=null && record.getValue()!=null && record.getKey().equals("Customer"))
					{
						custID = record.getValue();
						break;
					}
					
				}
				
				for(int j=0;j<numberOfFields;j++)
				{
					Pair<String,String> SAPPair = infoPairs[i][j];
					Pair<String,String> mapping = mapper.get(SAPPair.getKey());
					if(mapping.getKey()!=null && mapping.getValue()!=null)
					{
						System.out.println(mapping.getValue());
						switch(mapping.getValue().trim())
						{
						case "Account":
							accountPair[accountPairCounter] = new Pair<String,String>(mapping.getKey().trim(),SAPPair.getValue().trim());
							accountPairCounter++;
							break;
						case "Business":
							BusinessPair[businessPairCounter] = new Pair<String,String>(mapping.getKey().trim(),SAPPair.getValue().trim());
							businessPairCounter++;
							break;
						case "ShipTo":
							ShipToPair[shiptoPairCounter] = new Pair<String,String>(mapping.getKey().trim(),SAPPair.getValue().trim());
							shiptoPairCounter++;
							break;
							
						}
							
					}
				}			
				QueryBuilder QB = new QueryBuilder();
				String accountBody = QB.createJSONObject(accountPair);
				
				String businessBody = QB.createJSONObject(BusinessPair);
				String shipToBody = QB.createJSONObject(ShipToPair);
				
				JSONParser parser = new JSONParser();
				System.out.println("@@@"+accountBody);
	        	JSONObject jsonAcc = (JSONObject) parser.parse(accountBody);
	        	//String custID = (String)jsonAcc.get("Customer");
				System.out.print("#"+custID);
				RESTQuery rest = new RESTQuery();
				String accountField[] = {"PartyNumber"};
				
				restResponseAccount[i][0] = rest.createRecord("Account", accountBody,accountField);
				
				List<Pair<String,String> >list = restResponseAccount[i][0].getResponseList();
				System.out.println(list.size());
				String partyNumber = "";
				for(Pair<String,String> pp :list)
				{
					//System.out.println(pp.getValue());
					if(pp.getKey().equals("PartyNumber"))
						{
							partyNumber = pp.getValue();
							break;
						}
				}
				System.out.println("!!"+partyNumber);
				
				
				restResponseAccount[i][1] = rest.createRecord("Business", businessBody,null);
				restResponseAccount[i][2] = rest.createRecord("ShipTo", shipToBody,null);
				
				DateFormat dateTimeFormat = new SimpleDateFormat("YYYYMMdd-HHmmss");
				Date date = new Date();
				String dateString = dateTimeFormat.format(date);
				String status1 = String.valueOf(restResponseAccount[i][0].getStatusCode());
				String status2 = String.valueOf(restResponseAccount[i][1].getStatusCode());
				String status3 = String.valueOf(restResponseAccount[i][2].getStatusCode());
				
				String overAllResponse="FAIL";
				if(status1.startsWith("2") )//&& status2.startsWith("2") && status3.startsWith("3"))
				{
					overAllResponse = "SUCCESS";
					numberOfSucess++;
				}
				System.out.println(custID+"::::"+partyNumber);
			//	dml.insertInDatabase("CUSTOMER", custID, partyNumber, "Insert", overAllResponse, status1, status2,status3, dateString, 1);
			}
		}
		lrq.setSUCCESS_COUNT(numberOfSucess);
		return lrq;
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public RESTResponse querySelector(String queryFlag, 
			String accountBody,
			String businessBody,
			String shipToBody, 
			String accountFields[],
			String businessFields[],
			String shipToFields[])
	{
		switch(queryFlag)
		{
		case "C":
			break;
		case "U":
			break;
		case "B":
			break;
		case "A":
			break;
		case "D":
			break;
		case "R":
			break;
		}
		
		return null;
	}
}
