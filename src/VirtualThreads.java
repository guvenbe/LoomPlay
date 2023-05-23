import java.util.ArrayList;

public class VirtualThreads {

    private static final int NUM_THREADS = 1000000;
    private  static  void handleUserRequests(){

        System.out.println("Starting thread " + Thread.currentThread());
        try {
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Ending thread " + Thread.currentThread());

    }
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Main " + Thread.currentThread());
        var threads = new ArrayList<Thread>();
        for (int i = 0; i < NUM_THREADS; i++) {
            threads.add(startThread());
        }
        //join on the threads
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Ending Main");
    }

    private static Thread startThread() {
//        new Thread(() -> handleUserRequests()).start();
        return Thread.startVirtualThread(() ->handleUserRequests());
    }
}
