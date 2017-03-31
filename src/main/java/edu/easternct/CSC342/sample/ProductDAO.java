package edu.easternct.CSC342.sample;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {
	private List<Product> pL = new ArrayList<Product>();

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
