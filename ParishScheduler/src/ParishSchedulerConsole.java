import java.util.*;
public class ParishSchedulerConsole {
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
	
	public static int showParishMenu() {
		int choice;
		System.out.println("========================================================");
		System.out.printf("%1s%29s%27s", "=", "MENU", "=\n");
		System.out.println("========================================================");
		System.out.printf("%1s%5s%34s", "= ", "1. View mass Schedule", "=\n");
		System.out.printf("%1s%5s%25s", "= ", "2. Schedule intention for mass", "=\n");
		System.out.printf("%1s%5s%32s", "= ", "3. View Priest Schedule", "=\n");
		System.out.printf("%1s%5s%47s", "= ", "4. Exit ", "=\n");
		System.out.print("  Enter choice: ");
		choice = kbd.nextInt();
		return choice;
	}
	
	public static void run() {
		int choice;
		choice = showParishMenu();
		System.out.print(choice);
		switch(choice){
			case 1:

			case 2:

			case 3:

			case 4:
				System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		ParishSchedulerConsole ps;
		try{
			ps = new ParishSchedulerConsole();
			ps.run();
		}catch(Exception x){
			x.printStackTrace();
		}
		System.exit(0);
	}
}
