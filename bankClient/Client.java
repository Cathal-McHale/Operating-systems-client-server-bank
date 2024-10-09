import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 3999;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            boolean keepRunning = true;

            while (keepRunning) {
                System.out.print("Enter your choice (1: Register, 2: Login, 3: Exit): ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        register(scanner, output); // Register
                        break;
                    case 2:
                        if (login(scanner, output)) { // Login
                            keepRunning = displayPostLoginMenu(scanner, output);
                        }
                        break;
                    case 3:
                        keepRunning = false; // Exit the loop and close the application
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private static boolean displayPostLoginMenu(Scanner scanner, PrintWriter output) {
        System.out.print("Enter your choice (3: Lodge Money, 4: Retrieve Users, 5: Transfer Money, 6: View Transactions, 7: Update Password, 8: Logout): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 3:
                lodgeMoney(scanner, output);
                break;
            case 4:
                retrieveUsers(output);
                break;
            case 5:
                transferMoney(scanner, output);
                break;
            case 6:
                viewTransactions(output);
                break;
            case 7:
                updatePassword(scanner, output);
                break;
            case 8:
                return false; // Logout and return to main menu
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }

        return true;
    }

    private static void transferMoney(Scanner scanner, PrintWriter output) {
        System.out.println("=== Money Transfer ===");
        
        // Prompt the user for users Email and PPS Number to send money
        System.out.print("Enter recipient's Email: ");
        String recipientEmail = scanner.nextLine();
        
        System.out.print("Enter recipient's PPS Number: ");
        String recipientPpsNumber = scanner.nextLine();
        
        // prompt for the amount to transfer
        System.out.print("Enter the amount to transfer: ");
        double transferAmount = scanner.nextDouble();
        
        // Send the transfer request to the server
        String transferData = "TRANSFER:" + recipientEmail + ":" + recipientPpsNumber + ":" + transferAmount;
        output.println(transferData);
        
      
    }


	private static void lodgeMoney(Scanner scanner, PrintWriter output) {
		// TODO Auto-generated method stub
		
	}

	private static void retrieveUsers(PrintWriter output) {
		// TODO Auto-generated method stub
		
	}

	private static void viewTransactions(PrintWriter output) {
        
    }

    private static void updatePassword(Scanner scanner, PrintWriter output) {
        System.out.println("Enter new password: ");
        String newPassword = scanner.next();
        // Send update password request to server
        output.println("UPDATE_PASSWORD:" + newPassword);
        System.out.println("Password updated.");
    }



   
	private static void register(Scanner scanner, PrintWriter output) {
        System.out.println("=== User Registration ===");

        // Get name from the user
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // Get PPS Number from the user
        System.out.print("Enter your PPS Number: ");
        String ppsNumber = scanner.nextLine();

        // Get email from the user
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        // Get password from the user
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Get address from the user
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        // Get initial balance from the user
        System.out.print("Enter your initial balance: ");
        double initialBalance;
        while (true) {
            try {
                initialBalance = Double.parseDouble(scanner.nextLine());
                break; // break out of the loop if input was successful
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a numeric value for the initial balance: ");
            }
        }

        // Construct a registration data string
        String registrationData = String.join(",",
                "REGISTER",
                name,
                ppsNumber,
                email,
                password,
                address,
                String.valueOf(initialBalance));

        // Send registration data to server
        output.println(registrationData);
        System.out.println("Registration data sent to the server.");
    }

	private static boolean login(Scanner scanner, PrintWriter output) {
	    System.out.println("=== User Login ===");

	    // Get username and password from user
	    System.out.print("Enter username: ");
	    String username = scanner.next();
	    System.out.print("Enter password: ");
	    String password = scanner.next();

	    // Send login request to server 
	    output.println("LOGIN:" + username + ":" + password);

	    // reply with successful login
	    System.out.println("Login successful.");
	    return true; 
	}

}
