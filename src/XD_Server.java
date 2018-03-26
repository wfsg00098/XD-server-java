import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XD_Server {
    static int count = 0;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(10404);
            System.out.println("Listening on port 10404...");
            while (true) {
                Socket io = ss.accept();
                new Connection(io).start();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

class Connection extends Thread {
    private final String FileAddress = "/home/mode/upload/";
    private Socket socket;

    Connection(Socket io) {
        socket = io;
    }

    @Override
    public void run() {
        String now = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        int number = XD_Server.count++;
        System.out.println("A client(" + Integer.toString(number) + ") connected on " + now + " from " + socket.getInetAddress().getHostAddress());
        now = now + ".jpg";
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            FileOutputStream fos = new FileOutputStream(FileAddress + now);
            int temp;
            while ((temp = in.read()) != -1) {
                fos.write(temp);
                fos.flush();
            }
            fos.close();
            Process p = Runtime.getRuntime().exec("/home/anaconda3/python /home/model/test.py " + FileAddress + now);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String result = br.readLine();
            out.write(("https://www.guaiqihen.com/" + result + ".png").getBytes());
            out.flush();
            System.out.println("Client " + Integer.toString(number) + " result " + result);
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
            System.out.println("Client " + Integer.toString(number) + " disconnected");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}


