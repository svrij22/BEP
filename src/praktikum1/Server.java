package praktikum1;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class Server {
    public static void main(String[] arg) throws Exception{
        Socket s = null;
        ServerSocket ss = null;
        boolean serverOn = true;
        ss = new ServerSocket(4711);

        while (serverOn) {
            try{
                s = ss.accept();

                Scanner scanner = new Scanner(s.getInputStream());
                OutputStream os = s.getOutputStream();

                while (scanner.hasNext()) {
                    String input = scanner.nextLine();
                    System.out.println("Received " + input);
                    os.write(input.getBytes());

                    if (input.equals("exit")){
                        serverOn = false;
                        s.close();
                    }
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        s.close();
        ss.close();
    }
}