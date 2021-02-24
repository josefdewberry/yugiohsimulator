package yugioh;

import java.io.*;
import java.net.*;

import yugioh.RPS;
import yugioh.Player.*;

/**
 * Simple client program run by oth players. All it does is take input and sent output.
 * 
 * @author josefdewberry
 */
public class Client {

    /**
     * Main method which loops for infinity unless the program quits.
     * 
     * @param args Command line arguments, none needed.
     * @exception IOException If something goes wrong passing input/output with the server.
     */
    public static void main(String[] args) throws IOException {

        // Socket for the client.
        Socket s = new Socket("127.0.0.1", 9090);

        // Set up the client with all its readers and writers. We take input from the
        // user using the keboard buffered reader.
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        ObjectOutputStream dataOut = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream dataIn = new ObjectInputStream(s.getInputStream());

        // Because there is a chance of something going wrong reading/writing we wrap the
        // infinite loop in a try/catch.
    
        while (true) {
            // Input given from the server.
            String message = in.readLine();
            // Should only occur if the game ends or the user specifically types
            // "quit program".
            if (message.equalsIgnoreCase("quit program")) {
                System.out.println("Bye-Bye!");
                s.close();
                System.exit(0);
            }
            // Print out the response.
            System.out.println(message);
            // This is how we know if the server is expecting a response. After EVERY SINGLE
            // message the server should send a second message that either says "respond"
            // meaning it expects a response, or "no response" which means it simply wants
            // the client to display the first message. Anything else should throw an error.
            String response = in.readLine();
            if (response.equals("respond")) {
                // I like the litter >, it lets the user know that we expect input from them.
                System.out.print("> ");
                // Read the input from the keboard and send it to the server.
                out.println(keyboard.readLine());
            // If we receive anything besides "respond" or "no response", throw an error.
            } else if (!response.equals("no response")) {
                throw new IOException("Bad secondary response received from server: " 
                                        + response);
            }
        }
    }
}