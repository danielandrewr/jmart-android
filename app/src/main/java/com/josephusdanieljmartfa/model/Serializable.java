package com.josephusdanieljmartfa.model;

import java.util.HashMap;
import java.util.Map;

public class Serializable {

    public int id;
    private static Map<Class<?>, Integer> mapCounter = new HashMap<Class<?>, Integer>();

    protected Serializable() {
        Integer counter = mapCounter.get(getClass());
        if (counter == null) {
            mapCounter.put(getClass(), 1);
        } else {
            mapCounter.put(getClass(), counter + 1);
        }
        this.id = mapCounter.get(getClass());
    }

    public static <T extends Serializable> Integer setClosingId(Class<T> clasz, int id) {
        if (clasz.getSuperclass() == Serializable.class) {
            return mapCounter.put(clasz, id);
        }
        return 0;
    }

    public static <T extends Serializable> Integer getClosingId(Class<T> clasz) {
        if (clasz.getSuperclass() == Serializable.class) {
            return mapCounter.get(clasz);
        }
        return 0;
    }

    public boolean equals(Serializable recognizeable) {
        if (this.id == recognizeable.id) {
            return true;
        }

        return false;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Serializable) {
            Serializable recog = (Serializable) obj;
            if (recog.id == this.id) {
                return true;
            }
        }
        return false;
    }
}
