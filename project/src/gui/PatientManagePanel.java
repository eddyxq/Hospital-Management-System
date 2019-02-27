package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import display.TestPanel;

/*
 * This class is a panel which allows administrator to select how to interact with a patient
 */
public class PatientManagePanel
{
	/**
	 * This method creates and returns a JPanel
	 * @wbp.parser.entryPoint
	 */
	
	private Font bArial = new Font("Arial", Font.BOLD, 30);
	
	public JPanel createPanel()
	{
		JPanel addPatients = new JPanel();
		addPatients.setLayout(null);
		addPatients.setBounds(0, 0, 1920, 1080);

		JLabel lblWelcomeBackAdministrator = new JLabel("Welcome Back Administrator!");
		lblWelcomeBackAdministrator.setFont(bArial);
		lblWelcomeBackAdministrator.setBounds(166, 29, 510, 26);
		
		DateFormat df = new SimpleDateFormat("EEE MMM dd, yyyy");
		Date today = new Date();
		JLabel date = new JLabel("Today is: "+df.format(today));
		date.setFont(new Font("Calibri Light", Font.PLAIN, 26));
		date.setBounds(166, 87, 560, 26);
		
		JButton btnAddP = new JButton("Add a Patient");
		btnAddP.setFont(new Font("Arial", Font.BOLD, 16));
		btnAddP.setBounds(350, 360, 200, 59);
			
		JButton btnView = new JButton("View Patient List");
		btnView.setFont(new Font("Arial", Font.BOLD, 16));
		btnView.setBounds(350, 560, 200, 59);
		
		JButton btnEdit = new JButton("Edit Patient");
		btnEdit.setFont(new Font("Arial", Font.BOLD, 16));
		btnEdit.setBounds(350, 760, 200, 59);
		
		
		JLabel lblBackground = new JLabel();
		lblBackground.setIcon(new ImageIcon(TestPanel.class.getResource("/graphics/AddPatientBackground.png")));
		lblBackground.setBounds(0, 0, 1920, 1080);
		
		//Adding Components 
		addPatients.add(btnAddP);
		addPatients.add(btnView);
		addPatients.add(btnEdit);
		addPatients.add(lblWelcomeBackAdministrator);
		addPatients.add(date);
		addPatients.add(lblBackground);
		
		return addPatients;
	}
}

