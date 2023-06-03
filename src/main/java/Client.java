import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8989;
    private static final String REQUEST = "бизнес";

    public static void main(String[] args) {
        try {
            InetAddress inetAddress = Inet4Address.getByName(SERVER_ADDRESS);

            try (Socket socket = new Socket(inetAddress, SERVER_PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                out.println(REQUEST);
                String response;

                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
