package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import input.FolderOperations;
import javafx.util.Pair;

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
}

