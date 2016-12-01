package util;

public class LogRecordQuery {

	private long RECORD_COUNT=0;
	private long UPDATE_COUNT=0;
	private long SUCCESS_COUNT=0;
	private long UPDATE_SUCESS_COUNT = 0;
	
	public long getUPDATE_SUCESS_COUNT() {
		return UPDATE_SUCESS_COUNT;
	}
	public void setUPDATE_SUCESS_COUNT(long uPDATE_SUCESS_COUNT) {
		UPDATE_SUCESS_COUNT = uPDATE_SUCESS_COUNT;
	}
	public long getRECORD_COUNT() {
		return RECORD_COUNT;
	}
	public void setRECORD_COUNT(long rECORD_COUNT) {
		RECORD_COUNT = rECORD_COUNT;
	}
	public long getUPDATE_COUNT() {
		return UPDATE_COUNT;
	}
	public void setUPDATE_COUNT(long uPDATE_COUNT) {
		UPDATE_COUNT = uPDATE_COUNT;
	}
	public long getSUCCESS_COUNT() {
		return SUCCESS_COUNT;
	}
	public void setSUCCESS_COUNT(long sUCCESS_COUNT) {
		SUCCESS_COUNT = sUCCESS_COUNT;
	}
	

}
