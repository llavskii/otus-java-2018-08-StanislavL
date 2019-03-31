package ru.anotherJGson;

import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.*;

public class AnotherJGson {

    private static final Set<Class> STRING_TYPES = getStringWrapperTypes();
    private static final Set<Class> NOT_STRING_TYPES = getNumericAndBooleanWrapperTypes();

    public String toJson(Object obj) {
        if (obj == null) return "null";
        return toJson(obj, obj.getClass());
    }

    private String toJson(Object obj, Class clazz) {

        if (isStringType(clazz)) return "\"" + obj.toString() + "\"";
        if (isNotStringType(clazz)) return obj.toString();

        return generateJson(obj, clazz);
    }

    private static boolean isNotStringType(Class clazz) {
        return NOT_STRING_TYPES.contains(clazz);
    }

    private static boolean isStringType(Class clazz) {
        return STRING_TYPES.contains(clazz);
    }

    private static String generateJson(Object obj, Class clazz) {
        JSONObject jsonObject = new JSONObject();
        List<Field> fields = getAllFields(clazz);
        fields.forEach(field -> {
            Class fieldClass = field.getType();
            if (!field.isAccessible()) field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(obj);
                if (fieldValue == null) return;
                else if (isNotStringType(fieldClass) | isStringType(fieldClass))
                    jsonObject.put(field.getName(), fieldValue);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return jsonObject.toJSONString();
    }

    private static Set<Class> getNumericAndBooleanWrapperTypes() {
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

    private static Set<Class> getStringWrapperTypes() {
        Set<Class> ret = new HashSet<>();
        ret.add(Character.class);
        ret.add(String.class);
        return ret;
    }

    public static List<Field> getAllFields(Class klass) {
        List<Field> fields = new ArrayList<>(Arrays.asList(klass.getDeclaredFields()));
        if (klass.getSuperclass() != null) {
            fields.addAll(getAllFields(klass.getSuperclass()));
        }
        return fields;
    }
}
