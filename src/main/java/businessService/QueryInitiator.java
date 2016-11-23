package businessService;

import java.util.HashMap;

import configuration.Settings;
import input.CSVReader;
import javafx.util.Pair;

public class QueryInitiator {

	public RESTResponse[][] queryInitiator(Pair<String,String> infoPairs[][],HashMap<String, Pair> mapper)
	{
		int numberOfRecords = infoPairs.length;
		int status[][] = new int[numberOfRecords][3];
		RESTResponse restResponseAccount[][] = new RESTResponse[numberOfRecords][3];
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
				for(int j=0;j<numberOfFields;j++)
				{
					Pair<String,String> SAPPair = infoPairs[i][j];
					Pair<String,String> mapping = mapper.get(SAPPair.getKey());
					if(mapping.getKey()!=null && mapping.getValue()!=null)
					{
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
				String shipToBody = QB.createJSONObject(BusinessPair);
				String businessBody = QB.createJSONObject(ShipToPair);
				
				
				RESTQuery rest = new RESTQuery();
				String accountField[] = {"PartyNumber"};
				restResponseAccount[i][0] = rest.createAccount("Account", accountBody,accountField);
				restResponseAccount[i][1] = rest.createAccount("Business", businessBody,null);
				restResponseAccount[i][2] = rest.createAccount("ShipTo", shipToBody,null);
			}
		}
		return restResponseAccount;
	}
}
