package BAI3;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.209.209", 1234);
            System.out.println("Kết nối thành công");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            int[] arr = new int[10];
            Random rd = new Random();
            for (int i = 0; i < arr.length; i++) {
                arr[i] = rd.nextInt(10);
            }

            System.out.print("Client gửi: ");
            for (int x : arr) System.out.print(x + " ");
            System.out.println();

            out.writeObject(arr);

            int[] rs = (int[]) in.readObject();
            System.out.print("Server trả về : ");
            for (int x : rs) System.out.print(x + " ");
            System.out.println();

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}