package memoryLeaksTest;

import memoryLeaksTest.benchMark.Benchmark;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class MemoryLeaksTest {
    public static void main(String args[]) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Benchmark mbean = new Benchmark();
        mBeanServer.registerMBean(mbean, new ObjectName("ru.benchmark:type=BenchmarkMemory"));
        mbean.run();
    }
}
