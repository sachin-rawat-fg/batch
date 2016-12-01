package businessService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import configuration.Settings;
import sun.misc.BASE64Encoder;
import javafx.util.Pair;

public class RESTQuery {

	public RESTResponse createRecord(String queryType, String body,String responseFields[])
	{		
		try{
		Settings set = Settings.getInstance();
		String contentType = "application/vnd.oracle.adf.resourceitem+json";
		String username = set.getLoginID();
		String password = set.getPassword();
		String url="";
		switch(queryType)
		{
			case "Account":
				url = set.getAccountURL();
				break;
			case "Business":
				url = set.getBusinessURL();
				break;
			case "ShipTo":
				url = set.getShipToURL();
				break;
		}
		String authString = username + ":" + password;
        String authStringEnc = new BASE64Encoder().encode(authString.getBytes());
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        //System.out.println(url);
        ClientResponse resp = webResource.type(contentType).header("Authorization", "Basic " + authStringEnc).post(ClientResponse.class,body);
        String output = resp.getEntity(String.class);
        System.out.println("response: "+output);
        
        Pair<Integer,String> statusReponse = new Pair<Integer,String>(resp.getStatus(), null);
 
        List <Pair<String,String> > lst = new ArrayList(); 
   
        String error = null;
        if(resp.getStatus()>=200 && resp.getStatus()<=210)
        {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(output);
        	if(responseFields!=null)
        	for(String res:responseFields)
        	{
        		lst.add(new Pair<String,String>(res,(String) json.get(res)));
        	}
        }
        else
        {
        	error = output;
        }
        RESTResponse restRep = new RESTResponse(resp.getStatus(),lst,error);        
		return restRep;
		}
		catch(Exception e){e.printStackTrace();}return null;
	}
	public RESTResponse updateRecord(String queryType,String body,String updateParameterKey,String updateParameterValue,String responseFields[])
	{
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			Settings set = Settings.getInstance();
			String contentType = "application/vnd.oracle.adf.resourceitem+json";
			String username = set.getLoginID();
			String password = set.getPassword();
			String url="";
			switch(queryType)
			{
				case "Account":
					url = set.getAccountURL();
					break;
				case "Business":
					url = set.getBusinessURL();
					break;
				case "ShipTo":
					url = set.getShipToURL();
					break;
			}
			String authString = username + ":" + password;
	        String authStringEnc = new BASE64Encoder().encode(authString.getBytes());

			HttpPatch patch= new HttpPatch(url+"/"+updateParameterValue);
			patch.addHeader("Content-Type",contentType);
	        patch.setHeader("Authorization", "Basic " + authStringEnc);
	        StringEntity input = new StringEntity(body);
			patch.setEntity(input);
			
			List<Pair<String,String> > list = null;
			String error = null;
			HttpResponse response = httpClient.execute(patch);
			
			
			
			if (response.getStatusLine().getStatusCode()>= 201 && response.getStatusLine().getStatusCode()<=210) {
				if(responseFields!=null && responseFields.length>0)
				{
					BufferedReader br = new BufferedReader(
		                    new InputStreamReader((response.getEntity().getContent())));
					StringBuilder sb = new StringBuilder();
					String partialoutput=null;
					
					while ((partialoutput = br.readLine()) != null) {
							sb.append(partialoutput);
					}
					String output = sb.toString();
					
					
					JSONParser parser = new JSONParser();
	            	JSONObject json = (JSONObject) parser.parse(output);
	            	if(responseFields!=null)
	            	for(String res:responseFields)
	            	{
	            		list.add(new Pair<String,String>(res,(String) json.get(res)));
	            	}
				}
				
			}
			else
			{
				BufferedReader br = new BufferedReader(
	                    new InputStreamReader((response.getEntity().getContent())));
				StringBuilder sb = new StringBuilder();
				String partialoutput=null;
				
				while ((partialoutput = br.readLine()) != null) {
						sb.append(partialoutput);
				}
				error = sb.toString();
			}
			RESTResponse restResponse = new RESTResponse(response.getStatusLine().getStatusCode(), list, error);
			httpClient.getConnectionManager().shutdown();
			return restResponse;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public RESTResponse deleteRecord(String queryType,String updateParameterKey,String updateParameterValue,String responseFields[])
	{
		try{
			Settings set = Settings.getInstance();
			String contentType = "application/json";
			String username = set.getLoginID();
			String password = set.getPassword();
			String url="";
			switch(queryType)
			{
				case "Account":
					url = set.getAccountURL();
					break;
				case "Business":
					url = set.getBusinessURL();
					break;
				case "ShipTo":
					url = set.getShipToURL();
					break;
			}
			String authString = username + ":" + password;
	        String authStringEnc = new BASE64Encoder().encode(authString.getBytes());
	        Client restClient = Client.create();
	        WebResource webResource = restClient.resource(url);
	        ClientResponse resp = webResource.accept("application/json")
                    .header("Authorization", "Basic " + authStringEnc)
                    .delete(ClientResponse.class);

	       
	        int statusCode = resp.getStatus();
	        List<Pair<String, String> > list = null;
	        String error = null;
	        RESTResponse restResponse;
	        String output = resp.getEntity(String.class);
	        if(statusCode>=200 && statusCode<=210)
	        {
	        	if(responseFields!=null && responseFields.length>0)
	        	{
	        		JSONParser parser = new JSONParser();
	            	JSONObject json = (JSONObject) parser.parse(output);
	            	if(responseFields!=null)
	            	for(String res:responseFields)
	            	{
	            		//System.out.println(res+"::"+json.get(res));
	            		list.add(new Pair<String,String>(res,(String) json.get(res)));
	            	}
	        	}
	        }
	        else
	        {
	        	error = output;
	        }
	        restResponse = new RESTResponse(statusCode, list, error);
	        return restResponse;
		}
		catch(Exception e)
		{
			
		}
		
		return null;
	}
}
