import java.util.*;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.*;

public class ParishSchedulerConsole {
	private static ParishSchedulerController controller;
	private static Scanner kbd = new Scanner(System.in);

	public static void main(String[] args) {
		ParishSchedulerConsole psct;
		controller = new ParishSchedulerController();
		try {
			String url = "jdbc:mysql://localhost/intentionmass?useSSL=false";
			controller.dbaseConnect(url, "root", null);
			psct = new ParishSchedulerConsole();
			psct.run();
		} catch (SQLException e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		} finally {
			controller.close();
		}
	}

	public static int showParishMenu() {
		int choice;
		System.out.println("========================================================");
		System.out.printf("%1s%29s%27s", "=", "MENU", "=\n");
		System.out.println("========================================================");
		System.out.printf("%1s%5s%34s", "= ", "1. View mass Schedule", "=\n");
		System.out.printf("%1s%5s%32s", "= ", "2. View Priest Schedule", "=\n");
		System.out.printf("%1s%5s%25s", "= ", "3. Schedule intention for mass", "=\n");
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
		switch (choice) {
		case 1:
			try {
				printMassSched(controller.getMassSched());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			break;
		case 3: 
			scheduleIntention();
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

	private static void printMassSched(ResultSet rs) {
		try {
			if (getResTotal(rs) == 0) {
				System.out.println("Error: name not found!!!");
			} else {
				System.out.printf("     %-12s %-10s %-20s %-15s %n", "Date", "Time", "Scheduled Priest", "Type");
				int row = 1;
				while (rs.next()) {
					String time = rs.getString("time");
					String date = rs.getString("date");
					String name = rs.getString("name");
					String type = rs.getString("mass_type");
					System.out.printf("%-4d %-12s %-10s %-20s %-15s %n", row++, date, time, name, type);
				}
			}

			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}

	public static void enterSchedule() {
		String startTime;
		boolean notValid = false;
		do {
			System.out.print("Enter start time (Ex. 7:00, 16:00): ");
			startTime = kbd.nextLine();
			if (!startTime.matches("[0-9]{1,2}:[0-9]{2}")) {
				notValid = true;
				System.out.println("Not a valid time!");
			} else {
				startTime += ":00";
				notValid = false;
			}
		} while (notValid);

		String date;
		do {
			System.out.print("Enter date (YYYY-MM-DD): ");
			date = kbd.nextLine();
			if (!date.matches("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")) {
				notValid = true;
				System.out.println("Not a valid date!");
			} else {
				notValid = false;
			}
		} while (notValid);

		System.out.print("Enter mass type : ");
		String massType = kbd.nextLine();

		String priest;
		ResultSet rs = null;
		do {
			System.out.print("Enter Priest name (Ex. Sales, Gilbert): ");
			priest = kbd.nextLine();
			String[] name = priest.split(",");
			try {
				rs = controller.getPriestInfo(name[0].trim(), name[1].trim());
				if (getResTotal(rs) == 0) {
					System.out.println("Priest not found. Please enter an existing priest.");
					notValid = true;
				} else {
					notValid = false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (notValid);

		ResultSet schedRs = null;
		try {
			schedRs = controller.getAllSched();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String schedID = incrementID(schedRs, "S");

		try {
			rs.next();
			controller.createSchedule(schedID, startTime, date, massType, rs.getString("priest_id"));
			System.out.println("New schedule was successfully added.");
			System.out.println();
			System.out.println("Summary of Schedule that added");
			System.out.println("Mass start Time: " + startTime);
			System.out.println("Day of mass: " + date);
			System.out.println("Type of mass : " + massType);
			System.out.println("Mass Priest : " + priest);
		} catch (MySQLIntegrityConstraintViolationException x) {
			// code for checking if schedule is already exist
			System.out.println("Schedule already exist");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String incrementID(ResultSet rs, String startingLetter) {
		String iD = "";
		try {
			if (getResTotal(rs) == 0) {
				iD += "001";
			} else {
				rs.last();
				iD = rs.getString(1);
				iD = startingLetter + String.format("%03d", (Integer.parseInt(iD.substring(1)) + 1));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return iD;
	}

	public static void scheduleIntention() {
		String time;
		String schedID = "";
		String kind = "";
		String to = "";
		String message = "";
		String intentionID = "";
		boolean notValid = false;
		do {
			do {
				System.out.print("Enter time of mass for the intention(Ex. 7:00, 16:00): ");
				time = kbd.next();
				if (!time.matches("[0-9]{1,2}:[0-9]{2}")) {
					notValid = true;
					System.out.println("Not a valid time!");
				} else {
					time += ":00";
					notValid = false;
				}
			} while (notValid);

			String date;
			do {
				System.out.print("Enter date of mass for the intention(YYYY-MM-DD): ");
				date = kbd.next();
				if (!date.matches("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")) {
					notValid = true;
					System.out.println("Not a valid date!");
				} else {
					notValid = false;
				}
			} while (notValid);

			ResultSet rs = null;

			try {
				rs = controller.searchMassSched(time, date);
				if (getResTotal(rs) == 0) {
					System.out.println("The schedule doesn't exist");
					notValid = true;
				} else {
					notValid = false;
					rs.next();
					schedID = rs.getString(1);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (notValid);

		System.out.print("For whom this intentions is (ex. Juan Dela Cruz): ");
		to = kbd.nextLine();
		System.out.print("Kind of intention : ");
		kind = kbd.nextLine();
		System.out.print("Message : ");
		message = kbd.nextLine();
		ResultSet rs = null;

		try {
			rs = controller.getAllIntention();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		intentionID = incrementID(rs, "I");

		try {
			controller.createIntention(intentionID, kind, to, message);
			controller.createMassIntention(intentionID, schedID);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Successfully added.");
	}

	private static int getResTotal(ResultSet rs) throws Exception {
		int count = 0;
		rs.last();
		count = rs.getRow();
		rs.beforeFirst();
		return count;
	}

}
