package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import system.Hospital_Management_System;
import system.ValidateInput;

/*
 * This class displays the add patient panel.
 */
public class AddAppointmentPanel 
{
	private JTextField tfDate;
	JComboBox<String> jcDepartment;
	private Font bArial = new Font("Arial", Font.BOLD, 30);
	private Color Red = new Color(255, 150, 135);
	private Color Default = new Color(255,255,255);
	private ValidateInput val = new ValidateInput();
	//Time slots for selection
	private String[] timeSlots = {"06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00",
			"10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
			"16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30"};
	private JComboBox<String> jcDoctors;
	private Hospital_Management_System hms;
	/**
	 * This method creates and returns a JPanel
	 * @param hms
	 * @return this panel
	 */
	public JPanel createPanel(Hospital_Management_System hms)
	{
		this.hms = hms;
		//initialize the panel layout and size
		JPanel addAppointmentPanel = new JPanel();
		addAppointmentPanel.setLayout(null);
		addAppointmentPanel.setBounds(0, 0, 1920, 1080);
		//set background
		JLabel lblBackground = new JLabel();
		lblBackground.setIcon(new ImageIcon(AddAppointmentPanel.class.getResource("/graphics/addApp_backgrounds.png")));
		lblBackground.setBounds(0, 0, 1920, 1080);
		/*
		 * HEADER MESSAGE
		 */
		JLabel lblWelcomeBackAdministrator = new JLabel("Welcome Back!");
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
		 * DATE
		 */
		JLabel lblDate = new JLabel("Date: (DD/MM/YYYY)");
		lblDate.setFont(new Font("Arial", Font.BOLD, 16));
		lblDate.setBounds(696, 410, 300, 30);
		/*
		 * Text Field for Appointment Date
		 */
		tfDate = new JTextField();
		tfDate.setBounds(1123, 417, 200, 20);
		tfDate.setColumns(10);
		/*
		 * START TIME
		 */
		JLabel lbStartTime = new JLabel("Start Time: (HH:MM)");
		lbStartTime.setFont(new Font("Arial", Font.BOLD, 16));
		lbStartTime.setBounds(696, 568, 250, 20);
		/*
		 * Drop-down menu for Appointment Start Time
		 */
		JComboBox<String> jcStartTime = new JComboBox<String>(timeSlots);
		jcStartTime.setSelectedIndex(0);
		jcStartTime.setBounds(1170, 570, 100, 20);
		/*
		 * END TIME
		 */
		JLabel lbEndTime = new JLabel("End Time: (HH:MM)");
		lbEndTime.setFont(new Font("Arial", Font.BOLD, 16));
		lbEndTime.setBounds(696, 623, 300, 14);
		/*
		 * Drop-down for Appointment End Time
		 */
		JComboBox<String> jcEndTime = new JComboBox<String>(timeSlots);
		jcEndTime.setSelectedIndex(1);
		jcEndTime.setBounds(1170, 622, 100, 20);
		/*
		 * DEPARTMENT
		 */
		JLabel lbDepartment = new JLabel("Department: ");
		lbDepartment.setFont(new Font("Arial", Font.BOLD, 16));
		lbDepartment.setBounds(696, 451, 250, 50);
		/*
		 * DEPARMENT LIST DROP-DOWN MENU
		 */
		String[] deptList = hms.getStringArray(hms.getDepartmentRecord());
		jcDepartment = new JComboBox<String>(deptList);
		jcDepartment.setSelectedIndex(0);
		jcDepartment.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//Clears the combo box
				jcDoctors.removeAllItems();
				
				String[] docList = hms.getDoctorsInDepartment((String)jcDepartment.getSelectedItem());
				for (int i=0; i< docList.length; i++) 
				{
					//Add the list of doctors into the combo box
					jcDoctors.addItem(docList[i]);
				}
			}
		});
		jcDepartment.setBounds(1123, 468, 200, 20);
		/*
		 * DOCTORS
		 */
		JLabel lbDoctors = new JLabel("Doctor: ");
		lbDoctors.setFont(new Font("Arial", Font.BOLD, 16));
		lbDoctors.setBounds(696, 505, 112, 42);
		/*
		 * DOCTORS LIST DROP-DOWN MENU
		 */
		String[] list = {"N/A"};
		// Sets the contents of the doctor drop-down menu to match the department.
		if (hms.getDoctorsInDepartment((String) jcDepartment.getSelectedItem()).length > 0)
		{
			list = hms.getDoctorsInDepartment((String) jcDepartment.getSelectedItem());
		}

		jcDoctors = new JComboBox<String>(list);
		jcDoctors.setSelectedIndex(0);
		jcDoctors.setBounds(1046, 518, 169, 20);
		/*
		 * CANCEL BUTTON
		 */
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//Initially clearing all field colors and content
				clearRedField();
				clearTextField();
				jcStartTime.setBackground(Default);
				jcEndTime.setBackground(Default);
				
				if(hms.getAccessFrom() == "Admin")
				{
					hms.displayPatientListPage();
				}
				else if(hms.getAccessFrom() == "Employee")
				{
					hms.displayEmployeeMainPage();
				}
			}
		});
		btnCancel.setFont(new Font("Arial", Font.BOLD, 16));
		btnCancel.setBounds(1060, 842, 169, 59);
		/*
		 * SUBMIT BUTTON
		 */
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Arial", Font.BOLD, 16));
		btnSubmit.setBounds(740, 842, 169, 59);
		btnSubmit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//Initially clearing all field colors
				clearRedField();
				jcStartTime.setBackground(Default);
				jcEndTime.setBackground(Default);
				
				//Initializing the error message string (Additional explanation is added for the entries that fail validation)
				String errorMessage = "";
	
		    	if(formComplete())
						{
							//If all input is correct, add this appointment
							if(val.validateAppointment(tfDate.getText(), jcStartTime.getSelectedItem().toString(), jcEndTime.getSelectedItem().toString(), 
									jcDoctors.getSelectedItem().toString())) 
							{
								String appointmentTime = jcStartTime.getSelectedItem().toString() + "-" + jcEndTime.getSelectedItem().toString();
								hms.addAppointment(tfDate.getText(), appointmentTime, jcDoctors.getSelectedItem().toString());
								//display confirmation message
								Object[] options = {"Ok"};
								JOptionPane.showOptionDialog(null, "Appointment added.", "Success",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
								null, options, options[0]);
								//go back to previous menu
								clearTextField();
								//Resetting dropdown selection fields to default
								jcStartTime.setSelectedIndex(0);
								jcEndTime.setSelectedIndex(0);
								clearRedField();
								//Resetting dropdown selection fields' colour
								jcStartTime.setBackground(Default);
								jcEndTime.setBackground(Default);
								
								
								hms.displayPatientListPage();
							}
							
							
							//Otherwise, find out which field(s) are incorrect
							else 
							{
								//Checking if date input isn't valid
								if(!val.validateDate(tfDate.getText()))
								{
									tfDate.setBackground(Red);
									errorMessage += "Please ensure the entered date is correctly formatted: (DD/MM/YYYY) and does not form a date in the past.\n";
								}
								
								//Checking if time input isn't valid
								if(!val.validateTime(jcStartTime.getSelectedItem().toString(), jcEndTime.getSelectedItem().toString()))
								{
									jcStartTime.setBackground(Red);
									jcEndTime.setBackground(Red);
									errorMessage += "Please ensure selected times are valid. \n";
								}
								
								//Checking if time input is conflicting
								else if(!val.ConflictFreeAppointment(tfDate.getText(), jcStartTime.getSelectedItem().toString(), jcEndTime.getSelectedItem().toString(), 
										jcDoctors.getSelectedItem().toString()))
								{
									jcStartTime.setBackground(Red);
									jcEndTime.setBackground(Red);
									errorMessage += "Please ensure selected the times available for this doctor. \n";
								}
								
								Object[] options = {"Close"};
								
								
								JOptionPane.showOptionDialog(null, errorMessage, "Error",
								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
								null, options, options[0]);
							}
						}
						
						else
						{
							//display warning message if any fields are empty 
							Object[] options = {"Close"};
							JOptionPane.showOptionDialog(null, "Please fill in all information.", "Warning",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
						}
					}
				});
				/*
				 * VIEW SCHEDULE BUTTON
				 */
				JButton btnViewSchedule = new JButton("View Schedule");
				btnViewSchedule.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						//clearing all field colors and content
						clearRedField();
						clearTextField();
						//Setting selected doctor as logged in to view their schedule
						String name = jcDoctors.getSelectedItem().toString().replaceAll("\\s", "");
						hms.setLoggedInUser(name);
						hms.displayAprilCalendarPage();
					}
				});
				btnViewSchedule.setFont(new Font("Arial", Font.PLAIN, 13));
				btnViewSchedule.setBounds(1225, 517, 155, 20);
				// Adds all the components to the panel.
				addAppointmentPanel.add(lbDoctors);
				addAppointmentPanel.add(jcDoctors);
				addAppointmentPanel.add(lbDepartment);
				addAppointmentPanel.add(jcDepartment);
				addAppointmentPanel.add(lblDate);
				addAppointmentPanel.add(tfDate);
				addAppointmentPanel.add(lbStartTime);
				addAppointmentPanel.add(jcStartTime);
				addAppointmentPanel.add(lbEndTime);
				addAppointmentPanel.add(jcEndTime);
				addAppointmentPanel.add(btnSubmit);
				addAppointmentPanel.add(btnCancel);
				addAppointmentPanel.add(btnViewSchedule);
				addAppointmentPanel.add(lblWelcomeBackAdministrator);
				addAppointmentPanel.add(date);
				addAppointmentPanel.add(lblBackground);
				
				return addAppointmentPanel;
			}
			
			/**
			 * This method returns true if form is completely filled out
			 * (Need to redo due to changes from textfield to JComboBox)
			 */
			private boolean formComplete() 
			{
				//add all the text fields to an array
				ArrayList<JTextField> allTextFields = new ArrayList<JTextField>();
				allTextFields.add(tfDate);
				
				//loop through array checking if they are not empty
				for(JTextField t : allTextFields)
				{
					if(!(t.getText().length() > 0))
					{
						return false;
					}
				}
				return true;
			}
			
			/**
			 * This method resets all the text fields
			 */
			private void clearTextField() 
			{
				tfDate.setText("");
			}
			
			/**
			 * This method resets all the text field colors
			 */
			private void clearRedField()
			{
				tfDate.setBackground(Default);
			}
			/*
			 * This method updates the jcombobox for the department
			 */
			public void setDeptList() {
				jcDepartment.removeAllItems();
				String[] deptList = hms.getStringArray(hms.getDepartmentRecord());
				for (int i=0; i< deptList.length; i++) 
				{
					//Add the list of department into the combo box
					jcDepartment.addItem(deptList[i]);
				}
			}
}
