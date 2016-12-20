package input;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class FolderOperations {

	public File[] getAllFiles(String folderLocation)
	{
		
		File folder = new File(folderLocation);
		if(folder.isDirectory()){
			return folder.listFiles();
		}
		return null;
		
	}
	public int fileMover(String inputFolder, String dataStore, int numberOfDays)
	{
		//Get the current date
		DateFormat dateTimeFormat = new SimpleDateFormat("YYYYMMdd-HHmmss");
		DateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
		Date date = new Date();
		
		//Foldername contains YYYYMMDD-HHMMSS format
		String folderName = dateTimeFormat.format(date);
		String currentDate = dateFormat.format(date);
		
		//Create a directory
		String newDirNamePath = dataStore+"/"+currentDate;
		boolean dirCreated = new File(newDirNamePath).mkdirs();
		
		try {
			FileUtils.copyDirectory(new File(inputFolder), new File(newDirNamePath));
			//Commented for testing
			FileUtils.cleanDirectory(new File(inputFolder));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Delete older than x months
		File folders = new File(dataStore);
		
		FileFilter directoryFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
		
		File folderList[] = folders.listFiles(directoryFilter);
		
		//Get the previous date
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -numberOfDays);
		Date olderDate = c.getTime();
		String olderDateString = dateFormat.format(olderDate);
		Integer olderDateInt = Integer.valueOf(olderDateString);
		
		
		//Remove all the data storage older than x days
		for(File ff:folderList)
		{
			String fileDate = ff.getName();
			int fileNameDate = Integer.valueOf(fileDate.split("-")[0]);
			if((olderDateInt-fileNameDate)>0)
			{
				try {
					FileUtils.deleteDirectory(ff);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return -1;
	}
}
