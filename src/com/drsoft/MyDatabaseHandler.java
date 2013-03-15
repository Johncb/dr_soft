/*
 * Title : Database Handler
 * Description : Creates and handles connections to the database
 * Author : Glen Holmes
 * Date :05 March 2013  
 */

package com.drsoft;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MyDatabaseHandler {
	Statement stat;
	
	//Creates databaseConnection
	public MyDatabaseHandler() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/surgery");
			Connection conn = ds.getConnection();
			stat = conn.createStatement();
		} catch (Exception e) {
		}
	}
	
	//Used for Queries expecting results
	//Select Statements
	//e.g. ResultSet rs = dbHandler.retQuery("select * from table");
	public ResultSet retQuery(String query) throws SQLException {
		ResultSet rs = stat.executeQuery(query);
		return rs;
	}
	
	//Used for Queries just carrying out query
	//Insert, Delete, etc...
	public void nonRetQuery(String query) throws SQLException {
		stat.executeQuery(query);
	}
}
