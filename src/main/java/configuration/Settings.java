package configuration;

public class Settings {

	private String SAPDataFileFolder="";
	private String LogStore="";
	private String DataStore="";
	private String MappingFile="";
	
	private static Settings obj = new Settings();
	private Settings(){}
	public static Settings getInstance( ) {
	      return obj;
	   }
	
	public void setSAPDataFileFolder(String folderLocation)
	{
		SAPDataFileFolder = folderLocation;
	}
	public void setLogStore(String logStoreLocation)
	{
		LogStore = logStoreLocation;
	}
	public void setDataStore(String dataStoreLocation)
	{
		DataStore = dataStoreLocation;
	}
	public void setMapping(String mappingLocation)
	{
		MappingFile = mappingLocation;
	}
	public String getMapping()
	{
		return MappingFile;
	}
	public String getSAPDataFileFolder()
	{
		return SAPDataFileFolder;
	}
	public String getDataStore()
	{
		return DataStore;
	}
	public String getLogStore()
	{
		return LogStore;
	}
	
	
	}
