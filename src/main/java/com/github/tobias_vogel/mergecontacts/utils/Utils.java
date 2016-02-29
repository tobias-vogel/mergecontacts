package com.github.tobias_vogel.mergecontacts.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;

public class Utils {

    public static void failsafeMapAppend(Map<String, Set<CardDavContact>> map, String key,
            CardDavContact contactToAdd) {
        // TODO does fastutil offer such thing?
        Set<CardDavContact> list;
        if (map.containsKey(key)) {
            list = map.get(key);
        } else {
            list = new HashSet<>();
        }

        list.add(contactToAdd);

        map.put(key, list);
    }





    public static <K, V> void appendMapOrDieIfKeyExists(Map<K, V> map, K key, V value) {
        // TODO research, whether there is a known mechanism for this

        if (map.containsKey(key)) {
            throw new RuntimeException("The key \"" + key + "\" already existed in the map.");
        } else {
            map.put(key, value);
        }
    }

    // @Deprecated
    // public static void appendMapOrDieIfKeyExists(Map<String, Class<? extends
    // DataSource>> map, String key,
    // Class<? extends DataSource> value) {
    // // TODO research, whether there is a known mechanism for this
    //
    // if (map.containsKey(key)) {
    // throw new RuntimeException("The key \"" + key + "\" already existed in
    // the map.");
    // } else {
    // map.put(key, value);
    // }
    // }
}