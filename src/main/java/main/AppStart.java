package main;



import javafx.util.Pair;
import util.Converter;
import util.Mailer;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;

import configuration.Settings;
import input.CSVReader;
import input.FolderOperations;
import input.XMLReader;

public class AppStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Initiator init = new Initiator();
		String SettingsFile = "./files/settings.xml";
		//init.initiateProcess(SettingsFile);
		Converter convert = new Converter();
		convert.generateAllMappingFiles("./sample", "./files/mappings/");
	}
	
	

}
