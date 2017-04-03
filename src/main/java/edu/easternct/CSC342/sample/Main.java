package edu.easternct.CSC342.sample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JFrame {
	private String hostname;
	private String port;
	private String sid;
	private String id;
	private String pwrd;

	private final static String GUI_USER = "team2", GUI_PASS = "database";

	private JButton skill = new JButton("Skills");
	private JButton salary = new JButton("Salaries");
	private JButton product = new JButton("Products");

	private Connection conn;

	public Main(String[] args) {
		// prompt for login?
		buildConnection(args);
		this.setTitle("CSC342 Management System");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		buildWindow();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.pack();
	}

	private void buildWindow() {
		JPanel p = new JPanel();
		p.add(skill);
		p.add(salary);
		p.add(product);

		addListeners();
		this.add(p);
	}

	private void addListeners() {
		skill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SkillGUI(conn);
			}
		});
		salary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SalaryGUI(conn);
			}
		});
		product.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ProductGUI(conn);
			}
		});
	}

	public static void main(String[] args) {
		/*
		 * if (!login()) { JOptionPane.showMessageDialog(null,
		 * "Invalid login credentials."); System.exit(0); }
		 */
		new Main(args);
	}

	private static boolean login() {
		String uName = JOptionPane.showInputDialog("Username:");
		String pWord = JOptionPane.showInputDialog("Password:");
		return uName.equals(GUI_USER) && pWord.equals(GUI_PASS);
	}

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

	private void buildConnection(String[] args) {
		try {

			this.setHostname(args[0]);
			this.setPort(args[1]);
			this.setSid(args[2]);
			this.setId(args[3]);
			this.setPwrd(args[4]);
		} catch (Exception ex) {
			System.out.println("Error in testing");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}

		conn = DBConnect.getConnection(hostname, port, sid, id, pwrd);
	}

	public Connection getConnection() {
		return conn;
	}

}
