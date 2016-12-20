package input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javafx.util.Pair;

public class CSVReader {

	public HashMap<String,Pair> readMapping(String mappingFileLocation)
	{
		try {
			String readingLine="";
			BufferedReader br = new BufferedReader(new FileReader(mappingFileLocation));
			
			//Skip one for header
			readingLine = br.readLine();
			
			//Hashmap to store SAP_Field, OSC_Field and Object
			HashMap<String,Pair> mapper = new HashMap<String,Pair>();
			
			while ((readingLine = br.readLine()) != null) {

                // use comma as separator, -1 option is set to consider null values
                String[] fieldMap = readingLine.split(",",-1);
            
                //If reach to last line
                if(fieldMap.length<2)
                	break;
                
                //Insert key value in the map
                Pair<String,String> OSCFiledAndTable = new Pair<String,String>(fieldMap[1],fieldMap[2]);
                mapper.put(fieldMap[0],OSCFiledAndTable);
            }
			return mapper;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;
	}
	
	public int returnMappingObjectCount(String mappingFileLocation,String objectName)
	{
		try {
			String readingLine="";
			int objectCounter=0;
			BufferedReader br = new BufferedReader(new FileReader(mappingFileLocation));
			
			//Skip one for header
			readingLine = br.readLine();
			
			//Hashmap to store SAP_Field, OSC_Field and Object
			HashMap<String,Pair> mapper = new HashMap<String,Pair>();
			
			while ((readingLine = br.readLine()) != null) {

                // use comma as separator, -1 option is set to consider null values
                String[] fieldMap = readingLine.split(",",-1);
                
                //Insert key value in the map
                if(fieldMap[2]!=null && fieldMap[2].trim().length()>0)
                {
                	if(fieldMap[2].toLowerCase().trim().equals(objectName.toLowerCase().trim()))
                	{
                		objectCounter++;
                	}
                }
            }
			return objectCounter;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return -1;
	}

	public HashMap<Integer,String> readMappingBIBO(String mappingFileLocation)
	{
		try {
			String readingLine="";
			BufferedReader br = new BufferedReader(new FileReader(mappingFileLocation));
			
			//Skip one for header
			readingLine = br.readLine();
			
			//Hashmap to store SAP_Field, OSC_Field and Object
			HashMap<Integer,String> mapper = new HashMap<Integer,String>();
			
			while ((readingLine = br.readLine()) != null) {

                // use comma as separator, -1 option is set to consider null values
                String[] fieldMap = readingLine.split(",",-1);
                int lineCount = Integer.valueOf(fieldMap[0]);
                String lineValue = fieldMap[1];
                mapper.put(lineCount, lineValue);
                //Insert key value in the map
               // Pair<String,String> OSCFiledAndTable = new Pair<String,String>(fieldMap[1],fieldMap[2]);
                //mapper.put(fieldMap[0],OSCFiledAndTable);
            }
			return mapper;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;
	}
}
