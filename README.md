# 🧵 Java Multithreading — Complete Guide
> A clear, beginner-to-advanced guide to understanding Multithreading in Java from scratch.

---

## 📌 Table of Contents
1. [Task](#1-task)
2. [Process](#2-process)
3. [Thread](#3-thread)
4. [Multitasking](#4-multitasking)
5. [Multiprocessing](#5-multiprocessing)
6. [Multithreading](#6-multithreading)
7. [Time Sharing Mechanism](#7-time-sharing-mechanism)
8. [Can We Achieve Multiprocessing Without Threading?](#8-can-we-achieve-multiprocessing-without-threading)
9. [Default Thread](#9-default-thread)
10. [start() calls run() — How It Works](#10-start-calls-run--how-it-works)
11. [Threads Run Based on CPU and JVM Decision](#11-threads-run-based-on-cpu-and-jvm-decision)
12. [4 Ways to Implement Multithreading](#12-4-ways-to-implement-multithreading)
13. [Runnable Interface, Thread Class and Methods](#13-runnable-interface-thread-class-and-methods)
14. [Thread Life Cycle](#14-thread-life-cycle)
15. [Biggest Problem — Race Condition](#15-biggest-problem--race-condition)
16. [Heap, Stack & Thread Stack — Block Diagram](#16-heap-stack--thread-stack--block-diagram)
17. [Synchronization](#17-synchronization)
18. [volatile Keyword](#18-volatile-keyword)
19. [Deadlock — When Synchronization Goes Wrong](#19-deadlock--when-synchronization-goes-wrong)
20. [Synchronization vs Performance](#20-synchronization-vs-performance)

---

## 1. Task
A **task** is a single unit of work that a program needs to do.

```
Examples:
- Downloading a file
- Sending an email
- Printing a document
```

> One program can have multiple tasks running at the same time.

---

## 2. Process
A **process** is a program in execution. It has its own memory space.

```
Program (on disk)  →  Loaded into RAM  →  Becomes a Process
```

```
Each Process has:
├── Code
├── Data
├── Heap Memory
└── Stack Memory
```

> Example: When you open Chrome, it becomes a **process** in your OS.

---

## 3. Thread
A **thread** is the smallest unit of execution inside a process.

```
Process
├── Thread 1  → Task A
├── Thread 2  → Task B
└── Thread 3  → Task C
```

> A process can have multiple threads sharing the same memory.

---

## 4. Multitasking
**Multitasking** means doing multiple tasks at the same time.

| Type | How | Example |
|---|---|---|
| Process-based | Multiple processes run together | Chrome + Spotify + VSCode |
| Thread-based | Multiple threads inside one process | Download + Upload in same app |

---

## 5. Multiprocessing
**Multiprocessing** means multiple processes run on multiple CPUs simultaneously.

```
CPU 1  →  Process 1 (Chrome)
CPU 2  →  Process 2 (Spotify)
CPU 3  →  Process 3 (IntelliJ)
```

> Each process is independent — they do NOT share memory.

---

## 6. Multithreading
**Multithreading** means multiple threads run inside a single process sharing the same memory.

```
Single Process (Java App)
├── Thread 1  → Download file
├── Thread 2  → Show progress bar
└── Thread 3  → Handle user click
```

```java
Thread t1 = new Thread(() -> System.out.println("Thread 1 running"));
Thread t2 = new Thread(() -> System.out.println("Thread 2 running"));
t1.start();
t2.start();
```

> ✅ Threads share Heap memory but have their own Stack.

---

## 7. Time Sharing Mechanism
The CPU gives each thread a small **time slice** to execute. This happens so fast it feels like everything runs simultaneously.

```
Timeline:
─────────────────────────────────────────────
CPU:   [T1][T2][T1][T3][T2][T1][T3][T2]...
─────────────────────────────────────────────
       ↑                               
     Each block = tiny time slice (milliseconds)
```

> This is managed by the **OS Scheduler** and **JVM Thread Scheduler**.

---

## 8. Can We Achieve Multiprocessing Without Threading?

**Yes**, but with limitations.

| | Without Threading | With Threading |
|---|---|---|
| Multiple tasks | Run sequentially ❌ | Run concurrently ✅ |
| Speed | Slower | Faster |
| CPU Usage | Low | High |
| Complexity | Simple | Complex |

```java
// Without Threading — tasks run one after another
doTask1();  // Must finish before task 2 starts
doTask2();  // Waits for task 1
doTask3();  // Waits for task 2

// With Threading — tasks run together
new Thread(() -> doTask1()).start();
new Thread(() -> doTask2()).start();
new Thread(() -> doTask3()).start();
```

> Without threading, you can fork new processes but that is expensive in memory and slower than threads.

---

## 9. Default Thread
When a Java program starts, JVM automatically creates a **main thread**.

```java
public class DefaultThread {
    public static void main(String args[]) {
        // This itself runs inside the MAIN thread
        System.out.println("Thread Name : " + Thread.currentThread().getName());
        System.out.println("Thread ID   : " + Thread.currentThread().getId());
        System.out.println("Priority    : " + Thread.currentThread().getPriority());
    }
}
```

```
Output:
Thread Name : main
Thread ID   : 1
Priority    : 5
```

> The **main thread** is the parent of all other threads in a Java program.

---

## 10. start() calls run() — How It Works

```
t1.start()
     │
     ▼
JVM creates a NEW thread
     │
     ▼
New thread calls run() internally
     │
     ▼
run() executes in the NEW thread
```

```java
class MyThread extends Thread {
    public void run() {
        // This runs in a NEW thread
        System.out.println("Running in: " + Thread.currentThread().getName());
    }
}

MyThread t1 = new MyThread();
t1.start();  // ✅ Creates new thread → calls run()
// t1.run(); // ❌ Does NOT create new thread — runs in main thread!
```

> ⚠️ Never call `run()` directly — always use `start()` to create a new thread.

---

## 11. Threads Run Based on CPU and JVM Decision
You **cannot control** which thread runs first — it is decided by:

```
Thread Execution Order decided by:
├── JVM Thread Scheduler
├── OS Scheduler  
└── CPU Availability
```

```java
Thread t1 = new Thread(() -> System.out.println("Thread 1"));
Thread t2 = new Thread(() -> System.out.println("Thread 2"));
Thread t3 = new Thread(() -> System.out.println("Thread 3"));

t1.start();
t2.start();
t3.start();

// Output order is UNPREDICTABLE — could be:
// Thread 2, Thread 1, Thread 3
// Thread 3, Thread 2, Thread 1
// Thread 1, Thread 3, Thread 2
```

> You can use `setPriority()` to give hints but JVM is not guaranteed to follow it.

---

## 12. 4 Ways to Implement Multithreading

### Way 1 — Extend Thread Class
```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Extending Thread class");
    }
}
new MyThread().start();
```

### Way 2 — Implement Runnable Interface
```java
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Implementing Runnable");
    }
}
new Thread(new MyRunnable()).start();
```

### Way 3 — Anonymous Class
```java
new Thread(new Runnable() {
    public void run() {
        System.out.println("Anonymous class");
    }
}).start();
```

### Way 4 — Lambda Expression ✅ (Best & Simplest)
```java
new Thread(() -> System.out.println("Lambda expression")).start();
```

| Way | Code Size | Reusable | Best For |
|---|---|---|---|
| Extend Thread | Large | ❌ | Simple cases |
| Implement Runnable | Medium | ✅ | Reusable tasks |
| Anonymous Class | Medium | ❌ | One-time use |
| Lambda | Small | ❌ | Simple one-liners ✅ |

---

## 13. Runnable Interface, Thread Class and Methods

### Important Thread Methods

```java
Thread t1 = new Thread(() -> {});

t1.start();              // Start the thread
t1.getName();            // Get thread name → "Thread-0"
t1.setName("MyThread");  // Set custom name
t1.getId();              // Get thread ID
t1.getPriority();        // Get priority (1-10, default 5)
t1.setPriority(8);       // Set priority
t1.isAlive();            // Check if thread is running
t1.join();               // Wait for this thread to finish
t1.interrupt();          // Interrupt the thread
t1.getState();           // Get current state
Thread.sleep(1000);      // Pause current thread for 1 second
Thread.currentThread();  // Get reference to current thread
```

### Thread Priority Constants
```java
Thread.MIN_PRIORITY   // 1
Thread.NORM_PRIORITY  // 5 (default)
Thread.MAX_PRIORITY   // 10
```

---

## 14. Thread Life Cycle

```
new Thread()          t1.start()         run() executes
     │                    │                    │
    NEW  ──────────►  RUNNABLE  ──────────►  RUNNING
                                               │
                    ◄──────────────────────────┤
                    │                          │
              TIMED_WAITING              TERMINATED
           (sleep / join / wait)         (run() done)
                    │
              BLOCKED
           (waiting for lock)
```

```java
Thread t1 = new Thread(() -> {
    try { Thread.sleep(1000); } 
    catch (InterruptedException e) {}
});

System.out.println(t1.getState());  // NEW
t1.start();
System.out.println(t1.getState());  // RUNNABLE
Thread.sleep(100);
System.out.println(t1.getState());  // TIMED_WAITING
t1.join();
System.out.println(t1.getState());  // TERMINATED
```

| State | When |
|---|---|
| `NEW` | Thread created, not started |
| `RUNNABLE` | After `start()` called |
| `RUNNING` | CPU is executing it (shown as RUNNABLE) |
| `TIMED_WAITING` | `sleep()` called |
| `WAITING` | `join()` or `wait()` called |
| `BLOCKED` | Waiting for a `synchronized` lock |
| `TERMINATED` | `run()` finished |

---

## 15. Biggest Problem — Race Condition

A **race condition** happens when two threads access and modify shared data at the same time, causing wrong results.

```java
// ❌ Race Condition Example
class Counter {
    int count = 0;
    public void increment() {
        count++;  // NOT atomic! — 3 steps internally:
                  // Step 1: Read count
                  // Step 2: Add 1
                  // Step 3: Write back
    }
}
```

```
❌ Without Synchronization:

Thread 1 reads  count = 5
Thread 2 reads  count = 5   ← Both read SAME value!
Thread 1 writes count = 6
Thread 2 writes count = 6   ← One increment is LOST!

Expected: 7   Got: 6  ❌
```

```java
Counter c = new Counter();
Thread t1 = new Thread(() -> { for(int i=0;i<1000;i++) c.increment(); });
Thread t2 = new Thread(() -> { for(int i=0;i<1000;i++) c.increment(); });
t1.start(); t2.start();
t1.join();  t2.join();

System.out.println(c.count); // ❌ Random: 1768, 1845, 1923 (NOT 2000)
```

---

## 16. Heap, Stack & Thread Stack — Block Diagram

```
┌─────────────────────────────────────────────────────────┐
│                      JVM MEMORY                         │
│                                                         │
│   ┌─────────────────────────┐                           │
│   │        HEAP MEMORY      │  ← SHARED by ALL threads  │
│   │  ┌──────────────────┐   │                           │
│   │  │  Counter object  │   │                           │
│   │  │   count = 0      │   │  ← Both threads access!  │
│   │  └──────────────────┘   │                           │
│   └─────────────────────────┘                           │
│                                                         │
│   ┌──────────────┐   ┌──────────────┐                   │
│   │   Thread 1   │   │   Thread 2   │  ← Each thread    │
│   │    STACK     │   │    STACK     │    has OWN stack  │
│   │  ┌────────┐  │   │  ┌────────┐  │                   │
│   │  │ i = 3  │  │   │  │ i = 7  │  │  ← Local vars    │
│   │  │ temp=5 │  │   │  │ temp=5 │  │    are separate  │
│   │  └────────┘  │   │  └────────┘  │                   │
│   └──────────────┘   └──────────────┘                   │
└─────────────────────────────────────────────────────────┘

⚠️ Heap is SHARED → Race Condition happens here
✅ Stack is PRIVATE → No race condition here
```

---

## 17. Synchronization

**Synchronization** ensures only **one thread** can access a block of code at a time using a lock.

```java
class Counter {
    int count = 0;

    // ✅ synchronized = LOCK on this method
    // Only ONE thread enters at a time
    // Other threads WAIT outside
    public synchronized void increment() {
        count++;  // ✅ Now thread safe!
    }
}
```

```
✅ With Synchronization:

Thread 1 LOCKS method
Thread 1 reads  count = 5
Thread 1 writes count = 6
Thread 1 UNLOCKS method

Thread 2 LOCKS method       ← Now t2 gets the lock
Thread 2 reads  count = 6
Thread 2 writes count = 7
Thread 2 UNLOCKS method

Result: 7 ✅ Always correct!
```

### Synchronized Block (More Flexible)
```java
public void increment() {
    synchronized (this) {   // ✅ Lock only this block, not whole method
        count++;
    }
}
```

---

## 18. volatile Keyword

**volatile** solves the **visibility problem** — ensures all threads read the latest value from **main memory**, not from CPU cache.

```
❌ Without volatile:

Main Memory:  isRunning = false
                   ↑
              NOT updated!
CPU Cache T1: isRunning = true  ← Thread 1 reads stale value
CPU Cache T2: isRunning = false
```

```java
class Flag {
    // ✅ volatile = every read/write goes to MAIN MEMORY
    volatile boolean isRunning = true;
}
```

```
✅ With volatile:

Main Memory:  isRunning = false  ← Always updated immediately
                   ↑
Thread 1 reads directly from MAIN MEMORY → sees false ✅
Thread 2 reads directly from MAIN MEMORY → sees false ✅
```

### volatile vs synchronized

| Feature | `volatile` | `synchronized` |
|---|---|---|
| Fixes Visibility | ✅ Yes | ✅ Yes |
| Fixes Race Condition | ❌ No | ✅ Yes |
| Allows one thread at a time | ❌ No | ✅ Yes |
| Performance | Fast ✅ | Slower |
| Use For | Simple flags | `count++` operations |

---

## 19. Deadlock — When Synchronization Goes Wrong

**Deadlock** happens when two threads wait for each other's lock **forever**.

```
Thread 1 holds Lock A → waiting for Lock B
Thread 2 holds Lock B → waiting for Lock A

Both wait forever = DEADLOCK ☠️
```

```java
// ❌ Deadlock Example
Object lockA = new Object();
Object lockB = new Object();

Thread t1 = new Thread(() -> {
    synchronized (lockA) {              // T1 holds Lock A
        System.out.println("T1 got Lock A");
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        synchronized (lockB) {          // T1 waiting for Lock B
            System.out.println("T1 got Lock B");
        }
    }
});

Thread t2 = new Thread(() -> {
    synchronized (lockB) {              // T2 holds Lock B
        System.out.println("T2 got Lock B");
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        synchronized (lockA) {          // T2 waiting for Lock A
            System.out.println("T2 got Lock A");
        }
    }
});

t1.start();
t2.start();
// Program hangs forever! ☠️
```

### How to Avoid Deadlock

```java
// ✅ Always acquire locks in the SAME ORDER
Thread t1 = new Thread(() -> {
    synchronized (lockA) {   // Lock A first
        synchronized (lockB) {   // Then Lock B
        }
    }
});

Thread t2 = new Thread(() -> {
    synchronized (lockA) {   // Lock A first (same order!)
        synchronized (lockB) {   // Then Lock B
        }
    }
});
// ✅ No deadlock — both threads follow same lock order
```

---

## 20. Synchronization vs Performance

> ⚠️ If you synchronize everything, you **kill the purpose of multithreading!**

```
Without Sync:   T1──T2──T3──T4 all run together = FAST ✅ but UNSAFE ❌

With Sync:      T1 runs → T2 waits → T3 waits → T4 waits = SAFE ✅ but SLOW ❌
                (Threads run one by one = like single threading!)
```

### Best Practices

```java
// ❌ Bad — synchronizing too much
public synchronized void doEverything() {
    readData();     // Doesn't need sync
    processData();  // Doesn't need sync  
    count++;        // Only THIS needs sync!
    printData();    // Doesn't need sync
}

// ✅ Good — synchronize only the critical section
public void doEverything() {
    readData();
    processData();
    synchronized (this) {
        count++;    // ✅ Only lock what needs to be locked
    }
    printData();
}
```

### The Golden Balance

```
Too little sync  →  Race Condition  ❌
Too much sync    →  Deadlock / Slow performance  ❌
Just right sync  →  Safe + Fast  ✅
```

---

## 🗺️ Complete Roadmap

```
TASK
 └── Multiple tasks needed? → MULTITASKING
        ├── Multiple programs?   → MULTIPROCESSING
        └── Multiple tasks in one program? → MULTITHREADING
                └── Use THREADS
                        ├── Implement: extends Thread / implements Runnable / Lambda
                        ├── Life Cycle: NEW → RUNNABLE → RUNNING → WAITING → TERMINATED
                        ├── Problem: RACE CONDITION (shared Heap memory)
                        │       └── Fix: SYNCHRONIZATION (one thread at a time)
                        │               └── Risk: DEADLOCK (lock order matters!)
                        └── Problem: VISIBILITY (CPU cache)
                                └── Fix: volatile (read from main memory)
```

---

## 📚 Quick Reference

| Concept | Keyword / Method | Purpose |
|---|---|---|
| Create thread | `new Thread()` | Create thread object |
| Start thread | `.start()` | Create new thread + call run() |
| Thread code | `.run()` | Code to execute in thread |
| Sleep | `Thread.sleep(ms)` | Pause current thread |
| Wait for thread | `.join()` | Wait until thread finishes |
| Stop gracefully | `volatile flag` | Control thread loop |
| Interrupt | `.interrupt()` | Wake sleeping thread |
| Thread safe | `synchronized` | One thread at a time |
| Visibility fix | `volatile` | Read from main memory |
| Current thread | `Thread.currentThread()` | Get running thread |
| Thread state | `.getState()` | NEW/RUNNABLE/TERMINATED etc |

---

> 💡 **Final Tip:** Use multithreading wisely — synchronize only what is needed, avoid nested locks to prevent deadlock, and prefer `volatile` for simple flags and `synchronized` for complex operations.
