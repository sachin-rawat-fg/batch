package businessService;

import java.util.List;
import javafx.util.Pair;

public class RESTResponse {

	private int statusCode;
	private List<Pair<String,String> > responseList;
	private String errorMessage="";
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public RESTResponse(int code,List<Pair<String,String> > list,String error )
	{
		statusCode = code;
		responseList = list;
		errorMessage = error;
	}
	public void setStatusCode(int code)
	{
		statusCode = code;
	}
	public void setResponseList(List<Pair<String,String> > response)
	{
		responseList = response;
	}
	public int getStatusCode()
	{
		return statusCode;
	}
	public List<Pair<String,String> > getResponseList()
	{
		return responseList;
	}
}
