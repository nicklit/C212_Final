import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MarketPlace {

	private boolean loggedin = false;

	public static void main(String[] args) throws FileNotFoundException {
		MarketPlace testMarketPlace = new MarketPlace();

	}

	public MarketPlace() throws FileNotFoundException {

		Scanner reader1 = new Scanner(System.in);
		System.out.println("Welcome!\n");
		System.out.println(helpMenu());

		while (true) {
			String input = reader1.next();

			if (input.equals("login")) {
				System.out.println("Enter your username");
				String userSelect = reader1.next();
				System.out.println("Enter your password");
				String passSelect = reader1.next();

				// this is where I validate the username and password
				if (login(userSelect, passSelect)) {
					System.out.println("logged in");
					System.out.println("\n" + helpMenu());
				} else {
					System.out.println("incorrect combination, please try again add something here");
				}
			}else if (input.equals("logout")) {
				loggedin = false;
				System.out.println("logged out");
				System.out.println("\n" + helpMenu());
			} else if (input.equals("search")) {
				System.out.println("searching...");
				System.out.println("\n" + helpMenu());
			} else if (input.equals("help")) {
				System.out.println("\n" + helpMenu());
			} else if(input.equals("register")){
				register();
			}else {
				System.out.println("Sorry, I cannot recognize your command, please try again");
				System.out.println("\n" + helpMenu());
			}
		}

	}

	// we may need to move this method to each individual class to customize it
	// for the type of user
	private String helpMenu() {
		String help = "Enter a command below (without the quotes) to continue.\n\n";

		if (loggedin) {
			help += "\"logout\" to logout\n";
		} else {
			help += "\"login\" to login\n";
		}
		help += "\"search\" to search for items\n";
		help += "\"help\" anytime to see this message again\n";
		return help;
	}

	private void register() throws FileNotFoundException {
		
		int newID = makeID();
		
		String newUsername = "Nick";
		
		
		
		BufferedReader reader = new BufferedReader(
				new FileReader("bin/creds.csv"));
		String thisLine;
		try {
			thisLine = reader.readLine();
			while ((thisLine = reader.readLine()) != null) {
				thisLine = thisLine.trim();
				String[] items = thisLine.split(",");
			}
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				/* ignore */ }
		}
		
		try {
			FileWriter pw = new FileWriter("bin/creds.csv",true);
			StringBuilder sb = new StringBuilder();
			
			sb.append(Integer.toString(newID));
			
			pw.write(sb.toString());
			
			
			pw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		

	}

	private int makeID() throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader("bin/creds.csv"));
		String thisLine;
		int max = 0;
		try {
			thisLine = reader.readLine();
			while ((thisLine = reader.readLine()) != null) {
				thisLine = thisLine.trim();
				String[] items = thisLine.split(",");
				String id = items[0];
				int idNum = Integer.parseInt(id);
				if (idNum > max) {
					max = idNum;
				}
			}
		} catch (IOException e) {
			System.out.print("\nUser does not exist\n");
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				/* ignore */ }
		}
		
		return (max+1);
	}

	private String viewStock() {
		return null;

	}

	private void updateCreds() {

	}

	private String search() {
		return null;

	}

	private boolean login(String username, String password) throws FileNotFoundException {

		BufferedReader reader = new BufferedReader(
				new FileReader("bin/creds.csv"));
		String thisLine;
		try {
			thisLine = reader.readLine();
			while ((thisLine = reader.readLine()) != null) {
				thisLine = thisLine.trim();
				String[] items = thisLine.split(",");
				String user = items[1];
				String pass = items[2];
				String userType = items[4];
				user=user.toLowerCase();
				if (user.equals(username)) {
					if (pass.equals(password)) {
						if (userType.equals("Buyer")) {
							Buyer buyer = new Buyer();
						}
						loggedin = true;
						break;
					}
				}
			}

		} catch (IOException e) {
			System.out.print("\nUser does not exist\n");
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				/* ignore */ }
		}
		return loggedin;

	}

}