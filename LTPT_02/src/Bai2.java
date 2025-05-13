
import java.util.*;
import java.util.concurrent.locks.*;
class A {
    List<Integer> ds = new ArrayList<>();
    Lock mutex = new ReentrantLock();
    Random rd = new Random();
}

class LuongGhi extends Thread {
    A a;

    public LuongGhi(A a) {
        this.a = a;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(a.rd.nextInt(2000)); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int giatri = a.rd.nextInt(100); 
            a.mutex.lock();
            try {
                a.ds.add(giatri);
                System.out.println("W: " + giatri + " - " + new Date());
            } finally {
                a.mutex.unlock();
            }
        }
    }
}

class LuongDoc extends Thread {
    A a;

    public LuongDoc(A a) {
        this.a = a;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(a.rd.nextInt(2000) + 1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            a.mutex.lock();
            try {
                if (!a.ds.isEmpty()) {
                    int giatri = a.ds.remove(0);
                    System.out.println("R: " + giatri + " - " + new Date());
                }
            } finally {
                a.mutex.unlock();
            }
        }
    }
}
public class Bai2 {
	 public static void main(String[] args) {
	        A a = new A();
	        int k = 5; 
	        int h = 3; 

	        for (int i = 0; i < k; i++) {
	            new LuongGhi(a).start();
	        }

	        for (int i = 0; i < h; i++) {
	            new LuongDoc(a).start();
	        }
	    }
}
