package edu.easternct.CSC342.sample;

import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {
	private List<Product> pL = new ArrayList<Product>();

	public void report(File f) {
		String reportQuery = "SELECT P.PRODUCT_ID, SUM(ORDERED_QUANTITY) AS TOTAL_ORDERED, " + "PRODUCT_DESCRIPTION "
				+ "FROM csc342.PRODUCT P " + "INNER JOIN( "
				+ "        SELECT O.ORDER_ID, CUSTOMER_ID, ORDER_DATE, PRODUCT_ID, " + "ORDERED_QUANTITY "
				+ "        FROM csc342.ORDER_LINE O " + "        INNER JOIN( "
				+ "                SELECT ORDER_ID, O.CUSTOMER_ID, ORDER_DATE "
				+ "                FROM csc342.FACTORY_ORDER O " + "                INNER JOIN ( "
				+ "                        SELECT CUSTOMER_ID " + "                        FROM csc342.CUSTOMER "
				+ "                        INNER JOIN csc342.EMPLOYEE "
				+ "                        ON EMPLOYEE_ID=CUSTOMER_ID "
				+ "                ) T ON O.CUSTOMER_ID=T.CUSTOMER_ID " + "        ) T ON O.ORDER_ID=T.ORDER_ID "
				+ ") T ON P.PRODUCT_ID=T.PRODUCT_ID " + "GROUP BY P.PRODUCT_ID, PRODUCT_DESCRIPTION "
				+ "ORDER BY TOTAL_ORDERED DESC ";
		ResultSet rs = null;
		Product outProduct = new Product();
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement(reportQuery);

			rs = ps.executeQuery();

			BufferedWriter bW = new BufferedWriter(new FileWriter(f));
			
			bW.write("Product_ID,Total_Ordered,Product_Description"+System.lineSeparator());

			while (rs.next()) {
				// "SELECT P.PRODUCT_ID, SUM(ORDERED_QUANTITY) AS TOTAL_ORDERED,
				// "+ "PRODUCT_DESCRIPTION "+
				int prodId = rs.getInt(1);
				int orderedQty = rs.getInt(2);
				String productDesc = rs.getString(3);
				bW.write(prodId + "," + orderedQty + "," + productDesc + System.lineSeparator());
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

	public List<Product> getAllProducts() {
		pL.clear();
		ResultSet rs = null;
		Product outProduct = new Product();
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement(
					"select p.product_id, p.product_line_id, p.product_description, p.product_finish, p.product_standard_price"
							+ " from CSC342.product p order by p.product_id asc");

			rs = ps.executeQuery();
			while (rs.next()) {
				outProduct = new Product();
				outProduct.setProduct_id(rs.getInt(1));
				outProduct.setProduct_line_id(rs.getInt(2));
				outProduct.setProduct_description(rs.getString(3));
				outProduct.setProduct_finish(rs.getString(4));
				outProduct.setProduct_standard_price(rs.getInt(5));
				pL.add(outProduct);
			}
		} catch (SQLException e) {
			System.out.println("Error in Product view access" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Product view access");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Product connection \n");
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pL;
	}

	public void createProduct(Product product) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		System.out.println("Product to be Inserted \n");
		System.out.println(product.toString());

		// try {

		con = DBConnect.getConnection();
		ps = con.prepareStatement(
				"INSERT INTO csc342.Product (product_id,product_line_id,product_description,product_finish,product_standard_price) values (?,?,?,?,?)");
		ps.setInt(1, product.getProduct_id());
		ps.setInt(2, product.getProduct_line_id());
		ps.setString(3, product.getProduct_description());
		ps.setString(4, product.getProduct_finish());
		ps.setInt(5, product.getProduct_standard_price());

		ps.executeUpdate();
		System.out.println("Addition Success");

		/*
		 * } catch (SQLException e) {
		 * System.out.println("Error in Create Product" + e.getSQLState());
		 * System.out.println("/nError Code: " + e.getErrorCode());
		 * System.out.println("/nMessage: " + e.getMessage());
		 * 
		 * System.exit(1); } catch (Exception e) {
		 * System.out.println("unknown Error in Create Product");
		 * System.out.println("/nMessage: " + e.getMessage()); System.exit(1); }
		 * finally { if (con != null)
		 * System.out.println("closing Product create statement \n");
		 * ps.close();
		 * 
		 * }
		 */
		if (con != null)
			System.out.println("closing Product create statement \n");
		ps.close();

	}

	public int findMaxProductId() throws SQLException {

		int maxProductId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement("select max(p.product_id) from CSC342.Product p");

			rs = ps.executeQuery();
			while (rs.next()) {
				maxProductId = rs.getInt(1);
				System.out.println("Get Max Product Id Success ");

			}
		} catch (SQLException e) {
			System.out.println("Error in get max product access" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in get max product");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Product connection \n");
			rs.close();
			ps.close();
		}
		return maxProductId;
	}

	public Product viewProduct(int productId) throws SQLException {

		ResultSet rs = null;
		Product outProduct = new Product();
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement(
					"select p.product_id, p.product_line_id, p.product_description, p.product_finish, p.product_standard_price"
							+ "from CSC342.product p where p.product_id=?");
			ps.setInt(1, productId);

			rs = ps.executeQuery();
			while (rs.next()) {
				outProduct.setProduct_id(rs.getInt(1));
				outProduct.setProduct_line_id(rs.getInt(2));
				outProduct.setProduct_description(rs.getString(3));
				outProduct.setProduct_finish(rs.getString(4));
				outProduct.setProduct_standard_price(rs.getInt(5));

				/*
				 * don't need to set parent, must be done when you instantiate
				 * the ee class (must setup past classes correctly.
				 */

				System.out.println("View Product Success");
			}
		} catch (SQLException e) {
			System.out.println("Error in Product view access" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Product view access");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Product connection \n");
			rs.close();
			ps.close();
		}
		return outProduct;
	}

	public void updateProduct(Product product) throws SQLException {

		System.out.println("Product to be Updated \n");
		System.out.println(product.toString());
		Connection con = null;
		PreparedStatement ps = null;

		// try {

		System.out.println("working w: " + product);

		con = DBConnect.getConnection();

		ps = con.prepareStatement("update CSC342.product p set p.product_id = ?, p.product_line_id = ?, "
				+ "p.product_description = ?, p.product_finish = ?, p.product_standard_price = ? where product_id = ?");

		ps.setInt(1, product.getProduct_id());
		ps.setInt(2, product.getProduct_line_id());
		ps.setString(3, product.getProduct_description());
		ps.setString(4, product.getProduct_finish());
		ps.setInt(5, product.getProduct_standard_price());

		ps.setInt(6, product.getProduct_id());

		ps.executeQuery();
		System.out.println("updated");
		/*
		 * } catch (SQLException e) {
		 * System.out.println("Error in Product Update" + e.getSQLState());
		 * System.out.println("/nError Code: " + e.getErrorCode());
		 * System.out.println("/nMessage: " + e.getMessage()); System.exit(1); }
		 * catch (Exception e) {
		 * System.out.println("unknown Error in Product Update");
		 * System.out.println("/nMessage: " + e.getMessage()); System.exit(1); }
		 * finally { if (con != null)
		 * System.out.println("closing Product connection \n"); ps.close(); }
		 */
		if (con != null)
			System.out.println("closing Product connection \n");
		ps.close();
	}

	public void deleteProduct(int ProductId) throws SQLException {

		System.out.println("Product to be Deleted \n");
		System.out.println("Product Id = " + ProductId + "\n");

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement("delete CSC342.Product where Product_id=?");
			ps.setInt(1, ProductId);
			ps.executeQuery();
			System.out.println("deleted");
		} catch (SQLException e) {
			System.out.println("Error in Product Delete" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in Product delete");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing Product connection \n");
			ps.close();

		}
	}

	public void countProducts() throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql1 = "Select count(*) from CSC342.Product";
		// p inner join CSC342.customer c on (p.person_id = c.customer_id)";

		try {

			con = DBConnect.getConnection();
			ps = con.prepareStatement(sql1);
			int ProductCt = 0;

			rs = ps.executeQuery();
			while (rs.next()) {
				ProductCt = rs.getInt(1);
			}
			System.out.println("countProducts success " + ProductCt);
		} catch (SQLException e) {
			System.out.println("Error in countProducts" + e.getSQLState());
			System.out.println("/nError Code: " + e.getErrorCode());
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("unknown Error in countProducts");
			System.out.println("/nMessage: " + e.getMessage());
			System.exit(1);
		} finally {
			if (con != null)
				System.out.println("closing count objects \n");
			rs.close();
			ps.close();

		}

	}

	public void saveProducts(List<Product> Products) throws SQLException {

		/*
		 * String delSql = "delete from CSC342.Product"; Connection con2 =
		 * DBConnect.getConnection(); PreparedStatement ps2 =
		 * con2.prepareStatement(delSql); ps2.executeQuery();
		 */

		Connection con = null;

		String sql1 = "Select count(*) as Product_count from CSC342.Product p WHERE p.Product_id = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		// try {

		con = DBConnect.getConnection();
		ps = con.prepareStatement(sql1);

		for (Iterator<Product> it = Products.iterator(); it.hasNext();) {
			Product testProduct = it.next();
			ps.setInt(1, testProduct.getProduct_id());
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 1)
					updateProduct(testProduct);
				else if (rs.getInt(1) == 0)
					createProduct(testProduct);
				else
					throw new RuntimeException("More than one Product has Product Id");
			}
		}

		/*
		 * } catch (SQLException e) { System.out.println("Error in saveProducts"
		 * + e.getSQLState()); System.out.println("/nError Code: " +
		 * e.getErrorCode()); System.out.println("/nMessage: " +
		 * e.getMessage()); System.exit(1); } catch (Exception e) {
		 * System.out.println("unknown Error in saveProducts");
		 * System.out.println("/nMessage: " + e.getMessage()); System.exit(1); }
		 * finally { }
		 */
		con.commit();
		rs.close();
		ps.close();

	}
}
