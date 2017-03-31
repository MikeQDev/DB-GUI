package edu.easternct.CSC342.sample;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SalaryGUI extends JFrame {

	private Connection conn;

	public SalaryGUI(Connection conn) {
		this.conn = conn;
		this.setTitle("Salary");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		buildWindow();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.pack();
	}

	private void buildWindow() {
		this.add(new JLabel("lolfddffdfdvdvffdvdfvvfdfddfdfv"));
	}

}
