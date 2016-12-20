package main;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import businessService.QueryInitiator;
import businessService.RESTResponse;
import configuration.Settings;
import input.CSVReader;
import input.FolderOperations;
import input.XMLReader;
import javafx.util.Pair;
import util.Converter;
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
		
		/*
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
				LogRecordQuery partialLog = query.fileToOSCQuery(infoPairs, mapper);
				
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
		*/
		FolderOperations fold  = new FolderOperations();
		
		//Convert all excel files to xml
		Converter convert = new Converter();
		File allFiles[] = fold.getAllFiles(configuration.getSAPDataFileFolder());
		for(File biboFile:allFiles)
		{
			if(biboFile.getName().startsWith("BIBO"))
			{
				String extn = FilenameUtils.getExtension(biboFile.getAbsolutePath());
				String nameWithoutExtn = FilenameUtils.getBaseName(biboFile.getAbsolutePath());
				if(extn.equals("xlsx"))
				{
					convert.convertXlsxToXML(biboFile.getAbsolutePath(), biboFile.getParent()+"/"+nameWithoutExtn+".xml");
				}
			}
		}
		
		
		
		QueryInitiator qi  = new QueryInitiator();
		Pair<String,String> logPair = qi.queryInitiate();
		String logBody = logPair.getKey();
		//Mail about task status
		Mailer mm = new Mailer();
		
		//List of reciver from xml file. Separated by comma.
		String recieverList[] = configuration.getNotificationReceiver().split(",");
		
		//Date format use to naming datastore and logfiles.
		//DateFormat dateTimeFormat = new SimpleDateFormat("YYYYMMdd-HHmmss");
		//Date date = new Date();
		String dateString = logPair.getValue();
		
		//Body of text of email notifications.
		//String mailText = "Number of Records :"+numberOfRecords+"\n Number of Sucess :"+numberOfSuccessfulInsertion;
		
		//Generate log files
		//LogGenerator lg = new LogGenerator();
		//File attach1 =  lg.generateLogFile("Customer",fields , "DATE", dateString);
		
		//attachment - only mapping file generated from log-database 
		String logFiles = configuration.getLogStore()+"/"+dateString.split("-")[0];
		File allLogFiles[] = fold.getAllFiles(logFiles);
		List<String> attachList = new ArrayList<String>();
		for(File ff:allLogFiles)
		{
			String fileNameWithoutExtn = FilenameUtils.removeExtension(ff.getName());
			if(fileNameWithoutExtn.endsWith(dateString))
				attachList.add(ff.getAbsolutePath());
		}
		String atttachmentList[] = attachList.toArray(new String[attachList.size()]);
	
		
		/*
		String atttachmentList[] = new String[allLogFiles.length];
		for(int i =0;i<atttachmentList.length;i++)
		{
			atttachmentList[i] = allLogFiles[i].getAbsolutePath();
		}
		*/
		
		//Move files, cleanup
		fold.fileMover(configuration.getSAPDataFileFolder(), configuration.getDataStore(), 90);
		
		//send email notification
		mm.sendNotification(recieverList,"STATUS MAIL <"+dateString+">" , logBody, atttachmentList);
		
	}
	
}
