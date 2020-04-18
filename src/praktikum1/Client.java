package praktikum1;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

class Client {
    public static void main(String[] arg) throws Exception{

        Socket s = new Socket("localhost", 4711);
        OutputStream os = s.getOutputStream();
        Scanner fromS = new Scanner(s.getInputStream());
        Scanner in = new Scanner(System.in);

        while (in.hasNext() || fromS.hasNext()){
            if (in.hasNext()) {
                String str = in.nextLine();
                System.out.println("Send " + str);
                os.write((str + "\n").getBytes());
            }else{
                System.out.println("Received " + fromS.nextLine());
            }
        }

        s.close();

    }
}