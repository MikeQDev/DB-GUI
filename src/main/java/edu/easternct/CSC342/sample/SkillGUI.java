package edu.easternct.CSC342.sample;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SkillGUI extends JFrame {
	private Connection conn;
	private List<Skill> sL = new ArrayList<Skill>();
	private JLabel label_skill_id = new JLabel("SkillID"), label_skill_desc = new JLabel("SkillDesc");
	private JTextField text_skill_id = new JTextField(10), text_skill_desc = new JTextField(10);

	private JButton button_next = new JButton(">"), button_previous = new JButton("<");
	private JButton button_save = new JButton("Save"), button_del = new JButton("Del"), button_add = new JButton("Add"),
			button_jump_to = new JButton("Jump"), button_report = new JButton("Report");;
	private JLabel label_pos = new JLabel("?/?");

	private int curRecord = 0;

	private boolean allNewSaved = true; // so we dont add records without
										// saving..

	public SkillGUI(Connection conn) {
		this.conn = conn;
		this.setTitle("Skill");
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
		JPanel p = new JPanel(new GridLayout(2, 2));

		p.add(label_skill_id);
		p.add(text_skill_id);
		p.add(label_skill_desc);
		p.add(text_skill_desc);
		this.add(p);

		// side/navigation bars
		button_next.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (curRecord != sL.size() - 1) {
					if (rewriteCurToSkillList()) {
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
					if (rewriteCurToSkillList()) {
						curRecord--;
						populateRecord();
					}
				} else {
					if (rewriteCurToSkillList()) {
						gotoRecord(sL.size() - 1);
					}
				}

			}
		});
		this.add(button_previous, BorderLayout.WEST);

		JPanel p_bottom = new JPanel();

		button_save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					rewriteCurToSkillList();
					new SkillDAO().saveSkill(sL);
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
					new SkillDAO().deleteSkill(sL.get(curRecord).getSkillId());
					sL.remove(curRecord);
					if (curRecord == sL.size())
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
					if (rewriteCurToSkillList()) {
						createNewForm();
					}
				}
			}
		});

		button_jump_to.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer i = Integer.parseInt(JOptionPane.showInputDialog("Record to jump to:"));
					if (i < 0 || i >= sL.size())
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

		button_report.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Reporting on most common employee's skills");
				JFileChooser jF = new JFileChooser();
				File f = null;
				if (jF.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					f = jF.getSelectedFile();
					new SkillDAO().report(f);
					JOptionPane.showMessageDialog(null, "Wrote to " + f);
				}
			}
		});

		p_bottom.add(button_report);

		JPanel parent = new JPanel(new GridLayout(2, 1));
		parent.add(p_bottom);

		JPanel child2 = new JPanel();
		child2.add(label_pos);
		parent.add(child2);

		this.add(parent, BorderLayout.SOUTH);

	}

	private void retrieveItems() {
		sL.clear();
		sL = new SkillDAO().getAllSkills();
		System.out.println(sL);
	}

	private int getLoadedRecordsAmt() {
		return sL.size() - 1;
	}

	private void populateRecord() {
		label_pos.setText(curRecord + "/" + getLoadedRecordsAmt());
		loadSkill(sL.get(curRecord));
	}

	private void gotoRecord(int i) {
		curRecord = i;
		label_pos.setText(curRecord + "/" + getLoadedRecordsAmt());
		loadSkill(sL.get(curRecord));
	}

	private void loadSkill(Skill s) {
		String id = s.getSkillId();
		String lineid = s.getSkillDescription();
		text_skill_id.setText("" + id);
		text_skill_desc.setText("" + lineid);
	}

	private boolean rewriteCurToSkillList() {
		boolean success = true;
		try {
			Skill t = sL.get(curRecord);
			t.setSkillId(text_skill_id.getText());
			t.setSkillDescription(text_skill_desc.getText());
			sL.set(curRecord, t);
		} catch (NumberFormatException e) {
			success = false;
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return success;
	}

	private void createNewForm() {
		// clear out
		// try {
		// int nextProdId = new SkillDAO().findMaxProductId() + 1;

		System.out.println("adding new item");
		curRecord = sL.size();
		Skill newSkill = new Skill();
		sL.add(newSkill);
		populateRecord();

		allNewSaved = false;

		text_skill_id.setText("");
		text_skill_desc.setText("");
		/*
		 * catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

}
