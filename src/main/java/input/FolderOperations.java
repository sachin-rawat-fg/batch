package input;

import java.io.File;

public class FolderOperations {

	public File[] getAllFiles(String folderLocation)
	{
		
		File folder = new File(folderLocation);
		if(folder.isDirectory()){
			return folder.listFiles();
		}
		return null;
		
	}
}
