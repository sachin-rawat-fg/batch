package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


import configuration.Settings;
import input.CSVReader;
import input.FolderOperations;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr; 

//UTF-8 text to save file as UTF-8 format
//4 หมู่ 8 ถ.บรมราชชนนี แขวงฉิมพลี


public class Converter {

	public void convertXMLToCSV(String inputFile,String outputFile)
	{
		SAXBuilder builder = new SAXBuilder();
		Pair<String,String> pp = new Pair<String,String>("","");
		try{
			Writer out = new BufferedWriter(new OutputStreamWriter(
        		    new FileOutputStream(outputFile), "UTF-8"));
			
			out.append("SAP_Field,OSC_Field,OSC_Object\n");
			Document document = (Document) builder.build(inputFile);
			Element rootNode = document.getRootElement();
			Element inputNode = rootNode.getChild("Input");
			List itemList = inputNode.getChildren("item");
			for(int i=0;i<itemList.size();i++)
			{
				Element node = (Element)itemList.get(i);
				List attr = node.getChildren();
				for(int j = 0;j<attr.size();j++)
				{
					Element child = (Element)attr.get(j);
					out.append(child.getName()+"\n");
				}
				
				
				break;
			}
			out.flush();
			out.close();
			//File ff = new File(outputFile);
			//System.out.println(ff.getPath().replace('\\', '/'));
			//4 หมู่ 8 ถ.บรมราชชนนี แขวงฉิมพลี
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void generateAllMappingFiles(String inputFolder,String outputFolder)
	{
		FolderOperations fo= new FolderOperations();
		File[] allFiles = fo.getAllFiles(inputFolder);
		Converter convert = new Converter();
		for(File ff:allFiles)
		{
			String fileName = FilenameUtils.removeExtension(ff.getName());
			int index = fileName.lastIndexOf("_");
			fileName = fileName.substring(0, index)+"_Mapping";
			convert.convertXMLToCSV(ff.getAbsolutePath(), outputFolder+"/"+fileName+".csv");
		}
		System.out.println("Done");
	}
	public void convertXlsxToXML(String inputFile,String outputFile)
	{
		//Get instance of Settings
		Settings config = Settings.getInstance();
		
		//Get the location of BIBO Xlsx mapping file
		String XlsxToXMLMappingFile = config.getBIBO_XLSX_Mapping();
		
		//Create instance of CSVReader to get mapping
		CSVReader csvR = new CSVReader();
		
        try {
        	
        	//Create instance
        	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		
    		
    		//root elements is input
    		org.w3c.dom.Document doc = docBuilder.newDocument();
    		org.w3c.dom.Element rootElement = doc.createElement("ZsdmOscCommunication");
    		doc.appendChild(rootElement);

            org.w3c.dom.Element input = doc.createElement("Input");
       		rootElement.appendChild(input);

       		
            // Get the workbook object for XLSX file
    		XSSFWorkbook wBook = new XSSFWorkbook(new FileInputStream(inputFile));

            // Get first sheet from the workbook
            XSSFSheet sheet = wBook.getSheetAt(1);
            Row row;
            Cell cell;

            // Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            
            //To skip initial rows 
            int rowCounter = 0;
            
            while (rowIterator.hasNext()) {
            	rowCounter++;	
                row = rowIterator.next();
                
                //In the given BIBO Excel file 2 rows contain headers. Skipping those to access data
                if(rowCounter<=2) continue;
                        
                //item bucket. Each record comes under <item></item> tags.
                org.w3c.dom.Element item = doc.createElement("item");
           		input.appendChild(item);

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                
                //Read mappings for BIBO Xlsx
                HashMap<Integer, String> mapping = csvR.readMappingBIBO(XlsxToXMLMappingFile);
                
                //To maintain column count
                int columnCounter=0;
                
                //Iterate over each column
                while (cellIterator.hasNext()) {
                	columnCounter++;    		
                	cell = cellIterator.next();
                	
                	//Put in each element
                	org.w3c.dom.Element element;
                	String valueOfColumn = mapping.get(columnCounter);
                	if(valueOfColumn==null || valueOfColumn.length()==0)
                		continue;
                        		
                	switch (cell.getCellType()) {
                	
                	//Cell type(value) is boolean
                		case Cell.CELL_TYPE_BOOLEAN:
                			element = doc.createElement(mapping.get(columnCounter));
                			element.appendChild(doc.createTextNode(String.valueOf(cell.getBooleanCellValue())));
                			item.appendChild(element);
                		break;

                    	//Cell type(value) is numeric
                		case Cell.CELL_TYPE_NUMERIC:
                			element = doc.createElement(mapping.get(columnCounter));
                			element.appendChild(doc.createTextNode(String.valueOf(cell.getNumericCellValue())));
                			item.appendChild(element);
                        break;

                    	//Cell type(value) is String
                		case Cell.CELL_TYPE_STRING:  
                			element = doc.createElement(mapping.get(columnCounter));
                			element.appendChild(doc.createTextNode(String.valueOf(cell.getStringCellValue())));
                			item.appendChild(element);
                		break;

                    	//Cell type(value) is blank
                        case Cell.CELL_TYPE_BLANK:
                            element = doc.createElement(mapping.get(columnCounter));
                            element.appendChild(doc.createTextNode(""));
                            item.appendChild(element);
                        break;
                        
                        //Default case
                        default:
                        	element = doc.createElement(mapping.get(columnCounter));
                            element.appendChild(doc.createTextNode(""));
                            item.appendChild(element);
                        	
                        }
                    }
                }
            
            	// write the content into xml file
        		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        		Transformer transformer = transformerFactory.newTransformer();
        		DOMSource source = new DOMSource(doc);
        		StreamResult result = new StreamResult(new File(outputFile));
        		transformer.transform(source, result);

        } catch (Exception ioe) {
                ioe.printStackTrace();
        }
	}

}

