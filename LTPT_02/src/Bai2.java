import java.util.*;
import java.util.concurrent.locks.*;

class A {
    List<Integer> ds = new ArrayList<>();
    Lock mutex = new ReentrantLock();
    Condition notFull = mutex.newCondition();
    Condition notEmpty = mutex.newCondition();
    Random rd = new Random();
    final int n = 10; 
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

            a.mutex.lock();
            try {
                while (a.ds.size() >= a.n) {
                    a.notFull.await();
                }

                int giatri = a.rd.nextInt(100);
                a.ds.add(giatri);
                System.out.println("W: " + giatri + " - " + new Date());

                a.notEmpty.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
                Thread.sleep(a.rd.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            a.mutex.lock();
            try {
                while (a.ds.isEmpty()) {
                    a.notEmpty.await();
                }

                int val = a.ds.remove(0);
                int sqrt = (int) Math.sqrt(val);
                if (sqrt * sqrt == val) {
                    System.out.println("R: " + val + " - scp - " + new Date());
                } else {
                    System.out.println("R: " + val + " - " + new Date());
                }

                a.notFull.signal();

            } catch (InterruptedException e) {
                e.printStackTrace();
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
