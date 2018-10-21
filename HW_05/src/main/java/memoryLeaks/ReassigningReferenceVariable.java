package memoryLeaks;

public class ReassigningReferenceVariable extends MemoryLeaks {

/* Class to demonstrate gc when one object referred to other object */

    public ReassigningReferenceVariable(String name) {
        super(name);
    }

    // Driver method
    @Override
    public void run() {
        while (true) {

            ReassigningReferenceVariable r1 = new ReassigningReferenceVariable("r1");
            ReassigningReferenceVariable r2 = new ReassigningReferenceVariable("r2");
            //r1 now referred to r2
            r1 = r2;
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
