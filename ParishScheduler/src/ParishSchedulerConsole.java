import java.util.*;
public class ParishSchedulerConsole {
	private static Scanner kbd = new Scanner(System.in);
	
	public static boolean checkTime(String time) {
		boolean flag = false;
		String[] t = time.split(":");
		String[] ap = time.split(" ");
		
		if ( ((Integer.parseInt(t[0]) >= 7) && (ap[1].equals("AM"))) || ((Integer.parseInt(t[0]) <= 6) && (ap[1].equals("PM"))) ) {
			flag = true;
		}
		return flag;
	}
	
	public static void enterSchedule() {
		String startTime;
		do {
			System.out.println("Example: 7:00 AM");
			System.out.print("Enter start time : ");
			startTime = kbd.nextLine();
		} while (checkTime(startTime.toUpperCase()) == false);
		
		System.out.print("Enter Priest name : ");
		String priestName = kbd.nextLine();
	}

	public static void main(String[] args) {
		enterSchedule();
	}

}
