package edu.easternct.CSC342.sample;
import java.sql.*;
import java.math.*;
import java.util.*;



public class SkillTestDriver {
	
	List<Skill> skills = new ArrayList<Skill>();
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
		
		
		//BigDecimal increment = new BigDecimal(1);
		//BigDecimal nextSkillId = new BigDecimal(1);  
		String nextSkillId = "QC3";  
		//Timestamp tstamp;
		//Calendar tempCalendar;
		//Address address;
		Connection conn;
		
 		conn = DBConnect.getConnection(hostname, port, sid, id, pwrd);
		SkillDAO skillDAO = new SkillDAO();
		//nextSkillId  = skillDAO.findMaxPersonId();
		//nextSkillId = nextSkillId.add(increment);
	    Skill sk = new Skill(nextSkillId);
	    sk.setSkillDescription("C++");
	    //sk.setSkillId("QCo");
	    skills.add(sk);
	    skillDAO.saveSkill(skills);
	    conn.commit();
//	    Reports2DAO skills = new Reports2DAO();
//	    StringBuffer skillsReport = skills.getSkillSummary();
//	    System.out.println(skillsReport);
	    	    
	}

	public void printSkills() throws Exception {
		
		
		Iterator it=skills.iterator();
		int skillNumber = 0;
        while(it.hasNext())

		{
			skillNumber++;
			Skill sk =(Skill)it.next();

			System.out.println(" Skill " + skillNumber + " = " + sk.toString());
		}
	
	}
	
	public static void main(String[] args)  
	{
		
	    /*  This command instantiates a class instance passing the connection object.  */   
	    SkillTestDriver testSkill = new SkillTestDriver();

        try {
        	   
			   /*  call purge method to delete all rows from new summary table   */    
	    	   
	    	   testSkill.setHostname(args[0]);
	    	   testSkill.setPort(args[1]);
	    	   testSkill.setSid(args[2]);
	    	   testSkill.setId(args[3]);
	    	   testSkill.setPwrd(args[4]);
	    	    
	    	   testSkill.testCreate();
	    	   testSkill.printSkills();
	    }
	    catch (Exception ex) {
	    	 System.out.println("Error in testing");
	    	 System.out.println(ex.getMessage());
         	 ex.printStackTrace();
         	 System.exit( 1 );
	    }
	}

	}
