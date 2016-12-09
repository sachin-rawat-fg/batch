package businessService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import configuration.Settings;
import dao.DMLQuery;
import input.CSVReader;
import javafx.util.Pair;
import util.LogRecordQuery;

public class QueryInitiator {

	
	
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
				dml.insertInDatabase("CUSTOMER", custID, partyNumber, "Insert", overAllResponse, status1, status2,status3, dateString, 1);
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
