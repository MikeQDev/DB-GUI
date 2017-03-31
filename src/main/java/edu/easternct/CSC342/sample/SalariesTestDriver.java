package edu.easternct.CSC342.sample;
import java.sql.*;
import java.math.*;
import java.util.*;

public class SalariesTestDriver {
	
	List<Salaries> contacts = new ArrayList<Salaries>();
	String hostname;
	String port;
	String sid;
	String id;
	String pwrd;

	public String getHostname() {
		return hostname;
	}
	public void setHostname(String inHostname) {
		this.hostname = inHostname;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String inPort) {
		this.port = inPort;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String inSid) {
		this.sid = inSid;
	}
	public String getId() {
		return id;
	}
	public void setId(String inId) {
		this.id = inId;
	}
	public String getPwrd() {
		return pwrd;
	}
	public void setPwrd(String inPwrd) {
		this.pwrd = inPwrd;
	}
	public void testCreate() throws SQLException, Exception {
		BigDecimal increment = new BigDecimal(1);
		BigDecimal nextEmployeeId = new BigDecimal(1);
		BigDecimal salaryAmt = new BigDecimal(75000);
		Timestamp tstamp;
		Calendar tempCalendar;
		Connection conn;
 		conn = DBConnect.getConnection(hostname, port, sid, id, pwrd);
		SalariesDAO salariesDAO = new SalariesDAO();
		//nextEmployeeId  = salariesDAO.findMaxEmployeeId();
		nextEmployeeId = nextEmployeeId.add(increment);
	    Salaries salary = new Salaries(nextEmployeeId);
	    salary.setSalary(salaryAmt);
	    /* set time stamp for start date */
	    tempCalendar = GregorianCalendar.getInstance();
	    tempCalendar.set(Calendar.DAY_OF_MONTH, 18);
	    tempCalendar.set(Calendar.MONTH, 5);
	    tempCalendar.set(Calendar.YEAR, 2013);
	    tstamp = new Timestamp(tempCalendar.getTimeInMillis());
	    salary.setStartDate(tstamp);
	    /* set time stamp for end date */
	    tempCalendar = GregorianCalendar.getInstance();
	    tempCalendar.set(Calendar.DAY_OF_MONTH, 30);
	    tempCalendar.set(Calendar.MONTH, 3);
	    tempCalendar.set(Calendar.YEAR, 2017);
	    tstamp = new Timestamp(tempCalendar.getTimeInMillis());
	    salary.setEndDate(tstamp);
	    contacts.add(salary);
	    
	    salaryAmt = new BigDecimal(2);
	    Salaries salary2 = new Salaries(nextEmployeeId.add(increment));
	    salary2.setSalary(salaryAmt);
	    /* set time stamp for start date */
	    tempCalendar = GregorianCalendar.getInstance();
	    tempCalendar.set(Calendar.DAY_OF_MONTH, 14);
	    tempCalendar.set(Calendar.MONTH, 7);
	    tempCalendar.set(Calendar.YEAR, 2016);
	    tstamp = new Timestamp(tempCalendar.getTimeInMillis());
	    salary2.setStartDate(tstamp);
	    /* set time stamp for end date */
	    tempCalendar = GregorianCalendar.getInstance();
	    tempCalendar.set(Calendar.DAY_OF_MONTH, 30);
	    tempCalendar.set(Calendar.MONTH, 3);
	    tempCalendar.set(Calendar.YEAR, 2017);
	    tstamp = new Timestamp(tempCalendar.getTimeInMillis());
	    salary2.setEndDate(tstamp);
	    contacts.add(salary2);
	    salariesDAO.saveSalaries(contacts);
	    conn.commit();
//	    Reports2DAO skills = new Reports2DAO();
//	    StringBuffer skillsReport = skills.getSkillSummary();
//	    System.out.println(skillsReport);
	    	    
	}

	public void printContacts() throws Exception {		
		Iterator it = contacts.iterator();
		int salariesNumber = 0;
        while(it.hasNext()) {
			salariesNumber++;
			Salaries salary =(Salaries)it.next();
			System.out.println(" Salary " + salariesNumber + " = " + salary.toString());
		}	
	}
	
	public static void main(String[] args) {
		/*  This command instantiates a class instance passing the connection object.  */   
	    SalariesTestDriver testSalary = new SalariesTestDriver();
        try {        	   
			   /*  call purge method to delete all rows from new summary table   */   	    	   
	    	   testSalary.setHostname("localhost");
	    	   testSalary.setPort("1521");
	    	   testSalary.setSid("CSC342");
	    	   testSalary.setId("team00");
	    	   testSalary.setPwrd("team00");	    	    
	    	   testSalary.testCreate();
	    	   testSalary.printContacts();
	    }
	    catch (Exception ex) {
	    	 System.out.println("Error in testing");
	    	 System.out.println(ex.getMessage());
         	 ex.printStackTrace();
         	 System.exit( 1 );
	    }
	}
}