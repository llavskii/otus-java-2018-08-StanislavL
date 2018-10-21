package memoryLeaks;

/* Class to demonstrate that objects created inside a method will becomes
eligible for gc after method execution terminate */
public class ObjectInsideMethod extends MemoryLeaks {
    public static int count;

    public ObjectInsideMethod(String name) {
        super(name);
    }

    public void show() {
        //object t1 inside method becomes unreachable when show() removed
        ObjectInsideMethod o1 = new ObjectInsideMethod("o1");
        display();
    }

    public void display() {
        //object t2 inside method becomes unreachable when display() removed
        ObjectInsideMethod o2 = new ObjectInsideMethod("o2");
    }

    // Driver method
    public void run() {
        // calling show()
        while (true) {
            show();
            // calling garbage collector
            //System.gc();
            try {
                Thread.sleep(timeThreadSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
