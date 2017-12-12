import java.util.*;
// import java.sql.*;

public class ParishSchedulerConsole {
	//private static ParishSchedulerControllerTest controller;
	private static Scanner kbd = new Scanner(System.in);

	public static boolean checkTime(String time) {
		boolean flag = false;
		String[] t = time.split(":");
		String[] ap = time.split(" ");
		
		if ( ((Integer.parseInt(t[0]) >= 7) && (ap[1].toUpperCase().equals("AM"))) || ((Integer.parseInt(t[0]) <= 6) 
				&& (ap[1].toUpperCase().equals("PM"))) ) {
			flag = true;
		}
		return flag;
	}
	
	public static String checkDay() {
		boolean flag = false;
		String str = kbd.nextLine();
		while (flag == false) {
			if (str.equalsIgnoreCase("Monday") || str.equalsIgnoreCase("Tuesday") || str.equalsIgnoreCase("Wednesday")  || 
				str.equalsIgnoreCase("Thursday") || str.equalsIgnoreCase("Friday") || str.equalsIgnoreCase("Saturday") || 
				str.equalsIgnoreCase("Sunday")) {
					flag = true;
			} else {
				System.out.println("Invalid day. (Monday, Tueday, Wednesday, Thursday, Firday, Saturday, Sunday)");
				System.out.print("Enter day : ");
				str = kbd.nextLine();
			}
		}
		return str;
	}
	
	public static String checkType() {
		return "";
	}
	
	public static void enterSchedule() {
		String startTime;
		do {
			System.out.print("Enter start time (Ex. 7:00 AM): ");
			startTime = kbd.nextLine();
		} while (checkTime(startTime.toUpperCase()) == false);
		
		System.out.print("Enter day (Ex. Monday): ");
		String day = checkDay();
		
		System.out.print("Enter mass type : ");
		String massType = kbd.nextLine();
		
		System.out.print("Enter Priest name (Ex. Fr. Sales): ");
		String priest = kbd.nextLine();
		
		/*
		try {
			controller.createSchedule(startTime, day, massType, priest);
			System.out.println("New schedule was successfully added.");
			System.out.println();
			System.out.println("Summary of Schedule that added");
			System.out.println("Mass start Time: " + startTime);
			System.out.println("Day of mass: " + day);
			System.out.println("Type of mass : " + massType);
			System.out.println("Mass Priest : " + priest);
		} catch(MySQLIntegrityConstraintViolationException x) {
			// code for checking if schedule is already exist
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
	}
	
	public static int showParishMenu() {
		int choice;
		System.out.println("========================================================");
		System.out.printf("%1s%29s%27s", "=", "MENU", "=\n");
		System.out.println("========================================================");
		System.out.printf("%1s%5s%34s", "= ", "1. View mass Schedule", "=\n");
		System.out.printf("%1s%5s%25s", "= ", "2. Schedule intention for mass", "=\n");
		System.out.printf("%1s%5s%32s", "= ", "3. View Priest Schedule", "=\n");
		System.out.printf("%1s%5s%32s", "= ", "4. Create New Schedule ", "=\n");
		System.out.printf("%1s%5s%47s", "= ", "5. Exit ", "=\n");
		System.out.println("========================================================");
		System.out.print("  Enter choice: ");
		choice = kbd.nextInt();
		return choice;
	}

	public static void run() {
		int choice;
		choice = showParishMenu();
		switch(choice){
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				System.out.println("========================================================");
				System.out.println("                   Enter New Schedule");
				System.out.println("========================================================");
				kbd.nextLine();
				enterSchedule();
				System.out.println("========================================================");
				break;
			case 5:
				System.out.println("Thank you for using our program.");
				System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		ParishSchedulerConsole psct;
		//controller = new ParishSchedulerControllerTest();
		try {   
			//String url = "jdbc:mysql://localhost/contact?useSSL=false";
			//controller.dbaseConnect(url,"root",null);
			psct = new ParishSchedulerConsole();
			 psct.run();
	     } catch (Exception e) {
	    	 System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
	     }
	}
}
