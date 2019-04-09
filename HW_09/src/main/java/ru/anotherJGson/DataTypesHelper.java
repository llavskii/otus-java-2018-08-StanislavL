package ru.anotherJGson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class DataTypesHelper {
    //Constants for divide saving variables with or not quotes
    private static final Set<Class> STRING_COMMON_TYPES = getStringWrapperTypes();
    private static final Set<Class> NOT_STRING_COMMON_TYPES = getNumericAndBooleanWrapperTypes();

    static boolean isCommonNotStringType(Class clazz) {
        return NOT_STRING_COMMON_TYPES.contains(clazz);
    }

    static boolean isStringType(Class clazz) {
        return STRING_COMMON_TYPES.contains(clazz);
    }

    static boolean isPublicStaticFinal(Field field) {
        int modifiers = field.getModifiers();
        return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier
                .isFinal(modifiers));
    }

    static Set<Class> getNumericAndBooleanWrapperTypes() {
        Set<Class> ret = new HashSet<>();
        ret.add(Boolean.class);
        ret.add(boolean.class);
        ret.add(Byte.class);
        ret.add(byte.class);
        ret.add(Short.class);
        ret.add(short.class);
        ret.add(Integer.class);
        ret.add(int.class);
        ret.add(Long.class);
        ret.add(long.class);
        ret.add(Float.class);
        ret.add(float.class);
        ret.add(Double.class);
        ret.add(double.class);
        ret.add(Void.class);
        ret.add(void.class);
        return ret;
    }

    static Set<Class> getStringWrapperTypes() {
        Set<Class> ret = new HashSet<>();
        ret.add(Character.class);
        ret.add(char.class);
        ret.add(String.class);
        return ret;
    }
}
