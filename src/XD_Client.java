import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class XD_Client {
    public static void main(String [] args){
        try{
            Socket socket = new Socket("103.214.142.107",10404);
            FileInputStream fis = new FileInputStream("7.jpg");
            socket.getOutputStream().write(fis.readAllBytes());
            socket.shutdownOutput();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(br.readLine());
            socket.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
