package praktikum3;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
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

                //request
                String requestPath = "/";

                while (scanner.hasNextLine()) {
                    String input = scanner.nextLine();

                    System.out.println(input);

                    if (input.startsWith("GET")){
                        requestPath = input.split(" ")[1];
                        System.out.println("RequestedPath:" + requestPath);
                    }

                    if (input.equals("")) {
                        break;
                    }
                }


                //Output
                OutputStream os = s.getOutputStream();
                requestPath = System.getProperty("java.class.path") + "\\praktikum3" + requestPath;

                File tmpDir = new File(requestPath);
                String OUTPUT = "test";
                long contentLength = 0;

                if (!tmpDir.exists()){
                    final String OUTPUT_HEADERS = "HTTP/1.1 404 Not Found\r\n" + "Content-Type: text/html\r\n\r\n";
                    OUTPUT = OUTPUT_HEADERS + "<h3>Testing page</h3>";
                    OUTPUT += "<h3>Sorry, '"+ tmpDir.toString() + "' not found!</h3>";
                }else{
                    if  (tmpDir.isFile()) {
                        contentLength = Files.size(Paths.get(requestPath));
                    }else{
                        contentLength = requestPath.getBytes().length;
                    }
                    final String OUTPUT_HEADERS = "HTTP/1.1 200 OK\r\n" + "Content-Length: " + contentLength +"\r\n\r\n";
                    OUTPUT = OUTPUT_HEADERS;
                }

                //requested file
                os.write((OUTPUT).getBytes());

                //File
                if (tmpDir.exists() && tmpDir.isFile()) {
                    System.out.println(requestPath);
                    Files.copy(Paths.get(requestPath), os);
                }

                //directory
                if (tmpDir.exists() && tmpDir.isDirectory()) {
                    os.write(requestPath.getBytes());
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        s.close();
        ss.close();
    }
}