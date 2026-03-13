public class SynchronizationThread {
    public static void main(String args[]) throws InterruptedException {

        Counter c = new Counter();

        Runnable r1 = () -> {
            for (int i = 0; i < 1000; i++) {
                c.increment();  // Only ONE thread enters at a time
            }
        };

        Runnable r2 = () -> {
            for (int i = 0; i < 1000; i++) {
                c.increment();  //  t2 WAITS if t1 is inside
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        //  Always prints 2000
        System.out.println("With Sync Count: " + c.count);
    }
}

class Counter {
    int count = 0;

    //  synchronized = only ONE thread can enter at a time
    // Other threads are BLOCKED until current thread finishes
    // This is called a MUTEX LOCK
    public synchronized void increment() {
        count++;  //  Now safe — no two threads run this together
    }
}
