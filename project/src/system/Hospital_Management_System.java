package system;

import java.util.ArrayList;
import javax.swing.JPanel;
import user.Patient;
import gui.AddAppointmentPanel;
import gui.AddPatientPanel;
import gui.AdminMainPanel;
import gui.GUI;
import gui.LoginPanel;
import gui.PatientInfoPanel;
import gui.PatientListPanel;
import gui.PatientManagePanel;
import gui.PatientViewAppointmentPanel;

/*
 * This class contains the logic for the hospital manage system.
 */
public class Hospital_Management_System 
{
	//list of all the patients in the system
	ArrayList<Patient> patientRecord = new ArrayList<Patient>();
	//initialize GUI
	private	JPanel loginPage = new LoginPanel().createPanel(this);
	private	JPanel addPatientPage = new AddPatientPanel().createPanel(this);
	private JPanel patientManagementPage = new PatientManagePanel().createPanel(this);
	private JPanel adminMainPage = new AdminMainPanel().createPanel(this);
	private JPanel patientViewAppointmentPage = new PatientViewAppointmentPanel().createPanel(this);
	private JPanel patientInfoPage = new PatientInfoPanel().createPanel(this);
	private JPanel addAppointmentPage = new AddAppointmentPanel().createPanel(this);
	private PatientListPanel plp = new PatientListPanel();
	private JPanel patientListPage = plp.createPanel(this);
	
	public Integer id;
	/**
	 * This constructor starts the system.
	 */
	public Hospital_Management_System()
	{
		new GUI(loginPage, addPatientPage, patientManagementPage, adminMainPage, 
		patientViewAppointmentPage, patientInfoPage, addAppointmentPage, patientListPage);
	}
	/**
	 * This method will run the HMS.
	 */
	public void startHMS() 
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {public void run() {}});
		displayLoginPage();
	}
	/**
	 * This method will change the gui to display the login page.
	 */
	public void displayLoginPage() 
	{
		loginPage.setVisible(true);
		adminMainPage.setVisible(false);
		patientViewAppointmentPage.setVisible(false);
	}	
	/**
	 * This method will change the gui to display the admin main page.
	 */
	public void displayAdminMainPage() 
	{
		adminMainPage.setVisible(true);
		loginPage.setVisible(false);
		patientManagementPage.setVisible(false);
	}
	/**
	 * This method will change the gui to display the patient management page.
	 */
	public void displayPatientManagementPage() 
	{
		patientManagementPage.setVisible(true);
		adminMainPage.setVisible(false);
		addPatientPage.setVisible(false);
		patientInfoPage.setVisible(false);
		patientListPage.setVisible(false);
		addAppointmentPage.setVisible(false);
	}
	/**
	 * This method will change the gui to display the add patient page.
	 */
	public void displayAddPatientPage() 
	{
		addPatientPage.setVisible(true);
		patientManagementPage.setVisible(false);
	}
	/**
	 * This method will change the gui to display the patient main page.
	 */
	public void displayViewAppointmentPage() 
	{
		patientViewAppointmentPage.setVisible(true);
		loginPage.setVisible(false);
	}
	/**
	 * This method will change the gui to display the patient info page.
	 */
	public void displayPatientInfoPage()
	{
		patientInfoPage.setVisible(true);
		patientManagementPage.setVisible(false);
	}
	/**
	 * This method will change the gui to display the patient list page.
	 */
	public void displayPatientListPage() 
	{
		patientListPage.setVisible(true);
		patientManagementPage.setVisible(false);
		addAppointmentPage.setVisible(false);
	}
	/**
	 * This method will change the gui to display the add appointment page.
	 */
	public void displayAddAppointmnetPage()
	{
		addAppointmentPage.setVisible(true);
		patientListPage.setVisible(false);
	}
	/**
	 * This method will add a new patient to the patient records.
	 * @param patient The patient to be added.
	 */
	public void addPatient(Patient patient)
	{
		//assign the next available id number to patient
		patient.setId(patientRecord.size() + 1);
		//add to records
		patientRecord.add(patient);
		plp.addPatientToTable(patient, this);
	}
	/**
	 * This method will add an appointment for a patient
	 * @param date The date of the appointment
	 * @param time The time of the appointment
	 */
	public void addAppointment(String date, String time)
	{
		patientRecord.get(id-1).setAppointment(date, time);
	}
	/**
	 * This method returns the appointment date.
	 * @param id The patient id
	 */
	public String getAppointmentDate(String id)
	{
		return patientRecord.get(Integer.parseInt(id)-1).getAppointmentDate();
	}
	/**
	 * This method returns the appointment time.
	 * @param id The patient id
	 */
	public String getAppointmentTime(String id)
	{
		return patientRecord.get(Integer.parseInt(id)-1).getAppointmentTime();
	}
	/**
	 * This method returns true when patient id is valid
	 * @param id The patient id
	 */
	public boolean patientIdValid(String id)
	{
		return allDigits(id) && (Integer.parseInt(id)) <= patientRecord.size() ? true : false;
	}
	/**
	 * This method checks the inputed string and determines whether it contains only digits
	 * Returns true when string contains all digits, false otherwise
	 * @param s This is the string being checked
	 */
	public boolean allDigits(String s) 
	{
		boolean noDigits = true;
		for (int index = 0; index < s.length(); index++)
		{
			char aChar = s.charAt(index);
			if (!Character.isDigit(aChar))
			{
				noDigits = false;
			}
		}
		return noDigits;
	}
}
