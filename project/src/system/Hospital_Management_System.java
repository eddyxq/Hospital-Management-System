package system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import database.TextReader;
import database.TextWriter;
import user.Employee;
import user.Patient;
import gui.*;

/*
 * This class contains the logic for the hospital management system. 
 */
public class Hospital_Management_System 
{	
	//list of all the departments in the system
	private ArrayList<String> departmentRecord = new ArrayList<String>();
	//list of all the patients in the system
	private ArrayList<Patient> patientRecord = new ArrayList<Patient>();
	//list of all the patients in the system
	private ArrayList<Employee> employeeRecord = new ArrayList<Employee>();
	//lists of all the appointments in the system
	private ArrayList<Appointment> appointmentRecord = new ArrayList<Appointment>();
	
	//initialize GUI
	private	JPanel addPatientPage = new AddPatientPanel().createPanel(this);
	private JPanel patientManagementPage = new PatientManagePanel().createPanel(this);
	private JPanel staffManagementPage = new StaffManagePanel().createPanel(this);
	private JPanel adminMainPage = new AdminMainPanel().createPanel(this);
	private JPanel employeeMainPage = new EmployeeMainPanel().createPanel(this);
	private JPanel homePage = new HomePanel().createPanel(this);
	private AddAppointmentPanel aap = new AddAppointmentPanel();
	private JPanel addAppointmentPage;
	private EditAppointmentPanel eap = new EditAppointmentPanel();
	private JPanel editAppointmentPage;
	private PatientListPanel plp = new PatientListPanel();
	private JPanel patientListPage = plp.createPanel(this);
	private EmployeeListPanel slp = new EmployeeListPanel();
	private JPanel staffListPage = slp.createPanel(this);
	private AddStaffPanel asp = new AddStaffPanel();
	private JPanel addStaffPage;
	private JPanel loginPage = new LoginPanel().createPanel(this);
	private JPanel marchCalendarPage = new MayCalendarPanel().createPanel(this);
	private JPanel aprilCalendarPage = new AprilCalendarPanel().createPanel(this);
	private AppointmentListPanel alp = new AppointmentListPanel();
	private JPanel appointmentListPage = alp.createPanel(this);
	private AppointmentListPanelAdmin alap = new AppointmentListPanelAdmin();
	private JPanel appointmentListPageAdmin = alap.createPanel(this);
	private JPanel manageAppointmentPage = new ManageDepartmentPanel().createPanel(this);
	private JPanel addDepartmentPage = new AddDepartmentPanel().createPanel(this);
	private JPanel departmentStatisticsPage;
	
	//variables for identifying users
	private boolean admin;
	private String accessFrom;
	private Integer id;
	private String loggedInUser;
	private Appointment currentAppointment;
	/**
	 * This constructor starts the system.
	 */
	public Hospital_Management_System()
	{
		//retrieve saved data
		loadData();
		
		addStaffPage = asp.createPanel(this);
		addAppointmentPage = aap.createPanel(this);
		editAppointmentPage = eap.createPanel(this);
		departmentStatisticsPage = new DepartmentStatisticsPanel().createPanel(this);
		
		//start user interface
		new GUI(addPatientPage, patientManagementPage, adminMainPage, 
		homePage, addAppointmentPage, patientListPage,
		addStaffPage, staffManagementPage, staffListPage, loginPage, 
		employeeMainPage, marchCalendarPage, aprilCalendarPage, appointmentListPage, appointmentListPageAdmin,
		manageAppointmentPage, addDepartmentPage, editAppointmentPage, departmentStatisticsPage);
		//saves date on exit
		Runtime.getRuntime().addShutdownHook(onExit());
	}
	/**
	 * This method will restore the data saved from text file
	 */
	private void loadData() 
	{
		departmentRecord = new TextReader().loadDepartmentData();
		patientRecord = new TextReader().loadPatientData();
		employeeRecord = new TextReader().loadEmployeeData();
		appointmentRecord = new TextReader().loadAppointmentData();
		for(Patient p : patientRecord)
		{
			plp.addPatientToTable(p, this);
		}
		for(Employee e : employeeRecord)
		{
			slp.addEmployeeToTable(e, this);
		}
		
	}
	private void loadAppointmentToList() {
		alap.clearAllRow();
		alp.clearAllRow();
		for(Appointment app : appointmentRecord) 
		{
			if(app.getID().equals(getId().toString()) && app.getPName().equals(patientRecord.get(getId()-1).getName())) 
			{
				alap.addAppointmentToTable(app, this);
				alp.addAppointmentToTable(app, this);
			}
		}
	}
	
	/**
	 * This method creates and returns a thread that is executed
	 * when the program is closed allowing the system to save
	 * the patient records to a text file on exit
	 */
	private Thread onExit()
	{
		return new Thread() {public void run() 
		{	
			new TextWriter().saveDepartmentData(departmentRecord);
			new TextWriter().savePatientData(patientRecord);
			new TextWriter().saveEmployeeData(employeeRecord);
			new TextWriter().saveLoginInfo(employeeRecord);
			new TextWriter().saveAppointmentData(appointmentRecord);
		}};
	}
	/**
	 * This method will run the HMS.
	 */
	public void startHMS() 
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {public void run() {}});
		displayHomePage();
	}
	/**
	 * This method will change the gui to display the username and password prompt for staffs
	 */
	public void displayLoginPage()
	{
		hideAll();
		loginPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the admin main page.
	 */
	public void displayAddDepartmentPage() 
	{
		hideAll();
		addDepartmentPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the department statistics page.
	 */
	public void displayDepartmentStatisticsPage() 
	{
		hideAll();
		departmentStatisticsPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the add department page.
	 */
	public void displayManageDepartmentPage() 
	{
		hideAll();
		manageAppointmentPage.setVisible(true);
	}
	public void displayEditAppointmentPage()
	{
		hideAll();
		editAppointmentPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the admin main page.
	 */
	public void displayAdminMainPage() 
	{
		hideAll();
		adminMainPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the employee main page.
	 */
	public void displayEmployeeMainPage() 
	{
		hideAll();
		employeeMainPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the employee calendar page.
	 */
	public void displayMayCalendarPage() 
	{
		hideAll();
		marchCalendarPage.setVisible(true);
	}
	public void displayAprilCalendarPage() 
	{
		hideAll();
		aprilCalendarPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the patient management page.
	 */
	public void displayPatientManagementPage() 
	{
		hideAll();
		patientManagementPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the staff management page.
	 */
	public void displayStaffManagementPage() 
	{
		hideAll();
		staffManagementPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the add patient page.
	 */
	public void displayAddPatientPage() 
	{
		hideAll();
		addPatientPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the patient main page.
	 */
	public void displayHomePage() 
	{
		hideAll();
		homePage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the appointment list page.
	 */
	public void displayAppointmentListPage() 
	{
		hideAll();
		loadAppointmentToList();
		appointmentListPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the appointment list page for admin.
	 */
	public void displayAppointmentListPageAdmin() 
	{
		hideAll();
		loadAppointmentToList();
		appointmentListPageAdmin.setVisible(true);
	}
	/**
	 * This method will change the gui to display the patient list page.
	 */
	public void displayPatientListPage() 
	{
		hideAll();
		patientListPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the staff list page.
	 */
	public void displayStaffListPage()
	{
		hideAll();
		staffListPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the add appointment page.
	 */
	public void displayAddAppointmentPage()
	{
		hideAll();
		
		addAppointmentPage.setVisible(true);
	}
	/**
	 * This method will change the gui to display the add staff page.
	 */
	public void displayAddStaffPage()
	{
		hideAll();
		addStaffPage.setVisible(true);
	}
	
	public ArrayList<Appointment> getAppointmentRec()
	{
		return appointmentRecord;
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
	 * This method will add a new patient to the patient records.
	 * @param patient The patient to be added.
	 */
	public void addEmployee(Employee employee)
	{
		//assign the next available id number to patient
		employee.setId(employeeRecord.size() + 1);
		//add to records
		employeeRecord.add(employee);
		slp.addEmployeeToTable(employee, this);
	}
	/**
	 * This method will add an appointment for a patient
	 * @param date The date of the appointment
	 * @param time The time of the appointment
	 */
	public void addAppointment(String date, String time, String doctor)
	{
		appointmentRecord.add(new Appointment(getId()+"", patientRecord.get(getId()-1).getName(), 
								doctor, date, time));
	}
	/**
	 * This method returns the appointment date.
	 * @param id The patient id
	 */
	public boolean hasAppointment(String patientId)
	{
		boolean flag = false;
		for(Appointment app : appointmentRecord) 
		{
			if(app.getID().equals(patientId)) 
			{
				flag = true;
			}
		}
		return flag;
	}
	/**
	 * This method returns true when patient id is valid
	 * @param id The patient id
	 */
	public boolean patientIdValid(String id, String lastName)
	{
		if(new ValidateInput().allDigits(id) && (Integer.parseInt(id)) <= patientRecord.size())
		{
			if(patientRecord.get((Integer.parseInt(id))-1).getLastName().equals(lastName))
			{
				return true;
			}
		}
			return false;
	}
	/**
	 * This method will return the dates and times of patient appointments.
	 * @param currentDay The current day
	 * @param currentMonth The current month
	 * @param currentYear The current year
	 */
	public ArrayList<String> generateDaySchedule(String currentDay, String currentMonth, String currentYear)
	{
		ArrayList<Appointment> appointments = appointmentRecord;
		ArrayList<String> appointmentData = new ArrayList<String>();
		String names = "";
		String times = "";
		
		ArrayList<Appointment> currentDayAppointments = new ArrayList<Appointment>();
		
		try 
		{
			//go through and find all the current day appointments with this doctor
			for(Appointment a : appointments)
			{
					String[] date = a.getDate().split("/");
					String day = date[0];
					String month = date[1];
					String year = date[2];
	
					if ((Integer.parseInt(day) == Integer.parseInt(currentDay)) && 
						(Integer.parseInt(month) == Integer.parseInt(currentMonth)) && 
						(Integer.parseInt(year) == Integer.parseInt(currentYear)) && 
						(a.getDocNameNoSpace().equals(loggedInUser)))
					{
						//add these appointments to a new list
						currentDayAppointments.add(a);
					}		
			}
			//list if the starting appointment times
			ArrayList<Integer> timeList = new ArrayList<Integer>();
			//list containing the sorted appointments
			ArrayList<Appointment> sortedAppointments = new ArrayList<Appointment>();
			//maps the starting appointment time to the appointment
			Map<Integer, Appointment> map = new HashMap<Integer, Appointment>();
			//sort the times from early to late
			for(Appointment a : currentDayAppointments)
			{
				String time = a.getTime();
				int startTime = Integer.parseInt("" + time.charAt(0) + time.charAt(1) + time.charAt(3) + time.charAt(4)) ;
				map.put(startTime, a);
				timeList.add(startTime);		
			}
			Collections.sort(timeList);
			//add the appointments in sorted order
			for(int i = 0; i < timeList.size(); i++)
			{
				sortedAppointments.add(map.get(timeList.get(i)));
			}
			//create output string
			for(Appointment a : sortedAppointments)
			{
				names += a.getPName() + "<br/>";
				times += a.getTime() + "<br/>";
			}
			names = "<html>" + names + "</html>"; 
			times = "<html>" + times + "</html>"; 
			
			appointmentData.add(names);
			appointmentData.add(times);
		
			return appointmentData;
		}
		catch(Exception e)
		{
			ArrayList<String> empty = new ArrayList<String>();
			empty.add("");
			empty.add("");
			return empty;
		}
	}
	/**
	 * This method will hide all the visible panels.
	 */
	private void hideAll()
	{
		addPatientPage.setVisible(false);
		patientManagementPage.setVisible(false);
		staffManagementPage.setVisible(false);
		adminMainPage.setVisible(false);
		employeeMainPage.setVisible(false);
		homePage.setVisible(false);
		addAppointmentPage.setVisible(false);
		patientListPage.setVisible(false);
		staffListPage.setVisible(false);
		addStaffPage.setVisible(false);
		loginPage.setVisible(false);
		marchCalendarPage.setVisible(false);
		aprilCalendarPage.setVisible(false);
		appointmentListPage.setVisible(false);
		appointmentListPageAdmin.setVisible(false);
		manageAppointmentPage.setVisible(false);
		addDepartmentPage.setVisible(false);
		editAppointmentPage.setVisible(false);
		departmentStatisticsPage.setVisible(false);
	}
	/**
	 * This method will return the id.
	 */
	public Integer getId() 
	{
		return id;
	}
	/**
	 * This method set the id.
	 * @param id The ID.
	 */
	public void setId(Integer id) 
	{
		this.id = id;
	}
	/**
	 * This method will return departmentRecord.
	 */
	public ArrayList<String> getDepartmentRecord() 
	{
		return departmentRecord;
	}
	/**
	 * This method will return employeeRecord.
	 * @param department The department the doctors belong to.
	 */
	public String[] getDoctorsInDepartment(String department) 
	{
		
		ArrayList<String> doctorsInThisDepartment = new ArrayList<String>();
		
		for(Employee e : employeeRecord)
		{
			if (e.department.equals(department))
			{
				doctorsInThisDepartment.add(e.firstName + " " + e.lastName);
			}
		}
		return getStringArray(doctorsInThisDepartment);
	}
	/**
	 * This method will convert a array list of strings to array.
	 * @param arraylist The array list to be converted.
	 */
    public String[] getStringArray(ArrayList<String> arraylist) 
    { 
        String arr[] = new String[arraylist.size()]; 
        for (int i = 0; i < arraylist.size(); i++) 
        { 
            arr[i] = arraylist.get(i); 
        } 
        return arr; 
    } 
    /*
     * This method will return the list of employees.
     */
	public ArrayList<Employee> getEmployeeRecord() 
	{
		return employeeRecord;
	}
    /*
     * This method will return the current appointment.
     */
    public Appointment getCurrentAppointment() 
    {
		return currentAppointment;
	}
	/**
	 * This method will return the user currently logged in.
	 */
	public String getLoggedInUser() 
	{
		return loggedInUser;
	}
	/**
	 * This method will set the logged in user.
	 * @param loggedInUser The user currently using the system.
	 */
	public void setLoggedInUser(String loggedInUser) 
	{
		this.loggedInUser = loggedInUser;
	}
	/**
	 * This method will return true when is admin.
	 */
	public boolean isAdmin() 
	{
		return admin;
	}
	/**
	 * This method will set status of admin.
	 * @param admin A boolean variable.
	 */
	public void setAdmin(boolean admin) 
	{
		this.admin = admin;
	}
	/**
	 * This method will return accessFrom.
	 */
	public String getAccessFrom() 
	{
		return accessFrom;
	}
	/**
	 * This method set the id.
	 * @param accessFrom The type of employee that last accessed.
	 */
	public void setAccessFrom(String accessFrom) 
	{
		this.accessFrom = accessFrom;
	}
	/**
	 * This method will return the addAppointmentPanel.
	 */
	public AddAppointmentPanel getAAP() 
	{
		return aap;
	}
	/**
	 * This method will return the addStaffPanel.
	 */
	public AddStaffPanel getASP() 
	{
		return asp;
	}
	/**
	 * This method will return the editAppointmentPanel.
	 */
	public EditAppointmentPanel getEAP() 
	{
		return eap;
	}
	/**
	 * This method will set the current in appointment.
	 * @param app The appointment currently being edited in the system.
	 */
	public void setCurrentAppointment(Appointment currentAppointment) 
	{
		this.currentAppointment = currentAppointment;
	}
	/**
	 * This method will return the number of doctors in a given department.
	 * @param department The department the doctors works for.
	 */
	public int countDoctorsInDepartment(String department)
	{
		int count = 0;
		
		for(Employee e : employeeRecord)
		{
			if(e.getDepartment().equals(department))
			{
				count += 1;
			}
		}
		return count;
	}
}
