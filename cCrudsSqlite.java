// MySQL database

import java.sql.*;

public class cCrudsSqlite implements iCruds 
{
    Connection conn;
    Statement selectStatement;
    PreparedStatement insertStatement;
    String tableName;
    int columnCount;

    public cCrudsSqlite() throws SQLException 
    {
        tableName = "Item";
        conn = DriverManager.getConnection("jdbc:sqlite:D:\\Training\\JAVA\\dbAbhi");  
    }

    public cCrudsSqlite(String pDatabaseName, String pTableName) throws SQLException 
    {
        tableName = pTableName;
        conn = DriverManager.getConnection("jdbc:sqlite:" +  pDatabaseName); 
    }

    public cCrudsSqlite(String pHost, String pDatabaseName, String pUsername, String pPassword, String pTableName) throws SQLException 
    {
        tableName = pTableName;
        conn = DriverManager.getConnection("jdbc:sqlite:" +  pDatabaseName);  
    }

    public cUserInterface[] fetchAll() throws Exception 
    {
        selectStatement = conn.createStatement();
        ResultSet queryResult = selectStatement.executeQuery("select * from " + tableName);
        return convertToObjectArray(queryResult);
    }

    int getRowCount() throws SQLException 
    {
        int rowCount = 0;
        selectStatement = conn.createStatement();
        ResultSet resultSet = selectStatement.executeQuery("SELECT COUNT(*) FROM " + tableName);
        if (resultSet.next()) 
        {
            rowCount = resultSet.getInt(1);
        }
        return rowCount;
    }

    cUserInterface[] convertToObjectArray(ResultSet queryResult) throws Exception 
    {
        int rowCount = getRowCount();
        cUserInterface[] records = new cUserInterface[rowCount];
        int rowCounter = 0;
        while (queryResult.next()) 
        {
            records[rowCounter] = new cUserInterface();
            for (int column = 0; column < records[rowCounter].fieldsCount; column++)
            {
	            records[rowCounter].fieldValues[column] = queryResult.getString(column + 1);
            }
            rowCounter++;
        }
        return records;
    }

    public int addRecord(cUserInterface record) throws Exception
    {
    	String insertQuery = "INSERT INTO " + tableName + "(";
    	String subInsertQueryValuesPart = "Values(";
    	for (int columnCounter = 0; columnCounter < record.fieldsCount; columnCounter++)
    	{
    		insertQuery = insertQuery + record.fieldNames[columnCounter];
    		subInsertQueryValuesPart = subInsertQueryValuesPart + "?";

    		if (columnCounter != record.fieldsCount - 1)
    		{
    			insertQuery = insertQuery + ", ";
    			subInsertQueryValuesPart = subInsertQueryValuesPart + ", ";
    		}
    	}
    	insertQuery = insertQuery + ") " + subInsertQueryValuesPart + ")";

        insertStatement = conn.prepareStatement(insertQuery);
        for (int column = 0; column < record.fieldsCount; column++)
        {
	        insertStatement.setString(column + 1, record.fieldValues[column]);
        }
        int rowsAffected = insertStatement.executeUpdate();
	    if (rowsAffected > 0) 
	    {
	    	return 1;
	    } 
	    else 
	    {
	    	return 0;
	    }
	}
}
