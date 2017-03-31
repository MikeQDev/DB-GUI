package edu.easternct.CSC342.sample;

import java.sql.*;
import java.math.*;
import java.util.*;

public class ProductTestDriverDB {

	List<Product> products = new ArrayList<Product>();
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

		int increment = 1;
		int nextProductId = 1;
		Connection conn;

		conn = DBConnect.getConnection(hostname, port, sid, id, pwrd);
		ProductDAO productDAO = new ProductDAO();
		nextProductId = productDAO.findMaxProductId();
		nextProductId = nextProductId += increment;
		Product prod = new Product();
		prod.setProduct_id(nextProductId);
		prod.setProduct_line_id(3);
		prod.setProduct_description("Blue chair");
		prod.setProduct_finish("Satin");
		prod.setProduct_standard_price(100);
		products.add(prod);

		Product prod2 = new Product();
		prod2.setProduct_id(nextProductId += increment);
		prod2.setProduct_line_id(4);
		prod2.setProduct_description("Red chair");
		prod2.setProduct_finish("Satin");
		prod2.setProduct_standard_price(95);
		products.add(prod2);

		productDAO.saveProducts(products);
		conn.commit();
		// Reports2DAO skills = new Reports2DAO();
		// StringBuffer skillsReport = skills.getSkillSummary();
		// System.out.println(skillsReport);

	}

	public void printContacts() throws Exception {

		Iterator it = products.iterator();
		int productNumber = 0;
		while (it.hasNext())

		{
			productNumber++;
			Product people = (Product) it.next();

			System.out.println(" product " + productNumber + " = " + people.toString());
		}

	}

	public static void main(String[] args) {

		/*
		 * This command instantiates a class instance passing the connection
		 * object.
		 */
		ProductTestDriverDB testproduct = new ProductTestDriverDB();

		try {

			/* call purge method to delete all rows from new summary table */

			testproduct.setHostname(args[0]);
			testproduct.setPort(args[1]);
			testproduct.setSid(args[2]);
			testproduct.setId(args[3]);
			testproduct.setPwrd(args[4]);

			testproduct.testCreate();
			testproduct.printContacts();
		} catch (Exception ex) {
			System.out.println("Error in testing");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}
	}

}
