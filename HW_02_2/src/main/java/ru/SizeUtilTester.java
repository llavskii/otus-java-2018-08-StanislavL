package ru;

import static ru.SizeUtil.*;

public class SizeUtilTester {
    int a = 300;
    String s = "SizeUtilTester";

    //для запуска java -javaagent:HW_02_2-1.0-SNAPSHOT.jar -jar HW_02_2-1.0-SNAPSHOT.jar
    public static void main(String[] args) {
        //Проверка на наличие значения в пуле
        System.out.println(isPooled(127));
        System.out.println(isPooled(128));

        boolean bo1 = true;
        System.out.println("Retained space for boolean bo1 = true --> " + deepSizeOfObject(bo1));
        //Сравнение значений возвращаемых методами deepSizeOfObject/shallowSizeOfObject для примитивов
        System.out.println("Retained shallow space for boolean --> " + shallowSizeOfObject(bo1));

        byte b1 = 3;
        System.out.println("Retained space for byte b1 = 3 --> " + deepSizeOfObject(b1));

        short sh1 = 3;
        System.out.println("Retained space for short sh1 = 3 --> " + deepSizeOfObject(sh1));

        int i1 = 3;
        System.out.println("Retained space for int i1 = 3 --> " + deepSizeOfObject(i1));

        long l1 = 3L;
        System.out.println("Retained space for long l1 = 3L --> " + deepSizeOfObject(l1));

        char c1 = 'A';
        System.out.println("Retained space for char c1 = 'A' --> " + deepSizeOfObject(c1));

        float f1 = 3f;
        System.out.println("Retained space for float f1 = 3f --> " + deepSizeOfObject(f1));

        double d1 = 3;
        System.out.println("Retained space for double d1 = 3 --> " + deepSizeOfObject(d1));

        //Сравнение значений возвращаемых методами deepSizeOfObject/shallowSizeOfObject для ссылочных типов
        Object o = new Object();
        System.out.println("Retained shallow space for Object o = new Object()) --> " + shallowSizeOfObject(o));
        System.out.println("Retained space for Object o = new Object() --> " + deepSizeOfObject(o));

        Object obj = new String("test");
        System.out.println("Retained shallow space for Object obj = new String(\"test\") --> " + shallowSizeOfObject(obj));
        System.out.println("Retained deep space for Object obj = new String(\"test\") --> " + deepSizeOfObject(obj));

        String kkk = "ккк";
        System.out.println("Retained shallow space for String kkk = \"ккк\" --> " + shallowSizeOfObject(kkk));
        System.out.println("Retained space for String kkk = \"ккк\" --> " + deepSizeOfObject(kkk));

        byte[] arrByte = new byte[20];
        System.out.println("Retained shallow space for byte[] arrByte = new byte[20] --> " + shallowSizeOfObject(arrByte));
        System.out.println("Retained deep space for byte[] arrByte = new byte[20] --> " + deepSizeOfObject(arrByte));

        Byte[] arrByteObj = new Byte[]{3, 4, 5};
        System.out.println("Retained shallow space for Byte[] arrByteObj = new Byte[] { 3, 4, 5 } --> " + shallowSizeOfObject(arrByteObj));
        System.out.println("Retained deep space for Byte[] arrByteObj = new Byte[] { 3, 4, 5 } --> " + deepSizeOfObject(arrByteObj));

        int[] arrInt = new int[]{0};
        System.out.println("Retained shallow space for int[] arrInt = new int[] { 0 } --> " + shallowSizeOfObject(arrInt));
        System.out.println("Retained deep space for int[] arrInt = new int[] { 0 } --> " + deepSizeOfObject(arrInt));

        String[] array4String = {"1", "2", "3", "4"};
        System.out.println("Retained shallow space for String[] array4String = new String[4] --> " + shallowSizeOfObject(array4String));
        System.out.println("Retained deep space for String[] array4String = new String[4] --> " + deepSizeOfObject(array4String));

        String[] array4StringIsNull = new String[4];
        System.out.println("Retained shallow space for String[] array4StringIsNull = new String[4] --> " + shallowSizeOfObject(array4StringIsNull));
        System.out.println("Retained deep space for String[] array4StringIsNull = new String[4] --> " + deepSizeOfObject(array4StringIsNull));

        SizeUtilTester sizeUtilTester = new SizeUtilTester();
        System.out.println("Retained shallow space for SizeUtilTester sizeUtilTester = new SizeUtilTester() --> " + shallowSizeOfObject(sizeUtilTester));
        System.out.println("Retained deep space for SizeUtilTester sizeUtilTester = new SizeUtilTester() --> " + deepSizeOfObject(sizeUtilTester));
    }

}
