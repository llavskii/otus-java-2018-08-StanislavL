package ru.anotherJGson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Field;
import java.util.*;

public class AnotherJGson {

    //Constants for divide saving variables with or not quotes
    private static final Set<Class> STRING_COMMON_TYPES = getStringWrapperTypes();
    private static final Set<Class> NOT_STRING_COMMON_TYPES = getNumericAndBooleanWrapperTypes();

    public static String toJson(Object obj) {
        //Check for null object
        if (obj == null) return "null";
        return toJson(obj, obj.getClass());
    }

    private static String toJson(Object obj, Class clazz) {
        //Check for single value object
        if (isStringType(clazz) | isCommonNotStringType(clazz)) return JSONValue.toJSONString(obj);

        return getJsonObject(obj, clazz).toJSONString();
    }

    private static boolean isCommonNotStringType(Class clazz) {
        return NOT_STRING_COMMON_TYPES.contains(clazz);
    }

    private static boolean isStringType(Class clazz) {
        return STRING_COMMON_TYPES.contains(clazz);
    }

    //Method with recursive call to get Json object
    private static JSONObject getJsonObject(Object obj, Class clazz) {
        JSONObject jsonObject = new JSONObject();
        List<Field> fields = getAllFields(clazz);
        fields.forEach(field -> {
            Class fieldClass = field.getType();
            if (!field.isAccessible()) field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(obj);
                if (fieldValue == null) return;
                else if (isCommonNotStringType(fieldClass) | isStringType(fieldClass))
                    jsonObject.put(field.getName(), fieldValue);
                else if (fieldClass.isArray()) {
                    JSONArray list = new JSONArray();
                    Object[] arr = (Object[]) fieldValue;
                    for (Object objArr : arr) {
                        list.add(objArr);
                    }
                    jsonObject.put(field.getName(), list);
                } else if (fieldValue instanceof Collection) {
                    JSONArray list = new JSONArray();
                    list.addAll((List) fieldValue);
                    jsonObject.put(field.getName(), list);

                } else if (fieldValue instanceof Map) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.putAll((Map) fieldValue);
                    jsonObject.put(field.getName(), jsonObject1);

                } else {
                    //Recursive call to get Json object
                    JSONObject jsonObject1 = getJsonObject(fieldValue, fieldClass);
                    jsonObject.put(field.getName(), jsonObject1);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return jsonObject;
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
        ret.add(char.class);
        ret.add(String.class);
        return ret;
    }

    //Recursive call to get all fields of object
    private static List<Field> getAllFields(Class klass) {
        List<Field> fields = new ArrayList<>(Arrays.asList(klass.getDeclaredFields()));
        if (klass.getSuperclass() != null) {
            fields.addAll(getAllFields(klass.getSuperclass()));
        }
        return fields;
    }
}
