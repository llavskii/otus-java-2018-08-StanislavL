package memoryLeaks;

/* Class to demonstrate gc when object reference changed to NULL */
public class NullifyingReferenceVariable extends MemoryLeaks {
    public NullifyingReferenceVariable(String name) {
        super(name);
    }

    // Driver method
    public void run() {
        while (true) {
            NullifyingReferenceVariable n1 = new NullifyingReferenceVariable("n1");
            /* t1 being used for some purpose in program */
            /* When there is no more use of t1, make the object
            referred by t1 eligible for garbage collection */
            n1 = null;
            // calling garbage collector
//            System.gc();
            try {
                Thread.sleep(timeThreadSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
