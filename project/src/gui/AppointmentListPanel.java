package gui;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import system.Appointment;
import system.Hospital_Management_System;

/*
 * This class displays the add patient panel.
 */
public class AppointmentListPanel 
{
	private Font bArial = new Font("Arial", Font.BOLD, 30);
	private JTable table = new JTable(new DefaultTableModel(new Object[]{	
	 "Appointment Date", "Appointment Time", "Doctor"}, 0))
	{
		private static final long serialVersionUID = 1L;
		public boolean isCellEditable(int row, int column) 
		{  
			return false;            
		};
	};
	private DefaultTableModel model = (DefaultTableModel) table.getModel();
	/**
	 * This method creates and returns a JPanel
	 * @param hms The System.
	 */
	public JPanel createPanel(Hospital_Management_System hms)
	{
		//set table settings
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setRowHeight(50);
		table.setEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane tableContainer = new JScrollPane(table);
		tableContainer.setLocation(86, 244);
		tableContainer.setSize(1746, 700);
		//initialize the panel layout and size
		JPanel appointmentListPanel = new JPanel();
		appointmentListPanel.setLayout(null);
		appointmentListPanel.setBounds(0, 0, 1920, 1080);
		//set background
		JLabel lblBackground = new JLabel();
		lblBackground.setIcon(new ImageIcon(AppointmentListPanel.class.getResource("/graphics/patientList_background.png")));
		lblBackground.setBounds(0, 0, 1920, 1080);
		/*
		 * HEADER MESSAGE
		 */
		JLabel lblWelcomeBack = new JLabel("Welcome Back!");
		lblWelcomeBack.setFont(bArial);
		lblWelcomeBack.setBounds(166, 29, 510, 26);
		/*
		 * DATE DISPLAYED BELOW HEADER
		 */
		DateFormat df = new SimpleDateFormat("EEE MMM dd, yyyy");
		Date today = new Date();
		JLabel date = new JLabel("Today is: "+df.format(today));
		date.setFont(new Font("Calibri Light", Font.PLAIN, 26));
		date.setBounds(166, 87, 560, 26);
		/*
		 * RETURN BUTTON
		 */
		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				hms.displayHomePage();
			}
		});
		btnReturn.setFont(new Font("Arial", Font.BOLD, 16));
		btnReturn.setBounds(725, 955, 500, 59);
		//add all the components to panel
		appointmentListPanel.add(btnReturn);
		appointmentListPanel.add(tableContainer, BorderLayout.CENTER);
		appointmentListPanel.add(lblWelcomeBack);
		appointmentListPanel.add(date);
		appointmentListPanel.add(lblBackground);
		
		return appointmentListPanel;
	}
	/**
	 * Adds appointment to the list
	 * @param app The appointment to be added.
	 * @param hms The system.
	 */
	public void addAppointmentToTable(Appointment app, Hospital_Management_System hms) 
	{
		model.addRow(new Object[]{app.getDate(), app.getTime(), app.getDocName()});
	}
	/**
	 *	This method will clear all the rows in the table.
	 */
	public void clearAllRow() 
	{
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) 
		{
			model.removeRow(i);
		}
	}
}
