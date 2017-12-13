import java.util.*;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.*;

public class ParishSchedulerConsole {
	private static ParishSchedulerController controller;
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		ParishSchedulerConsole psct;
		controller = new ParishSchedulerController();
		try {
			String url = "jdbc:mysql://localhost/intentionmass?useSSL=false";
			controller.dbaseConnect(url, "root", null);
			psct = new ParishSchedulerConsole();
			while (true) {
				psct.run();
				System.out.println("Press enter to continue...");
				scan.nextLine();
			}
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
		System.out.printf("%1s%5s%34s", "= ", "1. View Mass Schedule", "=\n");
		System.out.printf("%1s%5s%32s", "= ", "2. View Priest Schedule", "=\n");
		System.out.printf("%1s%5s%20s", "= ", "3. View Scheduled Intentions a mass", "=\n");
		System.out.printf("%1s%5s%32s", "= ", "4. Create New Schedule ", "=\n");
		System.out.printf("%1s%5s%25s", "= ", "5. Schedule intention for mass", "=\n");
		System.out.printf("%1s%5s%37s", "= ", "6. Add New Priest ", "=\n");
		System.out.printf("%1s%5s%45s", "= ", "7. Update ", "=\n");
		System.out.printf("%1s%5s%45s", "= ", "8. Delete ", "=\n");
		System.out.printf("%1s%5s%47s", "= ", "9. Exit ", "=\n");
		System.out.println("========================================================");
		System.out.print("  Enter choice: ");
		choice = scan.nextInt();
		scan.nextLine();
		System.out.println();
		return choice;
	}

	public static void updateMenu() {
		int choice;
		System.out.println("1. Update Mass Schedule");
		System.out.println("2. Update Mass Intension");
		System.out.print("Enter choice : ");
		choice = scan.nextInt();
		scan.nextLine();

		switch (choice) {
		case 1:

			break;
		case 2:

			break;
		default:
			System.out.println("Please choose from numbers 1 to 2.");
		}
	}

	public static void update() {
		int choice;

		do {

			System.out.println("1. Update Mass Schedule");
			System.out.println("2. Update Mass Intention");
			System.out.println("3. Cancel");
			System.out.print("Enter choice : ");
			choice = scan.nextInt();
			scan.nextLine();

			switch (choice) {
			case 1:
				updateMassSched();
				break;
			case 2:
				break;
			case 3:
				break;
			default:
				System.out.println();
				System.out.println("Please choose from numbers 1 to 3.");
			}
		} while (choice < 1 || choice > 3);
	}

	public static void deleteMenu() {
		int choice;

		do {
			System.out.println("1. Remove Priest");
			System.out.println("2. Delete Mass Schedule");
			System.out.println("3. Cancel");
			System.out.print("Enter choice : ");
			choice = scan.nextInt();
			scan.nextLine();

			switch (choice) {
			case 1:
				removePriest();
				break;
			case 2:
				removeMassSched();
				break;
			case 3:
				break;
			default:
				System.out.println();
				System.out.println("Please choose from numbers 1 to 3.");
			}

		} while (choice < 1 || choice > 3);

	}

	public static void run() {
		int choice;
		ResultSet rs = null;
		do {
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
				viewPriestSched();
				break;
			case 3:
				printMassIntention(rs);
				break;
			case 4:
				enterSchedule();
				break;
			case 5:
				System.out.println("Do you want to create new intention?");
				String answer = scan.nextLine();
				if (answer.equalsIgnoreCase("yes") | answer.equalsIgnoreCase("y")) {
					scheduleNewIntention();
				} else {
					scheduleIntention();
				}

				break;
			case 6:
				addNewPriest();
				break;
			case 7:
				update();
				break;
			case 8:
				deleteMenu();
				break;
			case 9:
				System.out.println("Thank you for using our program.");
				System.exit(0);
			default:
				System.out.println();
				System.out.println("Please choose from numbers 1 to 8.");
			}

		} while (choice < 1 || choice > 8);

	}

	private static void scheduleIntention() {
		String schedID = "";
		String time = "";
		String date = "";
		ResultSet rs = null;
		boolean notValid = false;
		printAllIntention(rs);
		System.out.println("Enter the row of the intention to use: ");
		int row = scan.nextInt();
		scan.nextLine();
		
		do {
			System.out.print("Enter time of mass for the intention(Ex. 7:00, 16:00): ");
			time = scan.nextLine();
			if (!time.matches("[0-9]{1,2}:[0-9]{2}")) {
				notValid = true;
				System.out.println("Not a valid time!");
			} else {
				time += ":00";
				notValid = false;
			}
		} while (notValid);

		do {
			System.out.print("Enter date of mass for the intention(YYYY-MM-DD): ");
			date = scan.nextLine();
			if (!date.matches("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")) {
				notValid = true;
				System.out.println("Not a valid date!");
			} else {
				notValid = false;
			}
		} while (notValid);
		
		ResultSet schedRs = null;

		try {
			schedRs = controller.searchMassSched(date, time);
			if (getResTotal(schedRs) == 0) {
				System.out.println("The schedule doesn't exist");
				notValid = true;
			} else {
				notValid = false;
				schedRs.next();
				schedID = schedRs.getString(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String intentionID = controller.getIntentionID(row);
			controller.createMassIntention(intentionID, schedID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void printMassSched(ResultSet rs) {
		try {
			if (getResTotal(rs) == 0) {
				System.out.println("There is no scheduled mass");
			} else {
				System.out.println("====================================================================");
				System.out.println("                           Mass Schedules                           ");
				System.out.println("--------------------------------------------------------------------");
				System.out.printf("     %-12s %-10s %-20s %-15s %n", "Date", "Time", "Scheduled Priest", "Type");
				int row = 1;
				while (rs.next()) {
					String time = rs.getString("time");
					String date = rs.getString("date");
					String name = rs.getString("name");
					String type = rs.getString("mass_type");
					System.out.printf("%-4d %-12s %-10s %-20s %-15s %n", row++, date, time, name, type);
				}
				System.out.println("====================================================================");
			}

			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}

	public static void printAllIntention(ResultSet rs) {
		try {
			rs = controller.getAllIntention();
			if (getResTotal(rs) == 0) {
				System.out.println("There is no scheduled mass");
			} else {
				System.out.println("====================================================================");
				System.out.println("                           Mass Intentions                          ");
				System.out.println("--------------------------------------------------------------------");
				System.out.printf("     %-25s %-25s %-25s %n", "Kind", "To", "Message");
				int row = 1;
				while (rs.next()) {
					String kind = rs.getString("kind");
					String to = rs.getString("for_name");
					String message = rs.getString("message");
					System.out.printf("%-4d %-25s %-25s %-25s %n", row++, kind, to, message);
				}
				System.out.println("====================================================================");
			}

			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}

	private static void printMassIntention(ResultSet rs) {
		String time;
		String date;
		boolean notValid = false;
		do {
			System.out.print("Enter start time (Ex. 7:00, 16:00): ");
			time = scan.nextLine();
			if (!time.matches("[0-9]{1,2}:[0-9]{2}")) {
				notValid = true;
				System.out.println("Not a valid time!");
			} else {
				time += ":00";
				notValid = false;
			}
		} while (notValid);

		do {
			System.out.print("Enter date (YYYY-MM-DD): ");
			date = scan.nextLine();
			if (!date.matches("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")) {
				notValid = true;
				System.out.println("Not a valid date!");
			} else {
				notValid = false;
			}
		} while (notValid);

		try {
			rs = controller.getAllIntention();
			if (getResTotal(rs) == 0) {
				System.out.println("There is no scheduled mass");
			} else {
				System.out.println("====================================================================");
				System.out.println("                           Mass Intentions                          ");
				System.out.println("--------------------------------------------------------------------");
				System.out.printf("     %-25s %-25s %-25s %n", "Kind", "To", "Message");
				int row = 1;
				while (rs.next()) {
					String kind = rs.getString("kind");
					String to = rs.getString("for_name");
					String message = rs.getString("message");
					System.out.printf("%-4d %-25s %-25s %-25s %n", row++, kind, to, message);
				}
				System.out.println("====================================================================");
			}

			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}

	private static void printPriestSched(ResultSet rs) {
		try {
			if (getResTotal(rs) == 0) {
				System.out.println("No schedule found for this priest or priest doesn't exist");
			} else {
				System.out.printf("     %-12s %-10s %-15s %n", "Date", "Time", "Type");
				int row = 1;
				while (rs.next()) {
					String time = rs.getString("time");
					String date = rs.getString("date");
					String type = rs.getString("mass_type");
					System.out.printf("%-4d %-12s %-10s %-15s %n", row++, date, time, type);
				}
			}

			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}

	public static void viewPriestSched() {
		System.out.print("Enter priest name (ex. Burgos, Jose): ");
		String[] name = (scan.nextLine()).split(",");
		ResultSet rs = null;
		try {
			rs = controller.getPriestSched(name[0].trim(), name[1].trim());
			if (getResTotal(rs) > 0) {
				System.out.println("==========================================");
				System.out.println("Priest: " + name[1].trim() + " " + name[0].trim());
				System.out.println("==================Schedule================");
				printPriestSched(rs);
				System.out.println("==========================================");
			} else {
				printPriestSched(rs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void enterSchedule() {
		String startTime;
		boolean notValid = false;
		do {
			System.out.print("Enter start time (Ex. 7:00, 16:00): ");
			startTime = scan.nextLine();
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
			date = scan.nextLine();
			if (!date.matches("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")) {
				notValid = true;
				System.out.println("Not a valid date!");
			} else {
				notValid = false;
			}
		} while (notValid);

		System.out.print("Enter mass type : ");
		String massType = scan.nextLine();

		String priest;
		ResultSet rs = null;
		do {
			System.out.print("Enter Priest name (Ex. Burgos, Jose): ");
			priest = scan.nextLine();
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

	public static void scheduleNewIntention() {
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
				time = scan.nextLine();
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
				date = scan.nextLine();
				if (!date.matches("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")) {
					notValid = true;
					System.out.println("Not a valid date!");
				} else {
					notValid = false;
				}
			} while (notValid);

			ResultSet rs = null;

			try {
				rs = controller.searchMassSched(date, time);
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
		to = scan.nextLine();
		System.out.print("Kind of intention : ");
		kind = scan.nextLine();
		System.out.print("Message : ");
		message = scan.nextLine();
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

	public static void addNewPriest() {
		String priestID = "";
		String name = "";
		System.out.print("Enter the priest name (ex. Burgos, Jose): ");
		name = scan.nextLine();
		String[] splittedName = name.split(",");

		ResultSet rs = null;

		try {
			rs = controller.getAllPriest();
			priestID = incrementID(rs, "P");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			controller.createPriestInfo(priestID, splittedName[0].trim(), splittedName[1].trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void removePriest() {

		boolean notValid = false;
		String name = "";
		do {
			System.out.print("Enter the priest name (ex. Burgos, Jose): ");
			name = scan.nextLine();
			String[] splittedName = name.split(",");

			ResultSet rs = null;

			try {
				rs = controller.getPriestInfo(splittedName[0].trim(), splittedName[1].trim());
				if (getResTotal(rs) == 0) {
					System.out.println("Priest not found!");
					notValid = true;
				} else {
					controller.deletePriestInfo(splittedName[0].trim(), splittedName[1].trim());
					notValid = false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (notValid);
	}

	public static void removeMassSched() {
		String time = "";
		String date;
		boolean notValid;
		ResultSet rs = null;
		do {
			do {
				System.out.print("Enter time of mass for the intention(Ex. 7:00, 16:00): ");
				time = scan.nextLine();
				if (!time.matches("[0-9]{1,2}:[0-9]{2}")) {
					notValid = true;
					System.out.println("Not a valid time!");
				} else {
					time += ":00";
					notValid = false;
				}
			} while (notValid);

			do {
				System.out.print("Enter date of mass for the intention(YYYY-MM-DD): ");
				date = scan.nextLine();
				if (!date.matches("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")) {
					notValid = true;
					System.out.println("Not a valid date!");
				} else {
					notValid = false;
				}
			} while (notValid);

			try {
				rs = controller.searchMassSched(date, time);
				if (getResTotal(rs) == 0) {
					System.out.println("The schedule doesn't exist");
					notValid = true;
				} else {
					controller.deleteMassSched(date, time);
					notValid = false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (notValid);
	}

	public static void updateMassSched() {
		boolean notValid = true;
		int row = 0;
		String col = "";
		String replacement = "";

		ResultSet rs = null;

		try {
			rs = controller.getMassSched();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		do {
			printMassSched(rs);
			try {
				if (getResTotal(rs) != 0) {
					System.out.println("Enter the row to edit: ");
					row = scan.nextInt();
					scan.nextLine();
					if (row < 0 || row > getResTotal(rs)) {
						notValid = true;
						continue;
					}

					System.out.println("Enter the field to edit: ");
					col = scan.nextLine();
					switch (col.toLowerCase()) {
					case "date":
						System.out.print("Enter date replacement (YYYY-MM-DD): ");
						replacement = scan.nextLine();
						if (!replacement.matches("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")) {
							notValid = true;
							System.out.println("Not a valid date!");
							continue;
						} else {
							notValid = false;
						}

						controller.updateNumberInfo("date", row, replacement);
						break;
					case "time":
						System.out.print("Enter time replacement (Ex. 7:00, 16:00): ");
						replacement = scan.nextLine();
						if (!replacement.matches("[0-9]{1,2}:[0-9]{2}")) {
							notValid = true;
							System.out.println("Not a valid time!");
							continue;
						} else {
							replacement += ":00";
							notValid = false;
						}
						controller.updateNumberInfo("time", row, replacement);
						break;
					case "scheduled priest":
						System.out.print("Enter Priest name (Ex. Burgos, Jose): ");
						replacement = scan.nextLine();
						String[] name = replacement.split(",");
						ResultSet priestRs = null;
						try {
							priestRs = controller.getPriestInfo(name[0].trim(), name[1].trim());
							if (getResTotal(priestRs) == 0) {
								System.out.println("Priest not found. Please enter an existing priest.");
								notValid = true;
								continue;
							} else {
								notValid = false;
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						priestRs.next();
						controller.updateNumberInfo("priest_id", row, priestRs.getString("priest_id"));
						break;
					case "type":
						System.out.print("Enter mass type : ");
						replacement = scan.nextLine();
						controller.updateNumberInfo("mass_type", row, replacement);
						notValid = false;
						break;
					default:
						notValid = true;
						continue;
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (notValid);

	}

	/**
	 * updateIntention update ung message or ung sched
	 */

	public static String incrementID(ResultSet rs, String startingLetter) {
		String iD = "";
		try {
			if (getResTotal(rs) == 0) {
				iD = startingLetter + "001";
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

	private static int getResTotal(ResultSet rs) throws Exception {
		int count = 0;
		rs.last();
		count = rs.getRow();
		rs.beforeFirst();
		return count;
	}

}
