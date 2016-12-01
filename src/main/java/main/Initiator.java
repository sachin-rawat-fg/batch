package main;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import businessService.QueryInitiator;
import businessService.RESTResponse;
import configuration.Settings;
import input.CSVReader;
import input.FolderOperations;
import input.XMLReader;
import javafx.util.Pair;
import util.LogGenerator;
import util.LogRecordQuery;
import util.Mailer;

public class Initiator {

	public void initiateProcess(String settingFile)
	{
		//Container for the log notification body.
		LogRecordQuery completeDayLogger = new LogRecordQuery();
		
		//number of records processed in one batch run
		int numberOfRecords=0;
		
		//number of records passed successfully in one batch run
		int numberOfSuccessfulInsertion=0;
		
		//number of update query
		int numberOfUpdates = 0;
		
		//number of query for update completed successfully
		int numberOfSuccessfulUpdate = 0;
		
		//Read settings
		XMLReader xml = new XMLReader();
		xml.readSetting(settingFile);
		
		//Setting class object to access settings
		Settings configuration = Settings.getInstance();
		
		//Read mappings
		CSVReader csvR = new CSVReader();
		HashMap<String, Pair> mapper = csvR.readMapping(configuration.getMapping());
		
		
		//Read all the files available in the SAP Folder
		FolderOperations fo= new FolderOperations();
		File[] allFiles = fo.getAllFiles(configuration.getSAPDataFileFolder());
		for(File ff:allFiles)
		{
			//Consider only XML file ?? If other file exists need to modify the condition.
			if(ff.getName().endsWith("xml"))
			{				
				//Get pairs from xml file
				Pair<String,String> infoPairs[][] = xml.readSAP(ff,mapper);
				
				//Object to build query for OSC.
				QueryInitiator query = new QueryInitiator();
				
				//Store log record for each xml file.
				LogRecordQuery partialLog = query.queryInitiator(infoPairs, mapper);
				
				//Updates global variable
				numberOfRecords += partialLog.getRECORD_COUNT();
				numberOfSuccessfulInsertion += partialLog.getSUCCESS_COUNT();
				numberOfUpdates += partialLog.getUPDATE_COUNT();
				numberOfSuccessfulUpdate+=partialLog.getUPDATE_SUCESS_COUNT();
								
			}
		}
		
		//Set variables in global log record.
		completeDayLogger.setRECORD_COUNT(numberOfRecords);
		completeDayLogger.setSUCCESS_COUNT(numberOfSuccessfulInsertion);
		completeDayLogger.setUPDATE_COUNT(numberOfUpdates);
		completeDayLogger.setUPDATE_SUCESS_COUNT(numberOfSuccessfulUpdate);
		
		//Setting fields for log file.
		String fields[] = {"CUSTOMERID","PARTYNUMBER","QUERYTYPE","STATUS","STATUS1","STATUS2","STATUS3","DATE"};
		
		//Mail about task status
		Mailer mm = new Mailer();
		
		//List of reciver from xml file. Separated by comma.
		String recieverList[] = configuration.getNotificationReceiver().split(",");
		
		//Date format use to naming datastore and logfiles.
		DateFormat dateTimeFormat = new SimpleDateFormat("YYYYMMdd-HHmmss");
		Date date = new Date();
		String dateString = dateTimeFormat.format(date);
		
		//Body of text of email notifications.
		String mailText = "Number of Records :"+numberOfRecords+"\n Number of Sucess :"+numberOfSuccessfulInsertion;
		
		//Generate log files
		LogGenerator lg = new LogGenerator();
		File attach1 =  lg.generateLogFile("Customer",fields , "DATE", dateString);
		
		//attachment - only mapping file generated from log-database 
		String atttachmentList[] = {attach1.getAbsolutePath()};
		
		//Move files, cleanup
		FolderOperations fold  = new FolderOperations();
		fold.fileMover(configuration.getSAPDataFileFolder(), configuration.getDataStore(), 90);
		
		//send email notification
		mm.sendNotification(recieverList,"STATUS MAIL <"+dateString+">" , mailText, atttachmentList);
		
	}
	
}
