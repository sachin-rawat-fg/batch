package configuration;

public class Settings {

	private String SAPDataFileFolder="";
	private String LogStore="";
	private String DataStore="";
	private String MappingFile="";
	private String LoginId="";
	private String Password="";
	private String AccountURL="";
	private String BusinessURL="";
	private String ShipToURL="";
	private String SMTP_URL="";
	private String SMTP_Port="";
	private String SMTP_Username="";
	private String SMTP_Password="";
	private String NotificationReceiver="";
	private int NumberOfRetry=0;
	private int numberOfDaysArchive=0; 
	
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
	public void setLoginId(String loginID)
	{
		LoginId = loginID;
	}
	public String getLoginID()
	{
		return LoginId;
	}
	public void setPassword(String password)
	{
		Password = password;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setAccountURL(String accountURL)
	{
		AccountURL = accountURL;
	}
	public String getAccountURL()
	{
		return AccountURL;
	}
	public void setBusinessURL(String businessURL)
	{
		BusinessURL = businessURL;
	}
	public String getBusinessURL()
	{
		return BusinessURL;
	}
	public void setShipToURL(String shipTo)
	{
		ShipToURL = shipTo;
	}
	public String getShipToURL()
	{
		return ShipToURL;
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
	public void setSmtpUrl(String smtpUrl)
	{
		SMTP_URL = smtpUrl;
	}
	public String getSmtpUrl()
	{
		return SMTP_URL;
	}
	public void setSmtpPort(String smtpPort)
	{
		SMTP_Port = smtpPort;
	}
	public String getSmtpPort()
	{
		return SMTP_Port;
	}
	public void setSmtpUsername(String smtpUsername)
	{
		SMTP_Username = smtpUsername;
	}
	public String getSmtpUsername()
	{
		return SMTP_Username;
	}
	public void setSmtpPassword(String smtpPassword)
	{
		SMTP_Password = smtpPassword;
	}
	public String getSmtpPassword()
	{
		return SMTP_Password;
	}
	public void setNumberOfRetry(int numberOfRetry)
	{
		NumberOfRetry = numberOfRetry;
	}
	public int getNumberOfRetry()
	{
		return NumberOfRetry;
	}
	public void setNotificationReceiver(String reciverList)
	{
		NotificationReceiver = reciverList;
	}
	public String getNotificationReceiver()
	{
		return NotificationReceiver;
	}
	public void setNumberOfArchieveDays(int days)
	{
		numberOfDaysArchive = days;
	}
	public int getsetNumberOfArchieveDays()
	{
		return numberOfDaysArchive;
	}
	
	
	
	}
