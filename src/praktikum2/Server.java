package practicum1_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(4711);
        Socket ss = s.accept();
        InputStream is = ss.getInputStream();
        OutputStream os = ss.getOutputStream();

        Scanner inputScan = new Scanner(is);
        while (inputScan.hasNextLine()){

            String string = inputScan.nextLine();

            if (string.equals("")){
                break;
            }

            System.out.println(string);

        }

        String response = "HTTP/1.1 200 OK\r\n\r\n <h1>It works!</h1>";
        os.write(response.getBytes());
        System.out.println("Done request");


        s.close();
        ss.close();
    }
}
