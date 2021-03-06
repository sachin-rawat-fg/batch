package businessService;
import org.json.simple.JSONObject;

import javafx.util.Pair;

public class QueryBuilder {

	/**
	 * Function to create json body to post request.
	 * @param infoObject
	 * @return
	 */
	public String createJSONObject(Pair<String,String> infoObject[])
	{
		JSONObject json = new JSONObject();
		for(int i=0;i<infoObject.length;i++)
		{
			json.put(infoObject[i].getKey(), infoObject[i].getValue());
		}
		return json.toString();
	}
	 
}

