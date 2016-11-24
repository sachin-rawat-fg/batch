package input;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import configuration.Settings;


import java.io.File;

public class XMLReader {

	public void readSAP(String fileLocation)
	{
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(fileLocation);
		
		try{
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			Element inputNode = rootNode.getChild("Input");
			List itemList = inputNode.getChildren("item");
			for(int i=0;i<itemList.size();i++)
			{
				Element node = (Element)itemList.get(i);
				System.out.println("Customer :"+node.getChildText("Customer"));
				System.out.println("Name :"+node.getChildText("Locationname"));
			}
			//4 หมู่ 8 ถ.บรมราชชนนี แขวงฉิมพลี
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
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
				
			}
			//4 หมู่ 8 ถ.บรมราชชนนี แขวงฉิมพลี
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
