package Bai4;


import java.rmi.server.UnicastRemoteObject;
//Remote Object (Server): Cài đặt giao diện, đăng ký với RMI Registry.
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class OddServiceImpl extends UnicastRemoteObject implements OddService {

    protected OddServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public int[] findOdds(int[] n) throws RemoteException {
        List<Integer> odds = new ArrayList<>();
        for (int a : n) {
            if (isOdd(a)) {
                odds.add(a);
            }
        }
        return odds.stream().mapToInt(i -> i).toArray();
    }

    public static boolean isOdd(int n) {
        return n % 2 != 0; 
    }

//	@Override
//	public int countOdds(int[] numbers) throws RemoteException {
//	    int count = 0;
//	    for (int number : numbers) {
//	        if (isOdd(number)) {
//	            count++;
//	        }
//	    }
//	    return count;
//	}

}
