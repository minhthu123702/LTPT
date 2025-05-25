package BAI3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        try {
      
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server đang chạy, chờ client kết nối...");

    
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client đã kết nối.");
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println("Tạo luồng vào ra thành công.");
            int[] mangNhan = (int[]) ois.readObject();
            System.out.print("Mảng nhận từ client: ");
            for (int x : mangNhan) {
                System.out.print(x + " ");
            }
            System.out.println();
            int[] mangChinhPhuong = locSoChinhPhuong(mangNhan);
            oos.writeObject(mangChinhPhuong);
            System.out.print("Mảng gửi về client (chỉ gồm số chính phương): ");
            for (int x : mangChinhPhuong) {
                System.out.print(x + " ");
            }
            System.out.println();
            ois.close();
            oos.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] locSoChinhPhuong(int[] mang) {
        ArrayList<Integer> ds = new ArrayList<>();
        for (int x : mang) {
            if (laSoChinhPhuong(x)) {
                ds.add(x);
            }
        }
        int[] ketQua = new int[ds.size()];
        for (int i = 0; i < ds.size(); i++) {
            ketQua[i] = ds.get(i);
        }

        return ketQua;
    }
    public static boolean laSoChinhPhuong(int n) {
        if (n < 0) return false;
        int can = (int) Math.sqrt(n);
        return can * can == n;
    }
}