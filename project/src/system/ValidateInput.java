package system;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import database.TextReader;

/**
 * This class is used to ensure all input is valid 
 */

public class ValidateInput 
{	
	//lists of all the appointments in the system (holds appointments added during this session
	ArrayList<Appointment> appointmentTemp = new ArrayList<Appointment>();
			
	/*
	 * This method returns true if all entries of a patient form are valid
	 * @param firstName The first name.
	 * @param lastName The last name.
	 * @param day The day.
	 * @param month The month.
	 * @param year The year.
	 * @param phoneNumber The phone number.
	 * @param email The email.
	 */
	public boolean validatePatient(String firstName, String lastName, String day, String month, String year, String phoneNumber, String email)
	{
		if(!this.validateName(firstName) || !this.validateName(lastName) || !this.validateDay(day, month, year) || 
		!this.validateMonth(month) || !this.isPastYear(year) || !this.validatePhone(phoneNumber) || !this.validateEmail(email))
		{
			return false;
		}
		return true;
	}
	/*
	 * This method returns true if all elements of an appointment form are valid.
	 * @param date The date.
	 * @param time The time.
	 */
	public boolean validateAppointment(String date, String timeStart, String timeEnd, String docName)
	{
		if(!this.validateDate(date) || !this.validateTime(timeStart, timeEnd))
		{
			return false;
		}	
		//Only checked if the date and time are otherwise valid
		if(!this.ConflictFreeAppointment(date, timeStart, timeEnd, docName))
		{
			return false;
		}
		return true;
	}
	/*
	 * This method returns true if a name entry is valid.
	 * @param name The name of person.
	 */
	public boolean validateName(String name)
	{
		//Ensuring a name entry is all letters
		char[] check = name.toCharArray();
		for(char f : check)
		{
			if (!Character.isLetter(f))
			{
				return false;
			}
		}	
		return true;
	}
	/*
	 * This method returns true if the day entry is valid.
	 * @param day The day.
	 * @param month The month.
	 * @param year The year.
	 */
	public boolean validateDay(String day, String month, String year)
	{
		//check if month is valid
		if(!this.validateMonth(month))
		{
			return false;
		}
		//check if day is valid
		if(day.length() != 2 || !day.matches("[0-9]+") || Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31)
		{
			return false;
		}
		
		int theDay = 0, theMonth = 0, theYear = 0;
		
		try 
		{
			theDay = Integer.parseInt(day);
			theMonth = Integer.parseInt(month);
			theYear = Integer.parseInt(year);
		} 
		catch (NumberFormatException nfe) 
		{
			//If the current date (despite passing other validations cannot be parsed as an int
		}
		
		Calendar cal = new GregorianCalendar();
	
		//Default values will cause validation to return false
		if(theDay == 0 || theMonth == 0 || theYear == 0)
		{
			return false;
		}
		
		//Ensures the calendar does not accept invalid dates
		cal.setLenient(false);
		cal.set(theYear, theMonth-1, theDay);
		
		try 
		{
			cal.getTime();
		}
		catch(IllegalArgumentException iae)
		{
			//Invalid date, determined by calendar method; return false
			return false;
		}
		return true;
	}
	/*
	 * This method returns true if the month entry is valid.
	 * @param month The month.
	 */
	public boolean validateMonth(String month)
	{
		//Ensuring month entry is a number and meets length constraint
		if(month.length() != 2 || !month.matches("[0-9]+") || Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12)
		{
			return false;
		}
		return true;
	}
	/*
	 * This method returns true if the year entry is valid.
	 * @param year The year.
	 */
	public boolean isFutureYear(String year)
	{
		//Getting value of current year to compare entry 
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		//Ensuring year entry is a number and meets length constraint
		// also comparison to avoid registering patients with future birth date
		if(year.length() > 4 || !year.matches("[0-9]+") || Integer.parseInt(year) < 1 || Integer.parseInt(year) < thisYear)
		{
			return false;
		}
		//if no problems are found, then the year is valid
		return true;
	}
	/*
	 * This method returns true if the year of a patient's birth is valid.
	 * @param year The year.
	 */
	public boolean isPastYear(String year)
	{
		//Getting value of current year to compare entry 
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		//Ensuring year entry is a number and meets length constraint
		// also comparison to avoid registering patients with future birth date
		if(year.length() > 4 || !year.matches("[0-9]+") || Integer.parseInt(year) < 1 || Integer.parseInt(year) > thisYear)
		{
			return false;
		}
		//if no problems are found, then the year is valid
		return true;
	}
	/*
	 * This method returns true if the phone number entry is valid
	 * @param phoneNumber The phone number.
	 */
	public boolean validatePhone(String phoneNumber)
	{
		//Ensuring phone number entry is a number and meets length/content constraint
		String phoneCheck = phoneNumber.replaceAll("-", "");
		if(phoneCheck.length() != 10 || !phoneCheck.matches("[0-9]+"))
		{
			return false;
		}
		//if no problems are found, then email is valid
		return true;
	}
	/*
	 * This method returns true if the email entry is valid.
	 * @param email The email from the form.
	 */
	public boolean validateEmail(String email)
	{
		//Ensuring email entry contains @ symbol, and has at least length 9
		//(minimum length email accepted example) a@mail.ca 
	
		if(email.length() < 9 || !email.contains("@") || !email.contains("."))
		{
			return false;
		}
		//ensures there's no space
		if(email.contains(" "))
		{
			return false;
		}
		//ensures there's at most one @ sign
		int count = email.length() - email.replace("@", "").length();
		if(count != 1)
		{
			return false;
		}
		//if no problems are found, then email is valid
		return true;
	}
	/*
	 * This method returns true if the overall date entry is valid.
	 * @param date The string input from the form.
	 */
	public boolean validateDate(String date)
	{
		//Ensuring date entry contains / symbol, and has 3 parts (DD/MM/YYYY)
		if(!date.contains("/"))
		{
			return false;
		}
		//Splitting string by / signs
		String parts[] = date.split("/");		
		//If there aren't 3 parts to the date input, it isn't valid
		if(parts.length != 3)
		{
			return false;
		}
		//If the day or month are more than 2 digits, or the year is more than 4, it is invalid
		if(parts[0].length() != 2 || parts[1].length() != 2 | parts[2].length() != 4)
		{
			return false;
		}	
		//Finally, checking the input values for appropriate day, month, and year range
		if(!this.validateDay(parts[0], parts[1], parts[2]) || !this.validateMonth(parts[1]))
		{
			return false;
		}	
		//Getting value of current year to compare entry 
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		int thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; //Months start at 0
		int thisDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		//Prevent setting appointments in the past
		//Failure conditions described in order:
		//Year entry less than current year
		//Current year, month less than current month
		//Current year and month, day less than current day
		if(Integer.parseInt(parts[2]) < thisYear || 
		(Integer.parseInt(parts[2]) == thisYear && Integer.parseInt(parts[1]) < thisMonth) ||
		(Integer.parseInt(parts[2]) == thisYear && Integer.parseInt(parts[1]) == thisMonth
		&& Integer.parseInt(parts[0]) < thisDay))
		{
			return false;
		}
		//if no problems are found, then date is valid
		return true;
	}
	/*
	 * This method returns true if the time entry is valid
	 * @param time The string input from the form.
	 */
	public boolean validateTime(String timeStart, String timeEnd)
	{
		//Ensuring time entry contains : and fulfills length and value constraints (HH:MM)
		if(!timeStart.contains(":") || !timeEnd.contains(":"))
		{
			return false;
		}
		//first index of time array is hour
		//second index of time array is minute
		String startT[] = timeStart.split(":");
		String endT[] = timeEnd.split(":");
		//ensures only 2 arguments are given
		if(startT.length != 2 || endT.length != 2)
		{
			return false;
		}
		//check that the string's lengths are 2
		if(startT[0].length() != 2 || startT[1].length() != 2)
		{
			return false;
		}
		//check for valid hours
		if(Integer.parseInt(startT[0]) > 23 || Integer.parseInt(endT[0]) > 23 ||
			Integer.parseInt(startT[1]) > 59 || Integer.parseInt(endT[1]) > 59)
		{
			return false;
		}
		//If the start hour is greater than the end hour: appointment time is invalid
		if(Integer.parseInt(startT[0]) > Integer.parseInt(endT[0]))
		{
			return false;
		}
		//If the appointment starts and ends in same hour and start minute is greater than the end minute: appointment time is invalid
		if(Integer.parseInt(startT[0]) == Integer.parseInt(endT[0]) && (Integer.parseInt(startT[1]) > Integer.parseInt(endT[1])))
		{
			return false;
		}
		//If the appointment starts and ends at exactly the same time; the appointment time is invalid
		if(Integer.parseInt(startT[0]) == Integer.parseInt(endT[0]) && (Integer.parseInt(startT[1]) == Integer.parseInt(endT[1])))
		{
			return false;
		}
		//if no problems are found, then time is valid
		
		return true;
	}
	
	/**
	 * This method checks the input appointment parameters and checks them against the selected doctor's
	 * existing appointments saved for the given day.
	 * Returns true when the appointment is determined to not be the conflicting with any other appointment 
	 * on the same day for that doctor, false otherwise
	 * @param date This is the date being checked
	 * @param timeStart The proposed start time of the appointment
	 * @param timeEnd The proposed end time of the appointment
	 */
	public boolean ConflictFreeAppointment(String date, String timeStart, String timeEnd, String doctorName) 
	{
	
		//Generating all the appointments in the system as a list
		//lists of all the appointments in the system
		ArrayList<Appointment> appointmentRecord = new ArrayList<Appointment>();
		//Reading in data from the appointment records file
		appointmentRecord = new TextReader().loadAppointmentData();
		ArrayList<Appointment> appointments = appointmentRecord;
		//ArrayList to hold only the appointments of the current day
		ArrayList<Appointment> currentDayAppointments = new ArrayList<Appointment>();
				
		try 
		{
			//Splitting the date input by / to separate day, month and year
			String parts[] = date.split("/");	
			String thisDay = parts[0];
			String thisMonth = parts[1];
			String thisYear = parts[2];
			
			//Storing the input times as integers for comparison 
			int thisStart = Integer.parseInt("" + timeStart.charAt(0) + timeStart.charAt(1) + timeStart.charAt(3) + timeStart.charAt(4)); 
			int thisEnd = Integer.parseInt("" + timeEnd.charAt(0) + timeEnd.charAt(1) + timeEnd.charAt(3) + timeEnd.charAt(4));
			
			//Go through loaded record and find all the current day appointments with this doctor
			for(Appointment a : appointments)
			{
					//Splitting up the read in date
					String[] dateRead = a.getDate().split("/");
					String day = dateRead[0];
					String month = dateRead[1];
					String year = dateRead[2];
					
					//Ensuring the read in appointment is on the same date, by the same exact doctor
					if ((Integer.parseInt(day) == Integer.parseInt(thisDay)) && 
						(Integer.parseInt(month) == Integer.parseInt(thisMonth)) && 
						(Integer.parseInt(year) == Integer.parseInt(thisYear)) && 
						(a.getDocNameNoSpace().equals(doctorName.replaceAll("\\s+",""))))
					{
						//Add these appointments to a new list in order to parse times
						currentDayAppointments.add(a);
					}		
			}
			
			//Go through temporary record and find all the current day appointments with this doctor
			for(Appointment b : appointmentTemp)
			{
					//Splitting up the read in date
					String[] dateRead = b.getDate().split("/");
					String day = dateRead[0];
					String month = dateRead[1];
					String year = dateRead[2];
					
					//Ensuring the read in appointment is on the same date, by the same exact doctor
					if ((Integer.parseInt(day) == Integer.parseInt(thisDay)) && 
						(Integer.parseInt(month) == Integer.parseInt(thisMonth)) && 
						(Integer.parseInt(year) == Integer.parseInt(thisYear)) && 
						(b.getDocNameNoSpace().equals(doctorName.replaceAll("\\s+",""))))
					{
						//Add these appointments to a new list in order to parse times
						currentDayAppointments.add(b);
					}		
			}
			
			//list of the starting appointment times
			ArrayList<Integer> startList = new ArrayList<Integer>();
			ArrayList<Integer> endList = new ArrayList<Integer>();
			for(Appointment a : currentDayAppointments)
			{
				String time = a.getTime();
				
				//Storing the times read in from this appointment for comparison
				int startTime = Integer.parseInt("" + time.charAt(0) + time.charAt(1) + time.charAt(3) + time.charAt(4)) ;
				int endTime = Integer.parseInt("" + time.charAt(6) + time.charAt(7) + time.charAt(9) + time.charAt(10)) ;

				//Adding read in times to respective arraylists
				startList.add(startTime);
				endList.add(endTime);
			}
			
			//Looping through each start and end time to compare with the intended appointment times
			 for (int counter = 0; counter < startList.size(); counter++) 
			 { 		   
				  //If appointments start and end at the exact same time
		          if(startList.get(counter) == thisStart && endList.get(counter) == thisEnd)
		          {
		        	  return false;
		          }
		          
		          //If appointments start or end at the exact same time
		          if(startList.get(counter) == thisStart || endList.get(counter) == thisEnd)
		          {
		        	  return false;
		          }
		          
		          //If the proposed appointment takes place during an existing appointment
		          //If appointment starts after existing start even though existing appointment has not ended by the new start
		          if(startList.get(counter) < thisStart && endList.get(counter) > thisStart)
		          {
		        	  return false;
		          }
		     }   	
			 
		 	//If none of the conflicting cases were satisfied, appointment is conflict free
			 
		 	//adding to temporary list of appointments to ensure comparison until appointmentRecords updates
			String appointmentTime = timeStart + "-" + timeEnd;
			appointmentTemp.add(new Appointment(1 +"", "TempName", doctorName, date, appointmentTime));
			return true;
		}
				
		catch(Exception e)
		{
			ArrayList<String> empty = new ArrayList<String>();
			empty.add("");
			empty.add("");
		}
		//If the appointment file cannot be correctly parsed; shouldn't accept new appointments
		return false;
	}
	/**
	 * This method checks the inputed string and determines whether it contains only digits
	 * Returns true when string contains all digits, false otherwise
	 * @param str This is the string being checked
	 */
	public boolean allDigits(String str) 
	{
		boolean noDigits = true;
		for (int index = 0; index < str.length(); index++)
		{
			char aChar = str.charAt(index);
			if (!Character.isDigit(aChar))
			{
				noDigits = false;
			}
		}
		return noDigits;
	}
}
