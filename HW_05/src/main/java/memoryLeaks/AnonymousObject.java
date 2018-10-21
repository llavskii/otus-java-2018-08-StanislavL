package memoryLeaks;


/* Class to demonstrate gc of anonymous objects */
public class AnonymousObject extends MemoryLeaks {
    public AnonymousObject(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true) {
            new AnonymousObject("an1");
//            System.gc();
            try {
                Thread.sleep(timeThreadSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
