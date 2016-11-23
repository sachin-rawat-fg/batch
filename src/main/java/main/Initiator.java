package main;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import businessService.QueryInitiator;
import businessService.RESTResponse;
import configuration.Settings;
import input.CSVReader;
import input.FolderOperations;
import input.XMLReader;
import javafx.util.Pair;

public class Initiator {

	public void initiateProcess(String settingFile)
	{
		//Read settings
		XMLReader xml = new XMLReader();
		xml.readSetting(settingFile);
		
		//Setting class object to access settings
		Settings configuration = Settings.getInstance();
		
		//Read mappings
		CSVReader csvR = new CSVReader();
		HashMap<String, Pair> mapper = csvR.readMapping(configuration.getMapping());
		
		
		//Read all the files
		FolderOperations fo= new FolderOperations();
		File[] allFiles = fo.getAllFiles(configuration.getSAPDataFileFolder());
		for(File ff:allFiles)
		{
			if(ff.getName().endsWith("xml"))
			{
				//Get pairs from xml file
				Pair<String,String> infoPairs[][] = xml.readSAP(ff,mapper);
				QueryInitiator query = new QueryInitiator();
				RESTResponse[][] restResponse = query.queryInitiator(infoPairs, mapper);
				
				//Halt
				
				
				//Send pair to business service
				//Implement QueryBuilder
				//Implement RESTQuery
				//Implement QueryInitiator
				//Implement DAO
				//Research on logs - overall, system, csv status, failure details
				
			}
		}
	}
	
}
