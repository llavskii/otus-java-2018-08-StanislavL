package ru.anotherJGson;

public class AnotherJGson {

    public String toJson(Object obj) throws Exception {
        if (obj == null) throw new Exception("Object for serialization must not be null!");
        return toJson(obj, obj.getClass());
    }

    private String toJson(Object obj, Class<?> clazz) {
        return null;
    }
}
