import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {

    private static final int PORT = 3999;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                pool.execute(() -> handleClient(socket));
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            output.println("Welcome to Online Banking!");
            output.println("1. Register");
            output.println("2. Login");
            output.println(""); 
            
            // Read the client's choice
            String choice = input.readLine();
            System.out.println("Client chose: " + choice);

            

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Error closing socket: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
