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

	public HashMap<String,String> readFlags(File xmlFile,List<String> flags)
	{
		//Declare hashmap object
		HashMap<String, String> flagMapper = new HashMap<>();
		
		//
		
		return null;
	}
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
			//System.out.println(xmlFile.getCanonicalPath());
			Document document = (Document) builder.build(xmlFile);
			//System.out.println(document.getContentSize());
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
				
				
				//Set url for different objects
				sc.setAccountURL(node.getChildText("AccountURL"));
				sc.setBusinessURL(node.getChildText("BusinessURL"));
				sc.setShipToURL(node.getChildText("ShipToURL"));
				sc.setLoginId(node.getChildText("LoginId"));
				sc.setPassword(node.getChildText("Password"));
				
				
				//notification setting
				sc.setNotificationReceiver(node.getChildText("notificationReceiver"));
				
				//retry attempts
				sc.setNumberOfRetry(Integer.valueOf(node.getChildText("numberOfRetry")));
				
				//number of archieve days
				sc.setNumberOfArchieveDays(Integer.valueOf(node.getChildText("DataStoreArchiveLimitInDays")));
				
				
				//Database settings
				sc.setDBClass(node.getChildText("DatabaseDriver"));
				sc.setDBUrl(node.getChildText("DatabaseUrl"));
				sc.setDBUsername(node.getChildText("DatabaseUsername"));
				sc.setDBPassword(node.getChildText("DatabasePassword"));
				
				//Mapping files
				sc.setCOMMUNICATION_Mapping(node.getChildText("CommunicationMappingFile"));
				sc.setCUSTOMER_Mapping(node.getChildText("CustomerMappingFile"));
				sc.setCUSTOMER_SALESID_Mapping(node.getChildText("CustomerSalesIDMappingFile"));
				sc.setCUSTOMER_SHIPTO_Mapping(node.getChildText("CustomerShipToMappingFile"));
				sc.setLATEST_ORDER_Mapping(node.getChildText("LatestOrderMappingFile"));
				sc.setPRODUCT_Mapping(node.getChildText("ProductMappingFile"));
				sc.setSALES_AREA_Mapping(node.getChildText("SalesAreaMappingFile"));
				sc.setSALES_PERSON_Mapping(node.getChildText("SalesPersonMappingFile"));
				sc.setBIBO_XLSX_Mapping(node.getChildText("BIBOExcelMappingFile"));
				sc.setBIBO_Mapping(node.getChildText("BIBOMappingFile"));
				sc.setIMAGE_SCORE_Mapping(node.getChildText("ImageScoreMappingFile"));
			}
			//4 หมู่ 8 ถ.บรมราชชนนี แขวงฉิมพลี
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
