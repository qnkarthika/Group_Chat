
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GroupChatClient {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
 
    static String name = "jeeva";


    public GroupChatClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            // Connect to the server
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to the server.");

            // Create input and output streams
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start a separate thread to handle server messages
            ServerMessageHandler serverMessageHandler = new ServerMessageHandler();
            serverMessageHandler.start();

            // Read user input and send messages to the server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message+"~"+name);
            }
        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing the socket: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String serverAddress = "192.168.43.161"; // Change this to the server's IP address
        int serverPort = 8080; // Change this to the server's port number
        GroupChatClient client = new GroupChatClient(serverAddress, serverPort);
        client.start();
    }

    private class ServerMessageHandler extends Thread {
        @Override
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    String arr[]=message.split("~");
                    System.out.println(arr[1] +": "+arr[0]);
                }
            } catch (IOException e) {
                System.err.println("Error reading server message: " + e.getMessage());
            }
        }
    }
}
