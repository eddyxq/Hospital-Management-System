package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import system.Hospital_Management_System;

/*
 * This class is a panel which allows administrator to select how to interact with patient/
 * employee records.
 */
public class AdminMainPanel
{
	/**
	 * This method creates and returns a JPanel
	 * @wbp.parser.entryPoint
	 */
	private Font bArial = new Font("Arial", Font.BOLD, 30);
	
	public JPanel createPanel(Hospital_Management_System hms)
	{
		//initialize the panel layout and size
		JPanel adminMain = new JPanel();
		adminMain.setLayout(null);
		adminMain.setBounds(0, 0, 1920, 1080);
		//set background
		JLabel lblBackground = new JLabel();
		lblBackground.setIcon(new ImageIcon(AdminMainPanel.class.getResource("/graphics/Admin_background.png")));
		lblBackground.setBounds(0, 0, 1920, 1080);
		/*
		 * HEADER MESSAGE
		 */
		JLabel lblWelcomeBackAdministrator = new JLabel("Welcome Back Administrator!");
		lblWelcomeBackAdministrator.setFont(bArial);
		lblWelcomeBackAdministrator.setBounds(166, 29, 510, 26);
		/*
		 * DATE DISPLAYED BELOW HEADER
		 */
		DateFormat df = new SimpleDateFormat("EEE MMM dd, yyyy");
		Date today = new Date();
		JLabel date = new JLabel("Today is: "+df.format(today));
		date.setFont(new Font("Calibri Light", Font.PLAIN, 26));
		date.setBounds(166, 87, 560, 26);
		/*
		 * MANAGE PATIENT RECORDS BUTTON
		 */
		JButton btnManagePatient = new JButton("Manage Patient Records");
		btnManagePatient.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				hms.displayPatientManagementPage();
			}
		});
		btnManagePatient.setFont(new Font("Arial", Font.BOLD, 16));
		btnManagePatient.setBounds(725, 360, 500, 59);
		/*
		 * MANAGE EMPLOYEE RECORDS BUTTON
		 */
		JButton btnManageEmployee = new JButton("Manage Employee Records");
		btnManageEmployee.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				hms.displayStaffManagementPage();
			}
		});
		btnManageEmployee.setFont(new Font("Arial", Font.BOLD, 16));
		btnManageEmployee.setBounds(725, 560, 500, 59);
		/*
		 * RETURN BUTTON
		 */
		JButton btnReturn = new JButton("Logout");
		btnReturn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				hms.displayUsernameNPasswordPageAdmin();
			}
		});
		btnReturn.setFont(new Font("Arial", Font.BOLD, 16));
		btnReturn.setBounds(725, 760, 500, 59);
		//add all the components to panel
		adminMain.add(btnManagePatient);
		adminMain.add(btnManageEmployee);
		adminMain.add(btnReturn);
		adminMain.add(lblWelcomeBackAdministrator);
		adminMain.add(date);
		adminMain.add(lblBackground);
		
		return adminMain;
	}
}

