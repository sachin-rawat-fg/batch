package util;

import java.io.File;
import java.io.IOException;

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
	
}
