package edu.easternct.CSC342.sample;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProductGUI extends JFrame {
	private Connection conn;
	private List<Product> pL = new ArrayList<Product>();
	private JLabel label_product_id = new JLabel("ProdID"), label_product_line_id = new JLabel("LineID"),
			label_product_description = new JLabel("Description"), label_product_finish = new JLabel("Finish"),
			label_product_standard_price = new JLabel("Price");
	private JTextField text_product_id = new JTextField(10), text_product_line_id = new JTextField(10),
			text_product_description = new JTextField(10), text_product_finish = new JTextField(10),
			text_product_standard_price = new JTextField(10);

	private JButton button_next = new JButton(">"), button_previous = new JButton("<");
	private JButton button_save = new JButton("Save"), button_del = new JButton("Del"), button_add = new JButton("Add"),
			button_jump_to = new JButton("Jump");
	private JLabel label_pos = new JLabel("?/?");

	private int curRecord = 0;

	private boolean allNewSaved = true; // so we dont add records without
										// saving..

	public ProductGUI(Connection conn) {
		this.conn = conn;
		this.setTitle("Product");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		buildWindow();
		retrieveItems();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.pack();
		populateRecord();
	}

	private void buildWindow() {
		// main content
		JPanel p = new JPanel(new GridLayout(5, 2));

		p.add(label_product_id);
		p.add(text_product_id);
		p.add(label_product_line_id);
		p.add(text_product_line_id);
		p.add(label_product_description);
		p.add(text_product_description);
		p.add(label_product_finish);
		p.add(text_product_finish);
		p.add(label_product_standard_price);
		p.add(text_product_standard_price);
		this.add(p);

		// side/navigation bars
		button_next.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (curRecord != pL.size() - 1) {
					if (rewriteCurToProductList()) {
						curRecord++;
						populateRecord();
					}
				} else {
					gotoRecord(0);
				}
			}
		});
		this.add(button_next, BorderLayout.EAST);

		button_previous.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (curRecord != 0) {
					if (rewriteCurToProductList()) {
						curRecord--;
						populateRecord();
					}
				} else {
					if (rewriteCurToProductList()) {
						gotoRecord(pL.size() - 1);
					}
				}

			}
		});
		this.add(button_previous, BorderLayout.WEST);

		JPanel p_bottom = new JPanel();

		button_save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					rewriteCurToProductList();
					new ProductDAO().saveProducts(pL);
					retrieveItems();
					allNewSaved = true;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		button_del.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					new ProductDAO().deleteProduct(pL.get(curRecord).getProduct_id());
					pL.remove(curRecord);
					if (curRecord == pL.size())
						curRecord--;
					populateRecord();
					allNewSaved = true;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});

		button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (allNewSaved) {
					if (rewriteCurToProductList()) {
						createNewForm();
					}
				}
			}
		});

		button_jump_to.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer i = Integer.parseInt(JOptionPane.showInputDialog("Record to jump to:"));
					if (i < 0 || i >= pL.size())
						throw new Exception();
					gotoRecord(i);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid record to jump to!");
				}
			}
		});

		p_bottom.add(button_add);
		p_bottom.add(button_save);
		p_bottom.add(button_del);
		p_bottom.add(button_jump_to);
		p_bottom.add(label_pos);

		this.add(p_bottom, BorderLayout.SOUTH);

	}

	/*
	 * (private void rebuildProductObject() { Product p = new Product();
	 * p.setProduct_id(text_product_id);
	 * p.setProduct_line_id(text_product_line_id);
	 * p.setProduct_description(text_product_description);
	 * p.setProduct_finish(text_);
	 * p.setProduct_standard_price(product_standard_price); }
	 */

	private void retrieveItems() {
		pL.clear();
		pL = new ProductDAO().getAllProducts();
		System.out.println(pL);
	}

	private int getLoadedRecordsAmt() {
		return pL.size() - 1;
	}

	private void populateRecord() {
		label_pos.setText(curRecord + "/" + getLoadedRecordsAmt());
		loadProduct(pL.get(curRecord));
	}

	private void gotoRecord(int i) {
		curRecord = i;
		label_pos.setText(curRecord + "/" + getLoadedRecordsAmt());
		loadProduct(pL.get(curRecord));
	}

	private void loadProduct(Product p) {
		int id = p.getProduct_id();
		int lineid = p.getProduct_line_id();
		String desc = p.getProduct_description();
		String finish = p.getProduct_finish();
		int price = p.getProduct_standard_price();
		text_product_id.setText("" + id);
		text_product_line_id.setText("" + lineid);
		text_product_description.setText(desc);
		text_product_finish.setText(finish);
		text_product_standard_price.setText("" + price);
	}

	private boolean rewriteCurToProductList() {
		boolean success = true;
		try {
			Product t = pL.get(curRecord);
			t.setProduct_line_id(Integer.parseInt(text_product_line_id.getText()));
			t.setProduct_description(text_product_description.getText());
			t.setProduct_finish(text_product_finish.getText());
			t.setProduct_standard_price(Integer.parseInt(text_product_standard_price.getText()));
			pL.set(curRecord, t);
		} catch (NumberFormatException e) {
			success = false;
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return success;
	}

	private void createNewForm() {
		// clear out
		try {
			int nextProdId = new ProductDAO().findMaxProductId() + 1;

			System.out.println("adding new item");
			curRecord = pL.size();
			Product newProd = new Product();
			newProd.setProduct_id(nextProdId);
			pL.add(newProd);
			populateRecord();

			allNewSaved = false;

			text_product_id.setText("" + nextProdId);
			text_product_line_id.setText("");
			text_product_description.setText("");
			text_product_finish.setText("");
			text_product_standard_price.setText("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
