package com.github.tobias_vogel.mergecontacts.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;

public class Utils {

    public static void failsafeMapAppend(Map<String, Set<CardDavContact>> map, String key,
            CardDavContact contactToAdd) {
        Set<CardDavContact> list;
        if (map.containsKey(key)) {
            list = map.get(key);
        } else {
            list = new HashSet<>();
        }

        list.add(contactToAdd);

        map.put(key, list);
    }
}