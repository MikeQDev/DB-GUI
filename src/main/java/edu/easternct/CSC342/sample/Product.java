package edu.easternct.CSC342.sample;

public class Product implements Comparable {
	private int product_id;
	private int product_line_id;
	private String product_description;
	private String product_finish;
	private int product_standard_price;

	public int compareTo(Object o) {
		Product other = (Product) o;
		if (other.getProduct_standard_price() > product_standard_price)
			return -1;
		else if (other.getProduct_standard_price() < product_standard_price)
			return 1;
		return 0;
	}

	@Override
	public String toString() {
		return "Product_ID:"+product_id + ",Product_Line_ID:" + product_line_id + ",Product_Description:" + product_description + ",Product_Finish:" + product_finish + ",Product_Standard_Price:"
				+ product_standard_price;
	}

	public Product(int product_id, int product_line_id, String product_description, String product_finish,
			int product_standard_price) {
		this.product_id = product_id;
		this.product_line_id = product_line_id;
		this.product_description = product_description;
		this.product_finish = product_finish;
		this.product_standard_price = product_standard_price;
	}

	public Product() {

	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getProduct_line_id() {
		return product_line_id;
	}

	public void setProduct_line_id(int product_line_id) {
		this.product_line_id = product_line_id;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}

	public String getProduct_finish() {
		return product_finish;
	}

	public void setProduct_finish(String product_finish) {
		this.product_finish = product_finish;
	}

	public int getProduct_standard_price() {
		return product_standard_price;
	}

	public void setProduct_standard_price(int product_standard_price) {
		this.product_standard_price = product_standard_price;
	}

}
