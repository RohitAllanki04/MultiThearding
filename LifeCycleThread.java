class LifeCycleThread {//Acutally to achieve each stage of thread diffcult
    public static void main(String args[]) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println("State INSIDE thread: " + 
                Thread.currentThread().getState()); // RUNNABLE

            try {
                Thread.sleep(1000); // Thread goes to TIMED_WAITING
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread work done!"); // Back to RUNNABLE → then TERMINATED
        });

        // Stage 1: NEW — Thread is created but not started
        System.out.println("1. After creating  : " + t1.getState()); // NEW

        // Stage 2: RUNNABLE — Thread is ready to run
        t1.start();
        System.out.println("2. After start()   : " + t1.getState()); // RUNNABLE

        // Stage 3: TIMED_WAITING — Thread is sleeping
        Thread.sleep(100); // Give t1 time to enter sleep
        System.out.println("3. While sleeping  : " + t1.getState()); // TIMED_WAITING

        // Stage 4: Wait for t1 to finish
        t1.join(); // main thread waits for t1 to finish


        // Stage 5: TERMINATED — Thread has finished
        System.out.println("4. After finished  : " + t1.getState()); // TERMINATED

        // Main thread state
        System.out.println("5. Main thread     : " + 
            Thread.currentThread().getState()); // RUNNABLE
        
    }
}