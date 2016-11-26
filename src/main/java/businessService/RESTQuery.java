package businessService;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import configuration.Settings;
import sun.misc.BASE64Encoder;

public class RESTQuery {

	public int createAccount(String queryType, String body)
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
		return resp.getStatus();
		}
		catch(Exception e){e.printStackTrace();}return -1;
	}
}
