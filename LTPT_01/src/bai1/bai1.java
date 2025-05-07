package bai1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class bai1 {

	static final int n = 10;
	static final int k = 3;

	static int[] a = new int[n];
	static int[] c = new int[k];

	public static void main(String[] args) {

		Random r = new Random();
		for (int i = 0; i < n; i++) {
			a[i] = r.nextInt(50);
		}
		a[n-1]=9;
		
		System.out.println("Mảng a:");
		for (int i = 0; i < n; i++) {
			System.out.print(a[i] + " ");}
		System.out.println("\n");

		Thread[] t = new Thread[k];
		for (int i = 0; i < k; i++) {
			int st = i * (n / k);
			int en = (i == k - 1) ? n : (i + 1) * (n / k);
			t[i] = new Thread(new Task(i, st, en));
			t[i].start();
		}

		for (int i = 0; i < k; i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
			}
		}

		int s = 0;
		for (int i = 0; i < k; i++)
			s += c[i];

		System.out.println("\n==> Tổng số chính phương trong mảng A: " + s);
	}

	static boolean cs(int x) {
		int y = (int) Math.sqrt(x);
		return y * y == x;
	}

	static class Task implements Runnable {
		int id, st, en;

		Task(int id, int st, int en) {
			this.id = id;
			this.st = st;
			this.en = en;
		}

		public void run() {
			int dem = 0;
			String time = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
			for (int i = st; i < en; i++) {
				if (cs(a[i])) {
					 System.out.println("T" + (id + 1) + ": " + a[i] +" " + time);
					dem++;
			}
			c[id] = dem;
		
		}
	}
}
}
