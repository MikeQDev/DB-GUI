package edu.easternct.CSC342.sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SkillDAO {

	private List<Skill> sL = new ArrayList<Skill>();

	public List<Skill> getAllSkills() {
		sL.clear();
		ResultSet rs = null;
		Skill outSkill = new Skill();
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement(
					"select s.skill_id, s.skill_description" + " from CSC342.skill s order by s.skill_id asc");

			rs = ps.executeQuery();
			while (rs.next()) {
				outSkill = new Skill();
				outSkill.setSkillId(rs.getString(1));
				outSkill.setSkillDescription(rs.getString(2));
				sL.add(outSkill);
			}
		} catch (SQLException e) {
			System.out.println("Error in Skill view access" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Skill view access");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Skill connection \n");
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sL;
	}

	public void createSkill(Skill skill) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		System.out.println("Skill to be Inserted \n");
		System.out.println(skill.toString());

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement("insert into CSC342.skill (Skill_id, Skill_Description) values(?,?)");
			ps.setString(1, skill.getSkillId());
			ps.setString(2, skill.getSkillDescription());

			ps.executeUpdate();
			con.commit();
			System.out.println("Addition Success");

		} catch (SQLException e) {
			System.out.println("Error in Create Skill" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());

			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Create Person");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Skill create statement \n");
			ps.close();

		}

	}

	public Skill viewSkill(String skill_Id) throws SQLException {

		ResultSet rs = null;
		Skill outSkill = new Skill();
		// Address outAddress = new Address();
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement(
					"select s.skill_id, s.skill_description" + "from CSC342.skill s" + "where s.skill_id =?");
			// ps.setBigDecimal(1,personId);

			rs = ps.executeQuery();
			while (rs.next()) {
				outSkill.setSkillId(rs.getString(1));
				outSkill.setSkillDescription(rs.getString(2));

				/*
				 * don't need to set parent, must be done when you instantiate
				 * the ee class (must setup past classes correctly.
				 */

				System.out.println("View Skill Success");
			}
		} catch (SQLException e) {
			System.out.println("Error in Skill view access" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Person view access");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Skill connection \n");
			rs.close();
			ps.close();
		}
		return outSkill;
	}

	public void updateSkill(Skill sk) throws SQLException {

		System.out.println("Skill to be Updated \n");
		System.out.println(sk.toString());
		Connection con = null;
		PreparedStatement ps = null;
		System.out.println("update");
		try {

			con = DBConnect.getConnection();

			ps = con.prepareStatement("update CSC342.skill set skill_description = ?" + "where skill_id = ?");

			ps.setString(1, sk.getSkillDescription());
			ps.setString(2, sk.getSkillId());

			ps.executeQuery();
			con.commit();
			System.out.println("updated");
		} catch (SQLException e) {
			System.out.println("Error in Skill Update" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			e.printStackTrace();

			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Skill Update");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Skill connection \n");
			ps.close();
		}
	}

	public void deleteSkill(String skill_Id) throws SQLException {

		System.out.println("Skill to be Deleted \n");
		System.out.println("Skill Id = " + skill_Id + "\n");

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement("delete CSC342.skill where skill_id=?");
			ps.setString(1, skill_Id);
			ps.executeQuery();
			con.commit();
			System.out.println("deleted");
		} catch (SQLException e) {
			System.out.println("Error in Skill Delete" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Skill delete");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Skill connection \n");
			ps.close();

		}
	}

	/*
	 * public void countSkill() throws SQLException {
	 * 
	 * Connection con = null; PreparedStatement ps= null; ResultSet rs = null;
	 * String sql1 =
	 * "Select count(*) from CSC342.Skill s inner join CSC342.customer c " +
	 * " on (p.person_id = c.customer_id)";
	 * 
	 * try {
	 * 
	 * con = DBConnect.getConnection(); ps=con.prepareStatement(sql1); int
	 * personCt = 0;
	 * 
	 * rs = ps.executeQuery(); while(rs.next()) { personCt = rs.getInt(1); }
	 * System.out.println("countPeople success " + personCt); }
	 * catch(SQLException e) { System.out.println("Error in countPeople" +
	 * e.getSQLState()); System.out.println("/nError Code: " +
	 * e.getErrorCode()); System.out.println("/nMessage: " + e.getMessage());
	 * System.exit( 1 ); } catch(Exception e) {
	 * System.out.println("unknown Error in countPeople");
	 * System.out.println("/nMessage: " + e.getMessage()); System.exit( 1 ); }
	 * finally { if (con != null)
	 * System.out.println("closing count objects \n"); rs.close(); ps.close();
	 * 
	 * }
	 * 
	 * }
	 */

	public void saveSkill(List<Skill> sk) throws SQLException {

		Connection con = null;

		String sql1 = "Select count(*) as skill_count from CSC342.Skill s WHERE s.skill_id = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		System.out.println("save skill");
		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement(sql1);

			for (Iterator<Skill> it = sk.iterator(); it.hasNext();) {
				Skill testSkill = it.next();
				ps.setString(1, testSkill.getSkillId());
				rs = ps.executeQuery();
				while (rs.next()) {
					if (rs.getInt(1) == 1)
						updateSkill(testSkill);
					else if (rs.getInt(1) == 0)
						createSkill(testSkill);
					else
						throw new RuntimeException("More than one skill has skill Id");
				}
			}

		} catch (SQLException e) {
			System.out.println("Error in saveSkill" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in saveSkill");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			rs.close();
			ps.close();
		}

	}
}
