package ru;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

class ClassTransformer implements ClassFileTransformer {
    private static int count = 0;

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("load class: " + className.replaceAll("/", "."));
        System.out.println(String.format("loaded %s classes", ++count));
        return classfileBuffer;
    }
}
