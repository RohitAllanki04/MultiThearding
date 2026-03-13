class Threadcreate {
    public static void main(String args[]){
        Thread t1=new Thread(); // like instance
        t1.start();// when start calls excute run in thread class ,then start mention then only it is active,once terminate we can start/alive the same thread
        //excute run method in thread class,but run excute,nothing to execute expect null so we overide run. 
        System.out.println(t1.getName());//print default name ,if you want give name new thread("ThreadName")
        System.out.println(Thread.activeCount());//no of active thread
        System.out.println(Thread.currentThread().getName());
        System.out.println(t1.getPriority());
        System.out.println(Thread.currentThread().getPriority());
    }
}
