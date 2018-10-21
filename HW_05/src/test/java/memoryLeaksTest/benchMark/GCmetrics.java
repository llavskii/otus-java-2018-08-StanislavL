package memoryLeaksTest.benchMark;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GCmetrics {
    static final Set<String> YOUNG_GARBAGE_COLLECTORS = new HashSet<>(3);
    static final Set<String> OLD_GARBAGE_COLLECTORS = new HashSet<>(3);
    static Set<String> UNKNOWN_GARBAGE_COLLECTOR = new HashSet<>();
    static volatile long minorCount = 0;
    static volatile long minorTime = 0;
    static volatile long majorCount = 0;
    static volatile long majorTime = 0;
    static volatile long unknownCount = 0;
    static volatile long unknownTime = 0;

    static {
        // young generation GCmetrics names
        YOUNG_GARBAGE_COLLECTORS.add("PS Scavenge");
        YOUNG_GARBAGE_COLLECTORS.add("ParNew");
        YOUNG_GARBAGE_COLLECTORS.add("G1 Young Generation");

        // old generation GCmetrics names
        OLD_GARBAGE_COLLECTORS.add("PS MarkSweep");
        OLD_GARBAGE_COLLECTORS.add("ConcurrentMarkSweep");
        OLD_GARBAGE_COLLECTORS.add("G1 Old Generation");
    }

    public static void printGCMetrics() {
        List<GarbageCollectorMXBean> mxBeans
                = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gc : mxBeans) {
            long count = gc.getCollectionCount();
            if (count >= 0) {
                if (YOUNG_GARBAGE_COLLECTORS.contains(gc.getName())) {
                    minorCount += count;
                    minorTime += gc.getCollectionTime();
                } else if (OLD_GARBAGE_COLLECTORS.contains(gc.getName())) {
                    majorCount += count;
                    majorTime += gc.getCollectionTime();
                } else {
                    unknownCount += count;
                    unknownTime += gc.getCollectionTime();
                    UNKNOWN_GARBAGE_COLLECTOR.add(gc.getName());
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("MinorGC -> Count: ").append(minorCount)
                .append(", Time (ms): ").append(minorTime)
                .append(", MajorGC -> Count: ").append(majorCount)
                .append(", Time (ms): ").append(majorTime);

        if (unknownCount > 0) {
            sb.append(", UnknownGC: ")
                    .append(UNKNOWN_GARBAGE_COLLECTOR.toString())
                    .append(" -> Count: ").append(unknownCount)
                    .append(", Time (ms): ").append(unknownTime);
        }
//        System.out.println(sb);
    }
}
