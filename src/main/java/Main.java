import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 8989;

    public static void main(String[] args) {
        BooleanSearchEngine engine;
        try {
            engine = new BooleanSearchEngine(new File("pdfs"));
        } catch (IOException e) {
            System.out.println("Error initializing search engine");
            e.printStackTrace();
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server running on port " + PORT);

            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                ) {
                    String request = in.readLine();
                    String response = gson.toJson(engine.search(request));
                    out.println(response);
                } catch (IOException e) {
                    System.out.println("Error handling client request");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Error starting server");
            e.printStackTrace();
        }
    }
}