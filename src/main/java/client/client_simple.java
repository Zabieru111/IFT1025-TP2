package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client_simple {
    public static void main(String[] args) {
        try{
            Socket clientSocket = new Socket("127.0.0.1",1337);
            ObjectOutputStream ois = new ObjectOutputStream(clientSocket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            while(scanner.hasNext()){
                String line = scanner.nextLine();
                System.out.println("Envoi de : " +line);
                ois.writeObject(line);
            }
        }catch (IOException e){

        }
    }
}
