package memoryLeaksTest.benchMark;

import memoryLeaks.*;

public class Benchmark implements BenchmarkMBean {

    private volatile int size = 0;

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {

        System.out.println("Starting the loop");
        StatsGCperMinute statsGCperMinute = new StatsGCperMinute();
        statsGCperMinute.start();

        ObjectInsideMethod objectInsideMethod = new ObjectInsideMethod("");
        ReassigningReferenceVariable reassigningReferenceVariable = new ReassigningReferenceVariable("");
        NullifyingReferenceVariable nullifyingReferenceVariable = new NullifyingReferenceVariable("");
        AnonymousObject anonymousObject = new AnonymousObject("");
        AddMoreThanRemoveList addMoreThanRemoveList = new AddMoreThanRemoveList("");
        objectInsideMethod.start();
        reassigningReferenceVariable.start();
        nullifyingReferenceVariable.start();
        anonymousObject.start();
        addMoreThanRemoveList.start();

        while (true) {
            int local = size;
            Object[] array = new Object[local];
//            System.out.println("Benchmark: array of size: " + array.length + " created");

            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
//            System.out.println("Benchmark: created " + local + " objects.");
            GCmetrics.printGCMetrics();

        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
