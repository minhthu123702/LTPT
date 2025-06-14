package Bai4;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Remote Interface: Giao diện định nghĩa các phương thức từ xa
public interface OddService extends Remote {
   int[] findOdds(int[] numbers) throws RemoteException;

    // Hàm mới: Đếm số lượng số lẻ trong mảng
   // int countOdds(int[] numbers) throws RemoteException;
}
