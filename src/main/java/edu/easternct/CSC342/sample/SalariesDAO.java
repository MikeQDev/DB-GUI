package edu.easternct.CSC342.sample;

import java.math.BigDecimal;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalariesDAO {
	
    public void createSalaries(Salaries salaries) throws SQLException {
        Connection con = null;
        PreparedStatement ps= null;        
        System.out.println("Salary to be Inserted \n");
        System.out.println(salaries.toString());
        
		try {            
        	con = DBConnect.getConnection();
            ps=con.prepareStatement("insert into CSC342.employee_salary (employee_id, salary, salary_start_date, salary_end_date) values(?,?,?,?)");
            ps.setBigDecimal(1, salaries.getEmployeeId());
            ps.setBigDecimal(2, salaries.getSalary());
            ps.setTimestamp(3, salaries.getStartDate());
            ps.setTimestamp(4, salaries.getEndDate());            
            ps.executeUpdate();
            System.out.println("Addition Success");
        
        }
        catch(SQLException e) {
            System.out.println("Error in Create Salary" + e.getSQLState());
            System.out.println("/nError Code: " + e.getErrorCode());
            System.out.println("/nMessage: " + e.getMessage());
            System.exit( 1 );
        }
        catch(Exception e) {
            System.out.println("unknown Error in Create Salary");
            System.out.println("/nMessage: " + e.getMessage());
            System.exit( 1 );
        }
		finally {
			if (con != null)
				System.out.println("closing Salary create statement \n");
			    ps.close();				
		}        
    }

public BigDecimal findMaxEmployeeId() throws SQLException {
    BigDecimal maxEmployeeId = new BigDecimal(0);
    Connection con = null;
    PreparedStatement ps= null;
    ResultSet rs=null;    
    try {
    	con = DBConnect.getConnection();
    	ps=con.prepareStatement("select max(employee_id) from CSC342.employee_salary");
        rs=ps.executeQuery();
        while(rs.next()) {
            maxEmployeeId = rs.getBigDecimal(1);
            System.out.println("Get Max Employee Id Success ");      
        }
    }
    catch(SQLException e) {
        System.out.println("Error in get max employee access" + e.getSQLState());
        System.out.println("/nError Code: " + e.getErrorCode());
        System.out.println("/nMessage: " + e.getMessage());
        System.exit( 1 );
    }
    catch(Exception e) {
        System.out.println("unknown Error in get max employee");
        System.out.println("/nMessage: " + e.getMessage());
        System.exit( 1 );
    }
    finally {
		if (con != null)
			System.out.println("closing Employee connection \n");
			rs.close();
			ps.close();
	}
    return maxEmployeeId;
}

public Salaries viewEmployee(BigDecimal employeeId) throws SQLException {
		ResultSet rs=null;
        Salaries outSalaries = new Salaries();
        Connection con = null;
        PreparedStatement ps=null;
        try {    
        	con = DBConnect.getConnection();
        	ps=con.prepareStatement("select employee_id, salary, salary_start_date, salary_end_date" +
            		"from CSC342.employee_salary" +
            		"where employee_id =?");
            ps.setBigDecimal(1, employeeId);

            rs=ps.executeQuery();
            while(rs.next()) {
                outSalaries.setEmployeeId(rs.getBigDecimal(1));
                outSalaries.setSalary(rs.getBigDecimal(2));
                outSalaries.setStartDate(rs.getTimestamp(3));
                outSalaries.setEndDate(rs.getTimestamp(4));
                
                System.out.println("View Employee Success");
            }
        }
        catch(SQLException e) {
            System.out.println("Error in Employee view access" + e.getSQLState());
            System.out.println("/nError Code: " + e.getErrorCode());
            System.out.println("/nMessage: " + e.getMessage());
            System.exit( 1 );
        }
        catch(Exception e) {
            System.out.println("unknown Error in Employee view access");
            System.out.println("/nMessage: " + e.getMessage());
            System.exit( 1 );
        }
        finally {
    		if (con != null)
    			System.out.println("closing Employee connection \n");
				rs.close();
				ps.close();	
    	}
        return outSalaries;
    }
	public void updateSalaries(Salaries empl) throws SQLException {
        System.out.println("Employee to be Updated \n");
		System.out.println(empl.toString());
		Connection con = null;
    	PreparedStatement ps= null;
    	
        try {
        	con = DBConnect.getConnection();
            ps=con.prepareStatement("update CSC342.employee_salary set salary = ?," +
        	"salary_start_date = ?, salary_end_date = ?" +
            		"where employee_id = ?");
            ps.setBigDecimal(1, empl.getEmployeeId());
            ps.setBigDecimal(2, empl.getSalary());
            ps.setTimestamp(3, empl.getStartDate());
            ps.setTimestamp(4, empl.getEndDate());
            ps.executeQuery();
            System.out.println("updated");
        }
        catch(SQLException e) {
	            System.out.println("Error in Salary Update" + e.getSQLState());
	            System.out.println("/nError Code: " + e.getErrorCode());
	            System.out.println("/nMessage: " + e.getMessage());
	            System.exit( 1 );
	        }
	    catch(Exception e) {
	            System.out.println("unknown Error in Salary Update");
	            System.out.println("/nMessage: " + e.getMessage());
	            System.exit( 1 );
        }
        finally {
    		if (con != null)
    			System.out.println("closing Salary connection \n");
				ps.close();
    	}
    }
	public void deleteSalary(BigDecimal employeeId) throws SQLException {
		System.out.println("Employee to be Deleted \n");
		System.out.println("Employee Id = " + employeeId + "\n");
		Connection con = null;
    	PreparedStatement ps=null;
    	
        try {
        	con = DBConnect.getConnection();
            ps=con.prepareStatement("delete CSC342.employee_salary where employee_id=?");
            ps.setBigDecimal(1, employeeId);
            ps.executeQuery();
            System.out.println("deleted");
        }
        catch(SQLException e) {
            System.out.println("Error in Salary Delete" + e.getSQLState());
            System.out.println("/nError Code: " + e.getErrorCode());
            System.out.println("/nMessage: " + e.getMessage());
            System.exit( 1 );
        }
        catch(Exception e) {
            System.out.println("unknown Error in Salary delete");
            System.out.println("/nMessage: " + e.getMessage());
            System.exit( 1 );
        }
        finally {
    		if (con != null)
    			System.out.println("closing Salary connection \n");
				ps.close();				
    	}
    }

    	public void countSalaries() throws SQLException {        
        	Connection con = null;
        	PreparedStatement ps= null;
        	ResultSet rs = null;
        	String sql1 = "Select count(*) from CSC342.employee_salary s inner join CSC342.Employee e " + 
        				  " on (s.employee_id = e.employee_id)";
        	
            try {        
            	con = DBConnect.getConnection();
                ps=con.prepareStatement(sql1);
                int salaryCt = 0;                
                rs = ps.executeQuery();
                while(rs.next()) {
                    salaryCt = rs.getInt(1);
                }    
                System.out.println("countSalaries success " + salaryCt);
            }
            catch(SQLException e) {
                System.out.println("Error in countSalaries" + e.getSQLState());
                System.out.println("/nError Code: " + e.getErrorCode());
                System.out.println("/nMessage: " + e.getMessage());
                System.exit( 1 );
            }
            catch(Exception e) {
                System.out.println("unknown Error in countSalaries");
                System.out.println("/nMessage: " + e.getMessage());
                System.exit( 1 );
            }
            finally {
        		if (con != null)
        			System.out.println("closing count objects \n");
        		    rs.close();
        		    ps.close();    				    				
        	}        
    }

    	public void saveSalaries(List<Salaries> salary) throws SQLException {        
        	Connection con = null;        	
        	String sql1 = "Select count(*) as salary_count from CSC342.employee_salary  WHERE employee_id = ?";
        	PreparedStatement ps = null;
        	ResultSet rs = null;

        	try {    
        	    con = DBConnect.getConnection();
                ps=con.prepareStatement(sql1);

        	    for (Iterator<Salaries> it = salary.iterator(); it.hasNext();) {
        		      Salaries testSalary = it.next();
                      ps.setBigDecimal(1,testSalary.getEmployeeId());  
                      rs = ps.executeQuery();
                      while(rs.next()) {
                        if (rs.getInt(1) == 1)
                        	updateSalaries(testSalary);
                        else
                        if (rs.getInt(1) == 0)
                        	createSalaries(testSalary);
                        else
                        	throw new RuntimeException("More than one employee has Employee Id");
                      }
        	      }                
             }
             catch(SQLException e) {
                 System.out.println("Error in saveSalary" + e.getSQLState());
                 System.out.println("/nError Code: " + e.getErrorCode());
                 System.out.println("/nMessage: " + e.getMessage());
                 System.exit( 1 );
              }
             catch(Exception e) {
                 System.out.println("unknown Error in saveSalary");
                 System.out.println("/nMessage: " + e.getMessage());
                 System.exit( 1 );
              }
             finally {
                  rs.close();
            	  ps.close();
        	  }
        
        	}
}