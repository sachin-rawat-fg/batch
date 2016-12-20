package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import configuration.Settings;
import dao.DMLQuery;

public class LogGenerator {

	public File generateLogFile(String tableName,String fieldNames[], String searchField,String dateString)
	{
		//Create folder if it is not created
		Settings set = Settings.getInstance();
		String folderName= dateString.split("-")[0];
		String fileName = folderName +"-"+dateString.split("-")[1]+".csv";
		String logFileLocation = set.getLogStore()+"\\"+folderName+"\\"+fileName;
		
		File logF = new File(logFileLocation);
		logF.getParentFile().mkdirs();
		
		DMLQuery dml = new DMLQuery();
		String logBody = dml.generateLogReport(tableName, fieldNames, searchField, dateString.split("-")[0]);
		try {
			FileUtils.writeStringToFile(logF, logBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logF;

	}
	
	public String consolidateRecords(List<LogInformationRecord> logs,String folderpath, String filePath)
	{
		StringBuilder result = new StringBuilder();
		if(logs.size()==0) return "";
		try{
		
		StringBuilder saver = new StringBuilder();
		Iterator logIterator = logs.iterator();
		int numberOfRecords = 0;
		int numberOfSucessfulOperation = 0;
		
		//Create directories in the path
		boolean dirCreated = new File(folderpath).mkdirs();
		
		//object to write in the output file
		Writer out = new BufferedWriter(new OutputStreamWriter(
    		    new FileOutputStream(filePath,true), "UTF-8"));
		saver.append("FileName,RecordID,FLAG,RESPOSE_STATUS,RESPONSE_OUTPUT,Number_of_retry\n");
		
		while(logIterator.hasNext())
		{
			LogInformationRecord lg = (LogInformationRecord) logIterator.next();
			saver.append(lg.getFILENAME()+","+
						lg.getRECORD_ID()+","+
						lg.getFLAG()+","+
						lg.getRESPONSE_STATUS()+","+
						lg.getRESPONSE_OUTPUT()+","+
						lg.getNUMBER_OF_TRY()+"\n");
			
			numberOfRecords++;
			if(lg.getRESPONSE_STATUS().startsWith("2"))
				numberOfSucessfulOperation++;
		}
		out.append(saver.toString());
		out.flush();
		out.close();
		
		result.append(new File(filePath).getName()+"\n");
		result.append("Number of Records :"+numberOfRecords+"\n");
		result.append("Number of successful operations :"+numberOfSucessfulOperation+"\n");
		result.append("Number of failed operations :"+(numberOfRecords-numberOfSucessfulOperation)+"\n");
		result.append("*****************************************"+"\n");
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
}
