package memoryLeaksTest.benchMark;

import memoryLeaks.*;

import java.util.Timer;

public class Benchmark implements BenchmarkMBean {

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
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

        System.out.println("Starting benchmark...");
        Timer timer = new Timer();
        StatsGCperMinute statsGCperMinute = new StatsGCperMinute();
        timer.schedule(statsGCperMinute, 60_000, 60_000);
    }
}
