package main;



import javafx.util.Pair;
import util.Converter;
import util.Mailer;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import businessService.QueryInitiator;
import businessService.RESTQuery;
import businessService.RESTResponse;
import configuration.Settings;
import input.CSVReader;
import input.FolderOperations;
import input.XMLReader;

public class AppStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Initiator object
		Initiator init = new Initiator();
		
		//Location of setting file relative to development folder
		String SettingsFile = "./files/settings.xml";
		
		//Read settings (initialize configuration)
		//XMLReader xml = new XMLReader();
		//xml.readSetting(SettingsFile);
		
		//Call startup process
		//init.initiateProcess(SettingsFile);
		
		//init.initiateProcess(SettingsFile);
		//Settings set = Settings.getInstance();
		//Converter convert = new Converter();
		
		//convert.convertXlsxToXML(set.getSAPDataFileFolder()+"/BIBO_Example_final.xlsx",set.getSAPDataFileFolder()+"/BIBO_XML_.xml");
		//convert.generateAllMappingFiles("./sample", "./files/mappings/");
		//convert.generateAllMappingFiles("./bibo", "./bibo/");
	
	}
	
	

}
