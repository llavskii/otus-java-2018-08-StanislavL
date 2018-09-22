package ru;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;

public class SizeUtil {

    private static boolean SKIP_POOLED_OBJECTS = false;

    private static Instrumentation instrumentation = null;

    public static void premain(String args, Instrumentation paramInstrumentation) {
        instrumentation = paramInstrumentation;
    }

    //Метод для простого подсчета размера, используя Instrumentation, результат - только хедер.
    //Перед подсчетом размера проверяется, не из пула ли объект. Например, для int = 127 - из пула, int = 128 - нет
    public static long shallowSizeOfObject(Object obj) {
        if (SKIP_POOLED_OBJECTS && isPooled(obj))
            return 0;
        return instrumentation.getObjectSize(obj);
    }

    //Метод для проверки на наличие значения объекта в пуле
    public static boolean isPooled(Object paramObject) {
        if ((paramObject instanceof Comparable)) {
            if ((paramObject instanceof Enum)) {
                return true;
            }
            if ((paramObject instanceof String)) {
                return paramObject == ((String) paramObject).intern();
            }
            if ((paramObject instanceof Boolean)) {
                return (paramObject == Boolean.TRUE) || (paramObject == Boolean.FALSE);
            }
            if ((paramObject instanceof Integer)) {
                return paramObject == Integer.valueOf(((Integer) paramObject).intValue());
            }
            if ((paramObject instanceof Short)) {
                return paramObject == Short.valueOf(((Short) paramObject).shortValue());
            }
            if ((paramObject instanceof Byte)) {
                return paramObject == Byte.valueOf(((Byte) paramObject).byteValue());
            }
            if ((paramObject instanceof Long)) {
                return paramObject == Long.valueOf(((Long) paramObject).longValue());
            }
            if ((paramObject instanceof Character)) {
                return paramObject == Character.valueOf(((Character) paramObject).charValue());
            }
        }
        return false;
    }

    //Метод для проверки пропускать ли объект для подсчета его размера или нет
    private static boolean skipObject(Object obj, Map<Object, Object> previouslyVisited) {
        if (SKIP_POOLED_OBJECTS && isPooled(obj))
            return true;
        return (obj == null) || previouslyVisited == null || previouslyVisited.containsKey(obj);
    }

    //Метод для вызова глубокого подсчета размера объекта
    public static long deepSizeOfObject(Object obj) {
        Map<Object, Object> previouslyVisited = new IdentityHashMap<Object, Object>();
        long result = deepSizeOf(obj, previouslyVisited);
        previouslyVisited.clear();
        return result;
    }

    //Метод для глубокого подсчета размера объекта в памяти
    @SuppressWarnings("rawtypes")
    private static long deepSizeOf(Object obj, Map<Object, Object> previouslyVisited) {
        if (skipObject(obj, previouslyVisited)) {
            return 0;
        }
        previouslyVisited.put(obj, null);

        long returnVal = 0;

        // 1. получаем размер объекта с учетом размера примитивов и полей
        // 2. суммируем хедер массива с объемом занимаемым памятью для примитивов
        returnVal += shallowSizeOfObject(obj);
        Class objClass = obj.getClass();
        if (objClass == null) {
            return 0;
        }

        // Рекурсивный вызов всех элементов массива
        if (objClass.isArray()) {
            if (objClass.getName().length() != 2) {
                //Если размер массива примитивных типов равен 2, пропускаем его (они включены в shallowSize)
                //https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.3
                int lengthOfArray = Array.getLength(obj);
                for (int i = 0; i < lengthOfArray; i++) {
                    returnVal += deepSizeOf(Array.get(obj, i), previouslyVisited);
                }
            }
        } else {// рекурсивный вызов всех полей объекта включая поля суперкласса
            do {
                Field[] objFields = objClass.getDeclaredFields();
                for (int i = 0; i < objFields.length; i++) {
                    if (!Modifier.isStatic(objFields[i].getModifiers())) {//пропускаем статики
                        if (!objFields[i].getType().isPrimitive()) { // пропускаем примитивы
                            objFields[i].setAccessible(true);
                            Object tempObject = null;
                            try {
                                tempObject = objFields[i].get(obj);
                            } catch (IllegalArgumentException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            if (tempObject != null) {
                                returnVal += deepSizeOf(tempObject, previouslyVisited);
                            }
                        }
                    }
                }
                objClass = objClass.getSuperclass();
            } while (objClass != null);
        }
        return returnVal;
    }
}

