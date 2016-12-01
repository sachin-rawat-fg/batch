package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DMLQuery {

	
	public String recordExists(String tableName,String searchField,String value,String getField)
	{
		String getVal = null;
		String selectQuery = "SELECT "+getField+" FROM "+tableName+" WHERE "+ searchField+" = '"+value+"'";
		ConnectionDB connect = ConnectionDB.getInstance();
	
		try{
		Statement stmt = connect.getDBConnection().createStatement();
		ResultSet rs =  stmt.executeQuery(selectQuery);
		while(rs.next())
		{
			getVal = rs.getString(getField);
		}
		rs.close();
		stmt.close();
		}
		catch(Exception e){e.printStackTrace();}
		return getVal;
	}
	
	public int updateRecord(String tableName,String searchKey,String searchKeyValue, String updateFieldName,String updateValue)
	{
		ConnectionDB connect = ConnectionDB.getInstance();
		int numberOfUpdate=0;
		try{
			String updateQuery = "UPDATE "+tableName+" SET "+updateFieldName+" =? WHERE "+searchKey+" = ?";
			PreparedStatement ppts = connect.getDBConnection().prepareStatement(updateQuery);
			ppts.setString(1, updateValue);
			ppts.setString(2, searchKeyValue);
			numberOfUpdate = ppts.executeUpdate();
			ppts.close();
		}
		catch(Exception e){e.printStackTrace();};
		return numberOfUpdate;
	}
	public String generateLogReport(String tableName,String fieldNames[], String searchField,String startsWith)
	{
		StringBuilder reportGenerator = new  StringBuilder();
		StringBuilder queryBuilder= new StringBuilder("SELECT ");
		for(String field:fieldNames)
		{
			queryBuilder.append(field+",");
			reportGenerator.append(field+",");
		}
		queryBuilder.deleteCharAt(queryBuilder.length()-1);
		reportGenerator.deleteCharAt(reportGenerator.length()-1);
		reportGenerator.append("\n");
		queryBuilder.append(" FROM "+tableName+" WHERE "+searchField+" LIKE '"+startsWith+"%'");
	
		try{
		ConnectionDB connect = ConnectionDB.getInstance();
		Statement stmt = connect.getDBConnection().createStatement(); 
		System.out.println(queryBuilder.toString());
		ResultSet rs = stmt.executeQuery(queryBuilder.toString());

		while(rs.next())
		{
			for(String fields:fieldNames)
			{
				reportGenerator.append(rs.getString(fields)+",");
			}
			reportGenerator.deleteCharAt(reportGenerator.length()-1);
			reportGenerator.append("\n");
		}
		rs.close();
		stmt.close();
		reportGenerator.deleteCharAt(reportGenerator.length()-1);
		return reportGenerator.toString();
		
		}
		catch(Exception e){e.printStackTrace();}
		return null;
	}
	public void insertInDatabase(String tableName,
			String customerID,
			String partyNumber,
			String queryType,
			String status,
			String status1,
			String status2,
			String status3,
			String date,
			int numberOfRetry)
	{
		ConnectionDB connect = ConnectionDB.getInstance();
		
		String insertTableSQL = "INSERT INTO CUSTOMER VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ppt = connect.getDBConnection().prepareStatement(insertTableSQL);
			ppt.setString(1, customerID);
			ppt.setString(2, partyNumber);
			ppt.setString(3, queryType);
			ppt.setString(4, status);
			ppt.setString(5, status1);
			ppt.setString(6, status2);
			ppt.setString(7, status3);
			ppt.setString(8, date);
			ppt.setInt(9, numberOfRetry);
			
			ppt.executeUpdate();
			ppt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
				
	}
}
