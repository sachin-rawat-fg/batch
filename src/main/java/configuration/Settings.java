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
	private String DBClass ="";
	private String DBUrl ="";
	private String DBUsername="";
	private String DBPassword="";
	private String COMMUNICATION_Mapping="";
	private String CUSTOMER_Mapping="";
	private String CUSTOMER_SALESID_Mapping="";
	private String CUSTOMER_SHIPTO_Mapping="";
	private String LATEST_ORDER_Mapping="";
	private String PRODUCT_Mapping="";
	private String SALES_AREA_Mapping="";
	private String SALES_PERSON_Mapping="";
		
	
	public String getCOMMUNICATION_Mapping() {
		return COMMUNICATION_Mapping;
	}
	public void setCOMMUNICATION_Mapping(String cOMMUNICATION_Mapping) {
		COMMUNICATION_Mapping = cOMMUNICATION_Mapping;
	}
	public String getCUSTOMER_Mapping() {
		return CUSTOMER_Mapping;
	}
	public void setCUSTOMER_Mapping(String cUSTOMER_Mapping) {
		CUSTOMER_Mapping = cUSTOMER_Mapping;
	}
	public String getCUSTOMER_SALESID_Mapping() {
		return CUSTOMER_SALESID_Mapping;
	}
	public void setCUSTOMER_SALESID_Mapping(String cUSTOMER_SALESID_Mapping) {
		CUSTOMER_SALESID_Mapping = cUSTOMER_SALESID_Mapping;
	}
	public String getCUSTOMER_SHIPTO_Mapping() {
		return CUSTOMER_SHIPTO_Mapping;
	}
	public void setCUSTOMER_SHIPTO_Mapping(String cUSTOMER_SHIPTO_Mapping) {
		CUSTOMER_SHIPTO_Mapping = cUSTOMER_SHIPTO_Mapping;
	}
	public String getLATEST_ORDER_Mapping() {
		return LATEST_ORDER_Mapping;
	}
	public void setLATEST_ORDER_Mapping(String lATEST_ORDER_Mapping) {
		LATEST_ORDER_Mapping = lATEST_ORDER_Mapping;
	}
	public String getPRODUCT_Mapping() {
		return PRODUCT_Mapping;
	}
	public void setPRODUCT_Mapping(String pRODUCT_Mapping) {
		PRODUCT_Mapping = pRODUCT_Mapping;
	}
	public String getSALES_AREA_Mapping() {
		return SALES_AREA_Mapping;
	}
	public void setSALES_AREA_Mapping(String sALES_AREA_Mapping) {
		SALES_AREA_Mapping = sALES_AREA_Mapping;
	}
	public String getSALES_PERSON_Mapping() {
		return SALES_PERSON_Mapping;
	}
	public void setSALES_PERSON_Mapping(String sALES_PERSON_Mapping) {
		SALES_PERSON_Mapping = sALES_PERSON_Mapping;
	}
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
	public void setNumberOfArchieveDays(int days)
	{
		numberOfDaysArchive = days;
	}
	public int getsetNumberOfArchieveDays()
	{
		return numberOfDaysArchive;
	}
	
	public void setNotificationReceiver(String reciverList)
	{
		NotificationReceiver = reciverList;
	}
	public String getNotificationReceiver()
	{
		return NotificationReceiver;
	}
	public void setDBUrl(String dburl)
	{
		DBUrl = dburl;
	}
	public String getDBUrl()
	{
		return DBUrl;
	}
	public void setDBClass(String classurl)
	{
		DBClass = classurl;
	}
	public String getDBClass()
	{
		return DBClass;
	}
	
	public void setDBUsername(String username)
	{
		DBUsername = username;
	}
	public String getDBUsername()
	{
		return DBUsername;
	}
	public void setDBPassword(String password)
	{
		DBPassword = password;
	}
	public String getDBPassword()
	{
		return DBPassword;
	}
	
	
	
	}
