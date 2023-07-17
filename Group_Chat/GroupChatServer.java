import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GroupChatServer {
    private List<Socket> clients;
    private ServerSocket serverSocket;

    public GroupChatServer(int port) {
        clients = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
            System.exit(1);
        }
    }

    public void start() {
        while (true) {
            try {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);

                // Start a new thread to handle client messages
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();

                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
            } catch (IOException e) {
                System.err.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    public void broadcastMessage(String message) {
        for (Socket client : clients) {
            try {
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println(message);
            } catch (IOException e) {
                System.err.println("Error sending message to client: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        int port = 8080; // Change this to the desired port number
        GroupChatServer server = new GroupChatServer(port);
        server.start();
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;

            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                System.err.println("Error creating input stream: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    String arr[]=message.split("~");
                    System.out.print(arr[1]);
                    System.out.println(" : " + arr[0]);
                    broadcastMessage(message);
                }
            } catch (IOException e) {
                System.err.println("Error reading client message: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                    clients.remove(clientSocket);
                } catch (IOException e) {
                    System.err.println("Error closing client connection: " + e.getMessage());
                }
            }
        }
    }
}
