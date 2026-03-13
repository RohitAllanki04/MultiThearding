public class ThreadWithRunnable {//Runnable is functional interface with only one method run, so we can use lambda expression to implement it, and also we can use anonymous class to implement it, and also we can use separate class to implement it.
    public static void main(String args[]) {

        // 1️ Separate Class
        Student s1 = new Student();//or directly we can write new Thread(new Student()).start();
        Thread t1 = new Thread(s1);
        t1.start();

        // 2️ Anonymous Class
        Thread t2 = new Thread(new Runnable() {//Runnable r1=new Runnable(){to override method}; then pass r1 in thread constructor
            @Override
            public void run() {
                System.out.println("Anonymous : " + 
                    Thread.currentThread().getName());
            }
        });
        t2.start();

        // 3️ Lambda Expression
        Thread t3 = new Thread(() -> //Runnable r2=()->{System.out.println()}; then pass r2 in thread constructor
            System.out.println("Lambda     : " + 
                Thread.currentThread().getName())
        );
        t3.start();
    }
}

class Student implements Runnable {
    @Override
    public void run() {
        System.out.println("Separate   : " + 
            Thread.currentThread().getName());
    }
}
