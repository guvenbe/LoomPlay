public class Main {

    private static final int NUM_THREADS = 10;
    private  static  void handleUserRequests(){

        System.out.println("Starting thread " + Thread.currentThread());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Ending thread " + Thread.currentThread());

    }
    public static void main(String[] args) {
        System.out.println("Starting Main");
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> handleUserRequests()).start();

        }
    }
}