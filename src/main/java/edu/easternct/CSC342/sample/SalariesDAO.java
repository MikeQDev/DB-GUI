package edu.easternct.CSC342.sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalariesDAO {
	private List<Salaries> sal = new ArrayList<Salaries>();

	public void report(File f) {

		String reportQuery = "select employee_id, salary_start_date, salary_end_date, salary, case when salary_end_date is null then 'N' else 'Y' end as retired from CSC342.employee_salary order by retired asc, salary desc";
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement(reportQuery);

			rs = ps.executeQuery();

			BufferedWriter bW = new BufferedWriter(new FileWriter(f));

			bW.write("EmployeeID,EmployeeStartDate,EmployeeEndDate,Salary" + System.lineSeparator());

			while (rs.next()) {
				// "SELECT P.PRODUCT_ID, SUM(ORDERED_QUANTITY) AS TOTAL_ORDERED,
				// "+ "PRODUCT_DESCRIPTION "+
				int empId = rs.getInt(1);
				Date empStartDate = rs.getDate(2);
				Date empEndDate = rs.getDate(3);
				int salary = rs.getInt(4);
				bW.write(empId + "," + empStartDate + "," + empEndDate + "," + salary + System.lineSeparator());
			}
			bW.close();
		} catch (SQLException e) {
			System.out.println("Error in Reporting " + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Reporting");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Reporting connection \n");
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Salaries> getAllSalaries() {
		sal.clear();
		ResultSet rs = null;
		Salaries outSalaries = new Salaries();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBConnect.getConnection();
			ps = con.prepareStatement("select s.employee_id, s.salary, s.salary_start_date, s.salary_end_date"
					+ " from CSC342.employee_salary s order by s.employee_id asc");
			rs = ps.executeQuery();
			while (rs.next()) {
				outSalaries = new Salaries();
				outSalaries.setEmployeeId(rs.getBigDecimal(1));
				outSalaries.setSalary(rs.getBigDecimal(2));
				outSalaries.setStartDate(rs.getTimestamp(3));
				outSalaries.setEndDate(rs.getTimestamp(4));
				sal.add(outSalaries);
			}
		} catch (SQLException e) {
			System.out.println("Error in Salary view access" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Salary view access");
			System.out.println("/nMessage: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Salary connection \n");
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sal;
	}

	public void createSalaries(Salaries salaries) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		System.out.println("Salary to be Inserted \n");
		System.out.println(salaries.toString());

		try {
			con = DBConnect.getConnection();
			ps = con.prepareStatement(
					"insert into CSC342.employee_salary (employee_id, salary, salary_start_date, salary_end_date) values(?,?,?,?)");
			ps.setBigDecimal(1, salaries.getEmployeeId());
			ps.setBigDecimal(2, salaries.getSalary());
			ps.setTimestamp(3, salaries.getStartDate());
			ps.setTimestamp(4, salaries.getEndDate());
			ps.executeUpdate();
			System.out.println("Addition Success");

		} catch (SQLException e) {
			System.out.println("Error in Create Salary" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Create Salary");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Salary create statement \n");
			ps.close();
		}
	}

	public BigDecimal findMaxEmployeeId() throws SQLException {
		BigDecimal maxEmployeeId = new BigDecimal(0);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBConnect.getConnection();
			ps = con.prepareStatement("select max(employee_id) from CSC342.employee_salary");
			rs = ps.executeQuery();
			while (rs.next()) {
				maxEmployeeId = rs.getBigDecimal(1);
				System.out.println("Get Max Employee Id Success ");
			}
		} catch (SQLException e) {
			System.out.println("Error in get max employee access" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in get max employee");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Employee connection \n");
			rs.close();
			ps.close();
		}
		return maxEmployeeId;
	}

	public Salaries viewEmployee(BigDecimal employeeId) throws SQLException {
		ResultSet rs = null;
		Salaries outSalaries = new Salaries();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBConnect.getConnection();
			ps = con.prepareStatement("select employee_id, salary, salary_start_date, salary_end_date"
					+ "from CSC342.employee_salary" + "where employee_id =?");
			ps.setBigDecimal(1, employeeId);
			rs = ps.executeQuery();
			while (rs.next()) {
				outSalaries.setEmployeeId(rs.getBigDecimal(1));
				outSalaries.setSalary(rs.getBigDecimal(2));
				outSalaries.setStartDate(rs.getTimestamp(3));
				outSalaries.setEndDate(rs.getTimestamp(4));
				System.out.println("View Employee Success");
			}
		} catch (SQLException e) {
			System.out.println("Error in Employee view access" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Employee view access");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
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
		PreparedStatement ps = null;
		try {
			con = DBConnect.getConnection();
			ps = con.prepareStatement("update CSC342.employee_salary set salary = ?,"
					+ "salary_start_date = ?, salary_end_date = ?" + "where employee_id = ?");
			ps.setBigDecimal(1, empl.getSalary());
			ps.setTimestamp(2, empl.getStartDate());
			ps.setTimestamp(3, empl.getEndDate());
			ps.setBigDecimal(4, empl.getEmployeeId());
			ps.executeQuery();
			System.out.println("updated");
		} catch (SQLException e) {
			System.out.println("Error in Salary Update" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Salary Update");
			System.out.println("/nMessage: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Salary connection \n");
			ps.close();
		}
	}

	public void deleteSalary(BigDecimal employeeId) throws SQLException {
		System.out.println("Employee to be Deleted \n");
		System.out.println("Employee Id = " + employeeId + "\n");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBConnect.getConnection();
			ps = con.prepareStatement("delete CSC342.employee_salary where employee_id=?");
			ps.setBigDecimal(1, employeeId);
			ps.executeQuery();
			System.out.println("deleted");
		} catch (SQLException e) {
			System.out.println("Error in Salary Delete" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Salary delete");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Salary connection \n");
			ps.close();
		}
	}

	public void countSalaries() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql1 = "Select count(*) from CSC342.employee_salary s inner join CSC342.Employee e "
				+ " on (s.employee_id = e.employee_id)";
		try {
			con = DBConnect.getConnection();
			ps = con.prepareStatement(sql1);
			int salaryCt = 0;
			rs = ps.executeQuery();
			while (rs.next()) {
				salaryCt = rs.getInt(1);
			}
			System.out.println("countSalaries success " + salaryCt);
		} catch (SQLException e) {
			System.out.println("Error in countSalaries" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in countSalaries");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing count objects \n");
			rs.close();
			ps.close();
		}
	}

	public boolean hasCorrespondingEmployeeId(List<Salaries> salary) throws SQLException {
		// ensure no FK issues
		Connection conx = null;
		String sql1x = "Select employee_id from CSC342.employee";
		PreparedStatement psx = null;
		ResultSet rsx = null;

		List<BigDecimal> tempEmpIdList = new ArrayList<BigDecimal>();

		try {
			conx = DBConnect.getConnection();
			psx = conx.prepareStatement(sql1x);
			rsx = psx.executeQuery();
			while (rsx.next()) {
				tempEmpIdList.add(rsx.getBigDecimal(1));
			}
			for (Salaries s : salary) {
				if (!tempEmpIdList.contains(s.getEmployeeId())) {
					return false;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error in saveSalary" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			conx.commit();
			rsx.close();
			psx.close();
			return false;
		} catch (Exception e) {
			System.out.println("unknown Error in saveSalary");
			System.out.println("/nMessage: " + e.getMessage());
			conx.commit();
			rsx.close();
			psx.close();
			return false;
		} finally {
			conx.commit();
			rsx.close();
			psx.close();
		}
		return true;
	}

	public void saveSalaries(List<Salaries> salary) throws SQLException {
		Connection con = null;
		String sql1 = "Select count(*) as salary_count from CSC342.employee_salary  WHERE employee_id = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBConnect.getConnection();
			ps = con.prepareStatement(sql1);
			for (Iterator<Salaries> it = salary.iterator(); it.hasNext();) {
				Salaries testSalary = it.next();
				ps.setBigDecimal(1, testSalary.getEmployeeId());
				rs = ps.executeQuery();
				while (rs.next()) {
					if (rs.getInt(1) == 1)
						updateSalaries(testSalary);
					else if (rs.getInt(1) == 0)
						createSalaries(testSalary);
					else
						throw new RuntimeException("More than one employee has Employee Id");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error in saveSalary" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in saveSalary");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			con.commit();
			rs.close();
			ps.close();
		}
	}
}