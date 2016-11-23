package input;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import javafx.util.Pair;
import configuration.Settings;


import java.io.File;

public class XMLReader {

	public Pair<String,String>[][] readSAP(File xmlFile,HashMap<String,Pair> mapper)
	{
		SAXBuilder builder = new SAXBuilder();
		Pair<String,String> pp = new Pair<String,String>("","");
		try{
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			Element inputNode = rootNode.getChild("Input");
			List itemList = inputNode.getChildren("item");
			Pair<String,String>[][] recordPairs = new Pair[itemList.size()][mapper.size()];
			Set keySet = mapper.keySet();
			String keySetStrings[] = mapper.keySet().toArray(new String[mapper.keySet().size()]);
			for(int i=0;i<itemList.size();i++)
			{
				Element node = (Element)itemList.get(i);
				for(int j=0;j<keySetStrings.length;j++)
				{
					String childNode = node.getChildText(keySetStrings[j]);
					recordPairs[i][j] = new Pair<String,String>(keySetStrings[j], childNode);
				}	
				System.out.println("Customer :"+node.getChildText("Customer"));
				System.out.println("Name :"+node.getChildText("Locationname"));
			}
			//4 หมู่ 8 ถ.บรมราชชนนี แขวงฉิมพลี
			return recordPairs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public void readSetting(String fileLocation)
	{
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(fileLocation);
		
		Settings sc = Settings.getInstance();
		try{
			System.out.println(xmlFile.getCanonicalPath());
			Document document = (Document) builder.build(xmlFile);
			System.out.println(document.getContentSize());
			Element rootNode = document.getRootElement();
			//System.out.println(rootNode.getText());
			List itemList = rootNode.getChildren("Settings");
			for(int i=0;i<itemList.size();i++)
			{
				Element node = (Element)itemList.get(i);
				
				//Set customer file folder location
				sc.setSAPDataFileFolder(node.getChildText("SAPDataFile"));
				
				//Set data store path
				sc.setDataStore(node.getChildText("DataStore"));
				
				//Set log store path
				sc.setLogStore(node.getChildText("LogStore"));
				
				//Set mapping
				sc.setMapping(node.getChildText("MappingFile"));
				
				//Set smtp details
				sc.setSmtpUrl(node.getChildText("SMTPURL"));
				sc.setSmtpPort(node.getChildText("SMTPPort"));
				sc.setSmtpUsername(node.getChildText("SMTPUserName"));
				sc.setSmtpPassword(node.getChildText("SMTPPassword"));
				
				//notification setting
				sc.setNotificationReceiver(node.getChildText("notificationReceiver"));
				
				//retry attempts
				sc.setNumberOfRetry(Integer.valueOf(node.getChildText("numberOfRetry")));
				
				//number of archieve days
				sc.setNumberOfArchieveDays(Integer.valueOf(node.getChildText("DataStoreArchiveLimitInDays")));
				
			}
			//4 หมู่ 8 ถ.บรมราชชนนี แขวงฉิมพลี
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
