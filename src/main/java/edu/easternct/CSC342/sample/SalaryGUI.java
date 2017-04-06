package edu.easternct.CSC342.sample;

import java.sql.Connection;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SalaryGUI extends JFrame {

	private Connection conn;
	private List<Salaries> sal = new ArrayList<Salaries>();
	private JLabel labelEmplID = new JLabel("EmplID"), labelSalary = new JLabel("Salary"), 
			labelStartDate = new JLabel("Salary Start Date"),
			labelEndDate = new JLabel("Salary End Date");
	private JTextField textEmplID = new JTextField(10), textSalary = new JTextField(10),
			textStartDate = new JTextField(10), textEndDate = new JTextField(10);
	
	private JButton button_next = new JButton(">"), button_previous = new JButton("<");
	private JButton button_save = new JButton("Save"), button_del = new JButton("Del"), 
			button_add = new JButton("Add");
	private JLabel label_pos = new JLabel("?/?");

	private int curRecord = 0;

	private boolean allNewSaved = true;
	
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
		JPanel p = new JPanel(new GridLayout(4, 2));

		p.add(labelEmplID);
		p.add(textEmplID);
		p.add(labelSalary);
		p.add(textSalary);
		p.add(labelStartDate);
		p.add(textStartDate);
		p.add(labelEndDate);
		p.add(textEndDate);
		this.add(p);

		button_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (curRecord != sal.size() - 1) {
					if (rewriteCurToSalaryList()) {
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
					if (rewriteCurToSalaryList()) {
						curRecord--;
						populateRecord();
					}
				} else {
					if (rewriteCurToSalaryList()) {
						gotoRecord(sal.size() - 1);
					}
				}
			}
		});
		this.add(button_previous, BorderLayout.WEST);

		JPanel p_bottom = new JPanel();

		button_save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					rewriteCurToSalaryList();
					new SalariesDAO().saveSalaries(sal);
					retrieveItems();
					allNewSaved = true;
				} catch (SQLException e1) {					
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		button_del.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					new SalariesDAO().deleteSalary(sal.get(curRecord).getEmployeeId());
					sal.remove(curRecord);
					if (curRecord == sal.size())
						curRecord--;
					populateRecord();
					allNewSaved = true;
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});

		button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (allNewSaved) {
					if (rewriteCurToSalaryList()) {
						createNewForm();
					}
				}
			}
		});
		p_bottom.add(button_add);
		p_bottom.add(button_save);
		p_bottom.add(button_del);
		p_bottom.add(label_pos);

		this.add(p_bottom, BorderLayout.SOUTH);
	}

	private void retrieveItems() {
		sal.clear();
		sal = new SalariesDAO().getAllSalaries();
		System.out.println(sal);
	}

	private int getLoadedRecordsAmt() {
		return sal.size() - 1;
	}

	private void populateRecord() {
		label_pos.setText(curRecord + "/" + getLoadedRecordsAmt());
		loadProduct(sal.get(curRecord));
	}

	private void gotoRecord(int i) {
		curRecord = i;
		label_pos.setText(curRecord + "/" + getLoadedRecordsAmt());
		loadProduct(sal.get(curRecord));
	}

	private void loadProduct(Salaries s) {
		BigDecimal id = s.getEmployeeId();
		BigDecimal salary = s.getSalary();
		Timestamp startDate = s.getStartDate();
		Timestamp endDate = s.getEndDate();
		textEmplID.setText("" + id);
		textSalary.setText("" + salary);
		textStartDate.setText(startDate.toString());
		textEndDate.setText(endDate.toString());
	}

	private boolean rewriteCurToSalaryList() {
		boolean success = true;
		try {
			Salaries s = sal.get(curRecord);
			String ans = textEmplID.getText();
			BigDecimal empID = new BigDecimal(Integer.parseInt(ans));
			BigDecimal salNum = new BigDecimal(Integer.parseInt(textSalary.getText()));
			s.setEmployeeId(empID);
			s.setSalary(salNum);
			Timestamp sDate = new Timestamp(Integer.parseInt(textStartDate.getText()));
			Timestamp eDate = new Timestamp(Integer.parseInt(textEndDate.getText()));
			s.setStartDate(sDate);
			s.setEndDate(eDate);
			sal.set(curRecord, s);
		} catch (NumberFormatException e) {
			success = false;
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return success;
	}

	private void createNewForm() {
		try {
			BigDecimal nextEmplId = new SalariesDAO().findMaxEmployeeId();

			System.out.println("adding new salary");
			curRecord = sal.size();
			Salaries newSal = new Salaries();
			newSal.setEmployeeId(nextEmplId);
			sal.add(newSal);
			populateRecord();

			allNewSaved = false;

			textEmplID.setText("" + nextEmplId);
			textSalary.setText("");
			textStartDate.setText("");
			textEndDate.setText("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}