import java.io.*;
import java.net.*;

public class SocketServer {
    public static void main(String[] args) throws IOException {

        final int PORT_NR = 1250;
        ServerSocket server = new ServerSocket(PORT_NR);
        System.out.println("Waiting for connection...");
        Socket connection = server.accept();
        System.out.println("Connection made with client");

        InputStreamReader readingConnection = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(readingConnection);
        PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);

        writer.println("Connection has been made between client and server.");
        writer.println("Input complete math expression ((only adding and subtracting)): ");
        try {
            String line = reader.readLine().replaceAll("\\s", "");
            while (line != null) {
                System.out.println("Client wrote: " + line);
                writer.println(line + " = " + calculateExpression(line));
                writer.println("Input complete math expression (only adding and subtracting): ");
                line = reader.readLine().replaceAll("\\s", "");
            }
        } catch (IOException exception){
            writer.println(exception.getMessage());
        }
        writer.close();
        reader.close();
        connection.close();
    }

    public static int calculateExpression(String line) throws IOException{
        String[] split = line.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
        int result = Integer.parseInt(split[0]);
        for (int i = 1; i < split.length; i+=2){
            switch (split[i]) {
                case "+" -> result += Integer.parseInt(split[i + 1]);
                case "-" -> result -= Integer.parseInt(split[i + 1]);
            }
        }
        return result;
    }
}