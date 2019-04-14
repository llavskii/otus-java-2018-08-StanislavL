package ru.anotherJGson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static ru.anotherJGson.DataTypesHelper.*;

public class AnotherJGson {


    public static String toJson(Object obj) {
        //Check for null object
        if (obj == null) return "null";
        return toJson(obj, obj.getClass());
    }

    private static String toJson(Object obj, Class clazz) {
        //Check for single value of  String or primitives
        if (isStringType(clazz) | isCommonNotStringType(clazz)) return JSONValue.toJSONString(obj);
        //Check for array as single value
        if (clazz.isArray()) return getJsonArray(obj).toJSONString();
        //Check for collection
        if (obj instanceof Collection) return getJsonList(obj).toJSONString();
        //Check for collection
        if (obj instanceof Map) return getJsonMap(obj).toJSONString();
        return getJsonObject(obj, clazz).toJSONString();
    }

    //Method with recursive call to get Json object
    private static JSONObject getJsonObject(Object obj, Class clazz) {
        JSONObject jsonObject = new JSONObject();
        List<Field> fields = getAllFields(clazz);
        fields.forEach(field -> {
            Class fieldClass = field.getType();
            //Check to constant
            if (isPublicStaticFinal(field)) return;
            if (!field.isAccessible()) field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(obj);
                if (fieldValue == null) return;
                else if (isCommonNotStringType(fieldClass) | isStringType(fieldClass))
                    jsonObject.put(field.getName(), fieldValue);
                else if (fieldClass.isArray()) {
                    jsonObject.put(field.getName(), getJsonArray(fieldValue));
                } else if (fieldValue instanceof Collection) {
                    jsonObject.put(field.getName(), getJsonList(fieldValue));
                } else if (fieldValue instanceof Map) {
                    jsonObject.put(field.getName(), getJsonMap(fieldValue));
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

    private static JSONObject getJsonMap(Object obj) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll((Map) obj);
        return jsonObject;
    }

    private static JSONArray getJsonList(Object obj) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll((Collection) obj);
        return jsonArray;
    }

    private static JSONArray getJsonArray(Object obj) {
        String primType = Array.get(obj, 0).getClass().getSimpleName();
        JSONArray jsonArray = new JSONArray();
        switch (primType) {
            case "int":
            case "Integer":
            case "short":
            case "Short":
            case "byte":
            case "Byte":
                jsonArray.addAll(Arrays.stream((int[]) obj).boxed().collect(Collectors.toList()));
                break;
            case "double":
            case "Double":
                jsonArray.addAll(Arrays.stream((double[]) obj).boxed().collect(Collectors.toList()));
                break;
            case "String":
                jsonArray.addAll(Arrays.stream((String[]) obj).collect(Collectors.toList()));
                break;
        }
        return jsonArray;
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
