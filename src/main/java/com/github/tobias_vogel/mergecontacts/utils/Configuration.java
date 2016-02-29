package com.github.tobias_vogel.mergecontacts.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class Configuration {

    private static final String PROPERTIES_FILE = "settings.properties";

    private static final String CARDDAVCONTACT_STRING = "^carddavcontactattribute-";

    private static final Pattern CARDDAVCONTACTATTRIBUTE_RECOGNITION_PATTERN = Pattern
            .compile(CARDDAVCONTACT_STRING + ".+");

    private static final Pattern CARDDAVCONTACTATTRIBUTE_EXTRACTION_PATTERN = Pattern.compile(CARDDAVCONTACT_STRING);

    private static final Pattern DEFAULT_RECOGNITION_PATTERN = Pattern.compile("^default.+");

    private static final Splitter VALUE_SPLITTER = Splitter.on(Pattern.compile(", ?"));

    private static boolean propertiesLoaded = false;

    private static Map<String, CardDavContactAttributes> ALIAS_2_CARDDAVCONTACTATTRIBUTE_MAPPING;

    private static Map<String, String> DEFAULTS;





    public static void loadProperties(String filename) {
        initializeMaps();

        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(new File(filename));) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties file \"" + filename + "\".");
        }

        try {
            fillMapsFromProperties(properties);
        } catch (Exception e) {
            throw new RuntimeException(
                    "The properties file \"" + filename + "\" is inconsistent. " + e.getLocalizedMessage());
        }
        propertiesLoaded = true;
    }





    /* package */ static void initializeMaps() {
        ALIAS_2_CARDDAVCONTACTATTRIBUTE_MAPPING = new HashMap<>();
        DEFAULTS = new HashMap<>();
    }





    private static void fillMapsFromProperties(Properties properties) {
        // TODO use java8 lambda magic for filtering

        Set<Entry<Object, Object>> defaultEntries = new HashSet<>();
        Set<Entry<Object, Object>> aliasEntries = new HashSet<>();

        for (Entry<Object, Object> entry : properties.entrySet()) {
            String key = ((String) entry.getKey()).toLowerCase();

            if (DEFAULT_RECOGNITION_PATTERN.matcher(key).matches()) {
                defaultEntries.add(entry);
            } else if (CARDDAVCONTACTATTRIBUTE_RECOGNITION_PATTERN.matcher(key).matches()) {
                aliasEntries.add(entry);
            } else {
                throw new RuntimeException("Strange content found while parsing a properties file.");
            }
        }

        fillDefaultsFromProperties(defaultEntries);
        fillAliasesFromProperties(aliasEntries);
    }





    private static void fillAliasesFromProperties(Set<Entry<Object, Object>> aliasEntries) {
        for (Entry<Object, Object> aliasEntry : aliasEntries) {
            String key = (String) aliasEntry.getKey();
            String value = (String) aliasEntry.getValue();

            CardDavContactAttributes attribute = getCardDavContactAttributeFromKey(key);
            List<String> values = convertValueToList(value);

            for (String singleValue : values) {
                Utils.appendMapOrDieIfKeyExists(ALIAS_2_CARDDAVCONTACTATTRIBUTE_MAPPING, singleValue, attribute);
            }
        }
    }





    private static CardDavContactAttributes getCardDavContactAttributeFromKey(String key) {
        key = CARDDAVCONTACTATTRIBUTE_EXTRACTION_PATTERN.matcher(key.toLowerCase()).replaceFirst("");
        CardDavContactAttributes attribute = CardDavContactAttributes.valueOf(key.toUpperCase());
        return attribute;
    }





    private static void fillDefaultsFromProperties(Set<Entry<Object, Object>> defaultEntries) {
        for (Entry<Object, Object> defaultEntry : defaultEntries) {
            String key = (String) defaultEntry.getKey();
            String value = (String) defaultEntry.getValue();

            DEFAULTS.put(key, value);
        }
    }





    public static void loadDefaultProperties() {
        loadProperties(PROPERTIES_FILE);
    }





    private static List<String> convertValueToList(String value) {
        // TODO use java8 lambda magic
        return new ArrayList<>(Arrays.asList(Iterables.toArray(VALUE_SPLITTER.split(value), String.class)));
    }





    public static String getDefaultValue(String defaultKey) {
        return (String) getKnownValue(defaultKey, DEFAULTS, "Default");
    }





    public static CardDavContactAttributes getAlias(String alias) {
        return (CardDavContactAttributes) getKnownValue(alias, ALIAS_2_CARDDAVCONTACTATTRIBUTE_MAPPING, "Alias");
    }





    private static Object getKnownValue(String key, Map<String, ? extends Object> map, String name) {
        if (!propertiesLoaded) {
            throw new RuntimeException("Properties not yet loaded.");
        }

        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            throw new RuntimeException(name + " for \"" + key + "\" not known.");
        }
    }
}