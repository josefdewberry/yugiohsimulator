package yugioh;

import java.io.*;
import java.net.*;

import yugioh.RPS;
import yugioh.Player.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 9090);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        ObjectOutputStream dataOut = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream dataIn = new ObjectInputStream(s.getInputStream());

        while (true) {
            String response = in.readLine();
            if (response.equalsIgnoreCase("quit program")) {
                System.out.println("Bye-Bye!");
                s.close();
                System.exit(0);
            }
            System.out.println(response);
            if (in.readLine().equals("respond")) {
                System.out.print("> ");
                out.println(keyboard.readLine());
            }
        }
    }
}