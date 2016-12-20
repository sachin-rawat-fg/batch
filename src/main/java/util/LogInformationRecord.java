package util;

public class LogInformationRecord {

	private String FILENAME;
	private String RECORD_ID;
	private String FLAG;
	private String RESPONSE_STATUS;
	private String RESPONSE_OUTPUT;
	private String NUMBER_OF_TRY;
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public String getRECORD_ID() {
		return RECORD_ID;
	}
	public void setRECORD_ID(String rECORD_ID) {
		RECORD_ID = rECORD_ID;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
	public String getRESPONSE_STATUS() {
		return RESPONSE_STATUS;
	}
	public void setRESPONSE_STATUS(String rESPONSE_STATUS) {
		RESPONSE_STATUS = rESPONSE_STATUS;
	}
	public String getRESPONSE_OUTPUT() {
		return RESPONSE_OUTPUT;
	}
	public void setRESPONSE_OUTPUT(String rESPONSE_OUTPUT) {
		if(rESPONSE_OUTPUT!=null)
			RESPONSE_OUTPUT = rESPONSE_OUTPUT.replace(",", "");
		else
			RESPONSE_OUTPUT = null;
	}
	public String getNUMBER_OF_TRY() {
		return NUMBER_OF_TRY;
	}
	public void setNUMBER_OF_TRY(String nUMBER_OF_TRY) {
		NUMBER_OF_TRY = nUMBER_OF_TRY;
	}
}
