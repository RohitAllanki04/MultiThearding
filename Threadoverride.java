public class Threadoverride {
    public static void main(String[] args) {
        Student s1=new Student();
       s1.start();
       Students s2 = new Students(1);  // Pass value via constructor
        s2.start();
    }
}
class Student extends Thread
{
    @Override
    public void run(){
        System.out.println("Run the Thread");
    }
}
class Students extends Thread {
    int i;

    Students(int i) {       //  Store value in constructor
        this.i = i;
    }

    @Override
    public void run() {    //  No parameters
        System.out.println("Run the Thread " + i);
    }
}
