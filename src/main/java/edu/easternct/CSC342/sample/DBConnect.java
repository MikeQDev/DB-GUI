package edu.easternct.CSC342.sample;

import java.sql.*;

public class DBConnect {
	
	private static String urlLead = "jdbc:oracle:thin:@//";
	private static String dbURL = null;
	private static String dbDriverName = "oracle.jdbc.OracleDriver";
	private static String hostName;
	private static String port;
	private static String sid;
	private static String id;
	private static String pwrd;
	private static Connection conn = null;
	private static boolean driverLoaded = false;
	private static boolean connected = false;
	
	private  DBConnect()
    {
        
    return;
    }
   
	public static void loadDriver()
    {
    	
    	try
    {
        Class.forName(dbDriverName);
    }
    catch(Exception e)
    {
        System.out.println("Driver could not be loaded");
        System.out.println("/nMessage: " + e.getMessage());
        e.printStackTrace();
    }
    return;
    }
	
	public static Connection getConnection(String inHostname, String inPort, String inSid, String inId, String inPwrd)
    {
    	if (conn != null)
    		return conn;
    	
    	try
        {
          if (!driverLoaded) {
        	loadDriver();
            driverLoaded=true;
         }
        
         setHostName(inHostname);
         setPort(inPort);
         setSid(inSid);
         setId(inId);
         setPwrd(inPwrd);
         setDbURL();
         
         conn=DriverManager.getConnection(dbURL, id, pwrd);
         conn.setAutoCommit(false);
         connected = true;
        
         System.out.println("Connected");
       }
       catch(SQLException e)
       {
    	 System.out.println("DB Url = " + getDbURL());
    	 System.out.println("Id = " + getId());
    	 System.out.println("Pwd = " + getPwrd());
    	 System.out.println("Error in Create Connnection" + e.getSQLState());
         System.out.println("/nError Code: " + e.getErrorCode());
         System.out.println("/nMessage: " + e.getMessage());
       }	
         catch(Exception e)
       {
          System.out.println("Not Connected");
          e.printStackTrace();
       }
         return conn;
    }

	public static Connection getConnection()
    {
    	
		try {
		       boolean check = conn.isValid(5);
	           if ((check) && (conn != null)) 
		        {
	        	   System.out.println("Connected");
	        	   return conn;
		        }   
        }
       catch(SQLException e)
       {
    	 
    	 System.out.println("Error in Create Connnection" + e.getSQLState());
         System.out.println("/nError Code: " + e.getErrorCode());
         System.out.println("/nMessage: " + e.getMessage());
       }	
         catch(Exception e)
       {
          System.out.println("Not Connected");
          e.printStackTrace();
       }
         return conn;
    }

	public static String getDbURL() {
		return dbURL;
	}

	public static void setDbURL() {
		dbURL = urlLead + hostName + ':' + port + '/' + sid;
	}

	public static String getDbDriverName() {
		return dbDriverName;
	}

	public static void setDbDriverName(String dbDriverName) {
		dbDriverName = dbDriverName;
	}

	public static String getHostName() {
		return hostName;
	}

	public static void setHostName(String inHostName) {
		hostName = inHostName;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String inPort) {
		port = inPort;
	}

	public static String getSid() {
		return sid;
	}

	public static void setSid(String inSid) {
		sid = inSid;
	}


	public static String getId() {
		return id;
	}

	public static void setId(String inId) {
		id = inId;
	}

	public static String getPwrd() {
		return pwrd;
	}

	public static void setPwrd(String inPwrd) {
		pwrd = inPwrd;
	}
	
	

}
