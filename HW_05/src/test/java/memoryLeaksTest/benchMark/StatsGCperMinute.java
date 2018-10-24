package memoryLeaksTest.benchMark;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

public class StatsGCperMinute extends TimerTask {
    private final Set<String> YOUNG_GARBAGE_COLLECTORS = new HashSet<>(3);
    private final Set<String> OLD_GARBAGE_COLLECTORS = new HashSet<>(3);
    private Set<String> UNKNOWN_GARBAGE_COLLECTOR = new HashSet<>();

    private static long minorCountBefore;
    private static long minorTimeBefore;
    private static long majorCountBefore;
    private static long majorTimeBefore;
    private static long unknownCountBefore;
    private static long unknownTimeBefore;
    private static int minuteCount;

    {
        // young generation GCmetrics names
        YOUNG_GARBAGE_COLLECTORS.add("PS Scavenge");
        YOUNG_GARBAGE_COLLECTORS.add("ParNew");
        YOUNG_GARBAGE_COLLECTORS.add("G1 Young Generation");

        // old generation GCmetrics names
        OLD_GARBAGE_COLLECTORS.add("PS MarkSweep");
        OLD_GARBAGE_COLLECTORS.add("ConcurrentMarkSweep");
        OLD_GARBAGE_COLLECTORS.add("G1 Old Generation");
    }

    @Override
    public void run() {

        long minorCount = 0;
        long minorTime = 0;
        long majorCount = 0;
        long majorTime = 0;
        long unknownCount = 0;
        long unknownTime = 0;

        List<GarbageCollectorMXBean> mxBeans
                = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gc : mxBeans) {
            long count = gc.getCollectionCount();
            if (YOUNG_GARBAGE_COLLECTORS.contains(gc.getName())) {
                minorCount = count - minorCountBefore;
                minorTime = gc.getCollectionTime() - minorTimeBefore;
            } else if (OLD_GARBAGE_COLLECTORS.contains(gc.getName())) {
                majorCount = count - majorCountBefore;
                majorTime = gc.getCollectionTime() - majorTimeBefore;
            } else {
                unknownCount = count - unknownCountBefore;
                unknownTime = gc.getCollectionTime() - unknownTimeBefore;
                UNKNOWN_GARBAGE_COLLECTOR.add(gc.getName());
            }
        }
        minuteCount++;

        StringBuilder sb = new StringBuilder();
        sb.append("Stats per ")
                .append(minuteCount)
                .append(" minute: MinorGC -> Count: ")
                .append(minorCount)
                .append(", Time (ms): ").append(minorTime)
                .append(", MajorGC -> Count: ").append(majorCount)
                .append(", Time (ms): ").append(majorTime);

        if (unknownCount > 0) {
            sb.append(", UnknownGC: ")
                    .append(UNKNOWN_GARBAGE_COLLECTOR.toString())
                    .append(" -> Count: ").append(unknownCount)
                    .append(", Time (ms): ").append(unknownTime);
        }
        System.out.println(sb);
        minorCountBefore = minorCount;
        minorTimeBefore = minorTime;
        majorCountBefore = majorCount;
        majorTimeBefore = majorTime;
        unknownCountBefore = unknownCount;
        unknownTimeBefore = unknownTime;
    }
}
