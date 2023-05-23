
import java.lang.Thread.Builder.OfVirtual;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 *
 * This program shows the various ways in which a Virtual
 * Thread can be created. It does not include the
 * Thread.startVirtualThread(..) style.
 *
 * @author vshetty
 *
 */
public class VirtualMethodsPlay {
    private static void  heandleRequest(){
        System.out.println("Starting thread "+ Thread.currentThread());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ending thread "+ Thread.currentThread());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Virtual main");
        playWithVirtualBuilder();
        System.out.println("Ending Virtual main");

        System.out.println("Starting Factory main");
       playWithVirtualBuilder();
        System.out.println("Ending Factory main");

        System.out.println("Starting Virtual Thread using a Virtual Thread Executor main");
        playWithVirtualExecuterService();
        System.out.println("Ending Virtual Thread using a Virtual Thread Executor main");

        System.out.println("Starting Thread Per Task Executor Service main");
        playWithVirtualExecuterService();
        System.out.println("Ending Thread Per Task Executor Service main");

    }
    /**
     *
     * Creates Virtual Threads using a Virtual Builder
     * @throws Exception
     */
    @SuppressWarnings({"preview", "unused"})
    private static void playWithVirtualBuilder() throws InterruptedException {
        // Create a Virtual Builder object with name and initial index

        OfVirtual vBuilder = Thread.ofVirtual().name("userthread", 0);

        // Start two virtual threads using the builder
        Thread vThread1 = vBuilder.start(VirtualMethodsPlay::heandleRequest);
        Thread vThread2 = vBuilder.start(VirtualMethodsPlay::heandleRequest);
        // Make sure the threads terminate
        vThread1.join();
        vThread2.join();
        // Control reaches here once the two virtual threads complete
    }

    /**
     *
     * Creates Virtual Threads using a Thread Factory
     * @throws Exception
     */
    private static void playWithFactory() throws InterruptedException {
        //Creates a thread Factory
        ThreadFactory factory = Thread.ofVirtual().name("user-facotry-thread", 0).factory();

        //Start two virtual threads using the factory
        Thread vThread1
                = factory.newThread(VirtualMethodsPlay::heandleRequest);
        vThread1.start();
        Thread vThread2
                = factory.newThread(VirtualMethodsPlay::heandleRequest);
        vThread2.start();
        // Make sure the threads terminate
        vThread1.join();
        vThread2.join();
        // Control reaches here once the two virtual threads complete

    }

    /**
     *
     * Create a Virtual Thread using a Virtual Thread Executor
     */
    @SuppressWarnings({ "preview", "unused" })
    private static void playWithVirtualExecuterService(){
        // Create an Virtual Thread ExecutorService
        // Note the try with resource which will make sure all Virtual threads
        // are terminated
        try(ExecutorService srv = Executors.newVirtualThreadPerTaskExecutor()){
            // Submit two tasks to the Executor service
            srv.submit(VirtualMethodsPlay::heandleRequest);
            srv.submit(VirtualMethodsPlay::heandleRequest);
            // Control reaches here once the two virtual threads complet
        }
    }

    /**
     *
     * Create a Virtual Thread using a "Thread Per Task" Executor Service
     */
    @SuppressWarnings({ "preview", "unused" })
    private static void playWithExecutorService(){
        // Create a Virtual Thread factory with custom name
        ThreadFactory factory = Thread.ofVirtual().name("userthread", 0).factory();
        // Create an ExecutorService for this factory
        // Note the try with resource which will make sure all Virtual threads
        // are terminated
        try(ExecutorService srv = Executors.newThreadPerTaskExecutor(factory)){
            // Submit two tasks to the Executor service
            srv.submit(VirtualMethodsPlay::heandleRequest);
            srv.submit(VirtualMethodsPlay::heandleRequest);
        }
        // Control reaches here once the two virtual threads complete

    }


}
