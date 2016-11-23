package main;



import javafx.util.Pair;
import util.Mailer;

import java.util.HashMap;

import configuration.Settings;
import input.CSVReader;
import input.FolderOperations;
import input.XMLReader;

public class AppStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Path for settings file
		String SettingsFile = "./files/settings.xml";

		//Reading setting.xml to get settings
		XMLReader xml = new XMLReader();
		xml.readSetting(SettingsFile);
		
		CSVReader cs = new CSVReader();
		
		Settings settings = Settings.getInstance();
		try{
		//Thread.sleep(60*1000);
		}
		catch(Exception e){}
		System.out.println(settings.getSAPDataFileFolder());
		System.out.println(settings.getMapping());
		HashMap<String, Pair> hs = cs.readMapping(settings.getMapping());
		
		//
		Mailer mm = new Mailer();
		mm.sendMail("abhishek.vijayvargia@freshgravity.com", "notification test", "test");
		int numberOfFields = hs.size();
		FolderOperations fold = new FolderOperations();
		fold.fileMover("", "", 2);
	}

}
