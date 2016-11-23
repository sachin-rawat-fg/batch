package businessService;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import configuration.Settings;
import sun.misc.BASE64Encoder;
import javafx.util.Pair;

public class RESTQuery {

	public RESTResponse createAccount(String queryType, String body,String responseFields[])
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
			case "Business":
				url = set.getBusinessURL();
			case "ShipTo":
				url = set.getShipToURL();
		}
		String authString = username + ":" + password;
        String authStringEnc = new BASE64Encoder().encode(authString.getBytes());
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        
        ClientResponse resp = webResource.type(contentType).header("Authorization", "Basic " + authStringEnc).post(ClientResponse.class,body);
        String output = resp.getEntity(String.class);
        System.out.println("response: "+output);
        
        Pair<Integer,String> statusReponse = new Pair<Integer,String>(resp.getStatus(), null);
 
        List <Pair<String,String> > lst = new ArrayList(); 
   
        if(resp.getStatus()>=200 && resp.getStatus()<=210)
        {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(output);
        	for(String res:responseFields)
        	{
        		lst.add(new Pair<String,String>(res,(String) json.get(res)));
        	}
        	
        	//statusReponse = new Pair<Integer,String>(resp.getStatus(), null);
            
        }
        RESTResponse restRep = new RESTResponse(resp.getStatus(),lst);
        
		return restRep;
		}
		catch(Exception e){e.printStackTrace();}return null;
	}
}
