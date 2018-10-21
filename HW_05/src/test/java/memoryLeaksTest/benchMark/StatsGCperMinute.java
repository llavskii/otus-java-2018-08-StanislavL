package memoryLeaksTest.benchMark;

import static memoryLeaksTest.benchMark.GCmetrics.*;

public class StatsGCperMinute extends Thread {

    @Override
    public void run() {
        int minuteCount = 0;
        try {
            while (true) {
                long minorCountBefore = minorCount;
                long minorTimeBefore = minorTime;
                long majorCountBefore = majorCount;
                long majorTimeBefore = majorTime;
                long unknownCountBefore = unknownCount;
                long unknownTimeBefore = unknownTime;

                Thread.sleep(60 * 1000);
                long minorCountPerMinute = minorCount - minorCountBefore;
                long minorTimePerMinute = minorTime - minorTimeBefore;
                long majorCountPerMinute = majorCount - majorCountBefore;
                long majorTimePerMinute = majorTime - majorTimeBefore;
                long unknownCountPerMinute = unknownCount - unknownCountBefore;
                long unknownTimePerMinute = unknownTime - unknownTimeBefore;

                minuteCount++;
                StringBuilder sb = new StringBuilder();
                        sb.append("_*** Stats per ")
                        .append(minuteCount)
                        .append(" minute: ***_ \n_*** MinorGC -> Count: ")
                        .append(minorCountPerMinute)
                        .append(", Time (s): ").append(minorTimePerMinute/1000)
                        .append(", MajorGC -> Count: ").append(majorCountPerMinute)
                        .append(", Time (s): ").append(majorTimePerMinute/1000);

                if (unknownCount > 0) {
                    sb.append(", UnknownGC: ")
                            .append(UNKNOWN_GARBAGE_COLLECTOR.toString())
                            .append(" -> Count: ").append(unknownCountPerMinute)
                            .append(", Time (s): ").append(unknownTimePerMinute/1000);
                }
                System.out.println(sb);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
