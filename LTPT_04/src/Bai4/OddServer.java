package Bai4;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.net.InetSocketAddress;

public class OddServer {

  public static void main(String[] args) {
    try {
     	System.setProperty("java.rmi.server.hostname", "172.20.10.3");
      LocateRegistry.createRegistry(1099);
      //tạo một RMI Registry server chạy tại cổng 1099 trên chính máy đó.

      OddService oddService = new OddServiceImpl();
    //  tạo một đối tượng thực sự có thể xử lý logic (ở xa): gọi là remote object.
      Naming.rebind("rmi://172.20.10.3:1099/OddService", oddService);

      System.out.println("Service dang chay...");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
