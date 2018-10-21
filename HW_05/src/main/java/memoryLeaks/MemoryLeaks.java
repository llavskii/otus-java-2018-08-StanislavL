package memoryLeaks;

public abstract class MemoryLeaks extends Thread {
    public static int timeThreadSleep = 0;

    public MemoryLeaks(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    protected void finalize() throws Throwable {
        // will print name of object
//        System.out.println(String.format("%1$s %2$s successfully garbage collected", this.getClass().getSimpleName(), this));
    }

}
