package Bai4;


import java.rmi.Naming;
import java.util.Random;

public class OddClient {
    public static void main(String[] args) {
        try {
       
            int[] clientArray = new int[20];
            Random r = new Random();
            for (int i = 0; i < 10; i++) {
                clientArray[i] = r.nextInt(10);
            }
            
        
            System.out.print("Mang: ");
            for (int value : clientArray) {
                System.out.print(value + " ");
            }
            System.out.println();

            OddService oddService = (OddService) Naming.lookup("rmi://172.20.10.3:1099/OddService");
            // Naming.lookup tìm và lấy đối tượng đã đăng ký từ xa 
            int[] odds = oddService.findOdds(clientArray);

            System.out.print("Cac so le trong mang: ");
            for (int odd : odds) {
                System.out.print(odd + " ");
            }
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
       
    
    
    }
    }



/*package Bai4;

import java.rmi.Naming;
import java.util.Random;

public class OddClient {
    public static void main(String[] args) {
        try {
            int[] clientArray = new int[10]; // tạo mảng 10 phần tử
            Random r = new Random();
            for (int i = 0; i < 10; i++) {
                clientArray[i] = r.nextInt(10); // sinh số ngẫu nhiên từ 0 đến 9
            }

            System.out.print("Mang: ");
            for (int value : clientArray) {
                System.out.print(value + " ");
            }
            System.out.println();

            // Kết nối tới RMI server
            OddService oddService = (OddService) Naming.lookup("rmi://192.168.135.209:1099/OddService");

            // Gọi hàm countOdds
            int count = oddService.countOdds(clientArray);
            System.out.println("So luong so le trong mang: " + count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

 * */
 