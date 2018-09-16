package ru;

import java.lang.instrument.Instrumentation;

//Класс, объединяющий функции классов JavaAgentClassCount, JavaAgentMemoryCount
public class JavaAgentClassAndMemoryCount {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("JavaAgentClassAndMemoryCount started...");
        JavaAgentClassAndMemoryCount.instrumentation = instrumentation;
        instrumentation.addTransformer(new ClassTransformer());
    }

    public static long getSize(Object obj) {
        if (instrumentation == null) {
            throw new IllegalStateException("Instrumentation of MemoryCount not initialised!");
        }
        return instrumentation.getObjectSize(obj);
    }
}
