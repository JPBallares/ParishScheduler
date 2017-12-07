import java.util.*;
public class DBMSFinals {
	public static void main(String[] args) {
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

	public static int showParishMenu() {
		Scanner scan = new Scanner(System.in);
		int choice;
		System.out.println("========================================================");
		System.out.printf("%1s%29s%27s", "=", "MENU", "=\n");
		System.out.println("========================================================");
		System.out.printf("%1s%5s%34s", "= ", "1. View mass Schedule", "=\n");
		System.out.printf("%1s%5s%25s", "= ", "2. Schedule intention for mass", "=\n");
		System.out.printf("%1s%5s%32s", "= ", "3. View Priest Schedule", "=\n");
		System.out.printf("%1s%5s%47s", "= ", "4. Exit ", "=\n");
		System.out.print("  Enter choice: ");
		choice = scan.nextInt();
		return choice;
	}
}