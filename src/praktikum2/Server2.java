package praktikum2;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class Server2 {
    public static void main(String[] arg) throws Exception {
        Socket s = null;
        ServerSocket ss = null;
        boolean serverOn = true;
        ss = new ServerSocket(4711);

        while (serverOn) {
            try {
                s = ss.accept();

                //Input
                InputStream is = s.getInputStream();
                Scanner scanner = new Scanner(is);

                while (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    System.out.println(input);

                    if (input.equals("")) {
                        break;
                    }
                }

                //Output
                OutputStream os = s.getOutputStream();
                final String OUTPUT_HEADERS = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n\r\n";
                os.write((OUTPUT_HEADERS + "<h1>It works!</h1>").getBytes());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        s.close();
        ss.close();
    }
}