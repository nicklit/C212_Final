import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class MarketPlace {

	private boolean loggedin = false;
	private Scanner userGetter;
	private Scanner passGetter;
	private Scanner emailGetter;
	private Scanner typeGetter;
	private Scanner updater;
	private String currentUsername = "";

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

				userSelect = userSelect.toLowerCase();

				// this is where I validate the username and password
				if (login(userSelect, passSelect).equals("Buyer")) {
					Buyer buyer = new Buyer();
					
				} else if (login(userSelect, passSelect).equals("MarketPlaceAdmin")) {
					MarketPlaceAdmin admin = new MarketPlaceAdmin();
				} else if (login(userSelect, passSelect).equals("Seller")) {
					Seller seller = new Seller();
				}

				else {
					System.out.println("incorrect combination, please try again add something here");
				}
			} else if (input.equals("logout")) {
				loggedin = false;
				currentUsername = "";
				System.out.println("logged out");
				System.out.println("\n" + helpMenu());
			} else if (input.equals("search")) {
				System.out.println("searching...");
				System.out.println("\n" + helpMenu());
			} else if (input.equals("help")) {
				System.out.println("\n" + helpMenu());
			} else if (input.equals("register")) {
				register();
			} else if(input.equals("update")){
				updateCreds();
			}
			else {
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
			help += "\"update credentials\" to update credentials\n";
		} else {
			help += "\"register\" to register\n";
			help += "\"login\" to login\n";
		}
		help += "\"search\" to search for items\n";
		help += "\"help\" anytime to see this message again\n";
		return help;
	}

	private void register() throws FileNotFoundException {

		String newUsername = uniqueUsername();

		String newPassword = newPassword();

		String newEmail = newEmail();

		String accountType = accountType();

		int newID = makeID();

		try {
			FileWriter pw = new FileWriter("bin/creds.csv", true);
			StringBuilder sb = new StringBuilder();

			sb.append(Integer.toString(newID));
			sb.append(",");
			sb.append(newUsername);
			sb.append(",");
			sb.append(newPassword);
			sb.append(",");
			sb.append(newEmail);
			sb.append(",");
			sb.append(accountType);
			sb.append("\n");

			pw.write(sb.toString());

			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\nNew Account has been created!\n");

		System.out.print(helpMenu());
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

		return (max + 1);
	}

	private String uniqueUsername() throws FileNotFoundException {
		boolean unique;

		String newUsername;

		userGetter = new Scanner(System.in);

		do {

			System.out.print("Please enter a Username: ");
			unique = true;

			newUsername = userGetter.next();
			newUsername = newUsername.toLowerCase();

			BufferedReader reader = new BufferedReader(new FileReader("bin/creds.csv"));
			String thisLine;
			try {
				thisLine = reader.readLine();
				while ((thisLine = reader.readLine()) != null) {
					thisLine = thisLine.trim();
					String[] items = thisLine.split(",");
					String user = items[1];
					user = user.toLowerCase();

					if (newUsername.equals(user)) {
						unique = false;
						System.out.println("Not a unique Username. Please try again");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					/* ignore */ }
			}

			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!unique);

		return (toTitleCase(newUsername));
	}

	private String newPassword() {
		String newPassword;
		String confimationPassword;

		passGetter = new Scanner(System.in);

		while (true) {
			System.out.print("\nPlease enter a password: ");
			newPassword = passGetter.next();

			System.out.print("Please enter your password again: ");
			confimationPassword = passGetter.next();

			if (newPassword.equals(confimationPassword))
				break;
			else
				System.out.print("Passwords did not match, try again!\n");
		}

		return newPassword;
	}

	private String accountType() {
		String type;

		typeGetter = new Scanner(System.in);

		while (true) {
			System.out.print("\nWill you be a Buyer or Seller?: ");
			type = typeGetter.next();

			type = toTitleCase(type);

			if (type.equals("Buyer") || type.equals("Seller"))
				break;
			else
				System.out.print("Not a valid input, try again!");
		}

		return type;
	}

	private static String toTitleCase(String input) {
		StringBuilder titleCase = new StringBuilder();
		boolean nextTitleCase = true;

		for (char c : input.toCharArray()) {
			if (Character.isSpaceChar(c)) {
				nextTitleCase = true;
			} else if (nextTitleCase) {
				c = Character.toTitleCase(c);
				nextTitleCase = false;
			}

			titleCase.append(c);
		}

		return titleCase.toString();
	}

	private String newEmail() {
		String newEmail;
		String confimationEmail;

		emailGetter = new Scanner(System.in);

		// RFC822 compliant regex for email
		Pattern ptr = Pattern.compile(
				"(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");

		while (true) {
			System.out.print("\nPlease enter an email: ");
			newEmail = emailGetter.next();

			System.out.print("Please enter your email again: ");
			confimationEmail = emailGetter.next();

			if (newEmail.equals(confimationEmail) && ptr.matcher(newEmail).matches())
				break;
			else if (!(ptr.matcher(newEmail).matches()))
				System.out.print("Not a legal email!\n");
			else
				System.out.print("Emails did not match, try again!\n");
		}

		return newEmail;
	}

	private String viewStock() {
		return null;

	}

	private void updateCreds() {
		String userResponse;

		String username;

		boolean pass = false;
		boolean email = true;
		CSVReader reader = null;
		if (currentUsername != "") {
			try {
				reader = new CSVReader(new FileReader("bin/creds.csv"), ',');
				List<String[]> csvBody = reader.readAll();
				updater = new Scanner(System.in);

				while (true) {
					System.out.println("What would you like to update, Password or Email?");
					userResponse = updater.next();
					userResponse = toTitleCase(userResponse);
					if (userResponse.equals("Password")) {
						pass = true;
						break;
					} else if (userResponse.equals("Email")) {
						email = true;
						break;
					} else {
						System.out.println("Invalid Response");
					}
				}
				System.out.println("Current Username: " + currentUsername);
				for (String[] creds : csvBody) {
					if (currentUsername.equals(creds[1])) {
						if (pass) {
							System.out.println("Changing Password here");
							creds[2] = newPassword();
						} else if (email) {
							System.out.println("Changing Email here");
							creds[3] = newEmail();
						}
					}
				}
				
				

				CSVWriter writer = new CSVWriter(new FileWriter("bin/creds.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
				writer.writeAll(csvBody);
				writer.flush();
				

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println("Verify you are logged in first!");
		}
	}

	private String search() {
		return null;

	}

	private String login(String username, String password) throws FileNotFoundException {
		String type = "bad input";
		BufferedReader reader = new BufferedReader(new FileReader("bin/creds.csv"));
		String thisLine;
		try {
			thisLine = reader.readLine();
			while ((thisLine = reader.readLine()) != null) {
				thisLine = thisLine.trim();
				String[] items = thisLine.split(",");
				String user = items[1];
				String pass = items[2];
				String userType = items[4];
				user = user.toLowerCase();
				if (user.equals(username)) {
					if (pass.equals(password)) {

						if (userType.equals("Buyer")) {
							type = "Buyer";
						} else if (userType.equals("Seller")) {
							type = "Seller";
						} else if (userType.equals("MarketPlaceAdmin")) {
							type = "MarketPlaceAdmin";
						}
						currentUsername = toTitleCase(user);
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
		return type;

	}

}