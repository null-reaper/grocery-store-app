package cs425_databases;

import java.sql.*;
import java.util.ArrayList;

public class SQLInterface {
	
	private static final String url = "jdbc:postgresql://localhost/grocerystore";
    private static final String user = "postgres";
    private static final String password = "RS2381998";
    
    // Code to establish connection with the database
    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
        }

        return conn;
    }
    
    // Code to execute select operations in SQL
    public static ArrayList<ArrayList<Object>> sqlExecute(String code) {
    	
    	int numCol = 0;
    	
    	ArrayList<Object> columnNames = new ArrayList<Object>();
    	ArrayList<String> columnTypes = new ArrayList<String>();
    	ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
    	ArrayList<Object> rowData = new ArrayList<Object>();
    	
        ResultSet rs = null;
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();) {
        	rs = stmt.executeQuery(code);
        	numCol = rs.getMetaData().getColumnCount();
        	
        	for(int i = 1; i < numCol+1; i++) {
        		columnNames.add(rs.getMetaData().getColumnName(i));
        		columnTypes.add(rs.getMetaData().getColumnTypeName(i));
        	}
        	data.add(columnNames);
   
        	while(rs.next()) {
        		rowData = new ArrayList<Object>();
        		for (int i = 1; i < numCol+1; i++) {
        			rowData.add(rs.getString(i));
        		}
        		data.add(rowData);
        	}
        	
        	
        } 
        catch (SQLException ex) {
        }
        return data;
    }
    
    // Code to execute insert, delete and update operations in SQL
    public static int sqlUpdate(String code) {
    	
		int update = 0;
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();) {
        	update = stmt.executeUpdate(code);
        } 
        catch (SQLException ex) {
        }
        
        return update;
    }
 
}
