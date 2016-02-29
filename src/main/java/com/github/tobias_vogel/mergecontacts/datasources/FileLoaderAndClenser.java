package com.github.tobias_vogel.mergecontacts.datasources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.utils.Utils;

public class FileLoaderAndClenser {

    private static final Map<String, Class<? extends DataSource>> PREFIX_2_DATA_SOURCE_LOOKUP;





    static {
        try {
            PREFIX_2_DATA_SOURCE_LOOKUP = new HashMap<>();
            Reflections reflections = new Reflections();
            Set<Class<? extends DataSource>> dataSourceClasses = reflections.getSubTypesOf(DataSource.class);

            // use java8 magic for filtering abstract classes out
            for (Class<? extends DataSource> dataSourceClass : dataSourceClasses) {
                if (!Modifier.isAbstract(dataSourceClass.getModifiers())) {
                    String prefix = dataSourceClass.newInstance().getPrefix();
                    Utils.appendMapOrDieIfKeyExists(PREFIX_2_DATA_SOURCE_LOOKUP, prefix, dataSourceClass);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Something went wrong during loading the known datasource classes.");
        }
    }





    private FileLoaderAndClenser() {
        // avoid construction
    }





    public static Set<CardDavContact> loadFileAndClenseContacts(String fileSpecifier) {
        StringBuffer prefix = new StringBuffer();
        StringBuffer filename = new StringBuffer();

        splitFileSpecifierIntoFiletypeAndFilename(fileSpecifier, prefix, filename);

        Class<? extends DataSource> dataSourceType = detectDataSourceType(prefix.toString());

        DataSource dataSource = instantiateDatasource(dataSourceType, filename.toString());

        Set<CardDavContact> contacts = dataSource.readContacts(filename.toString());

        return contacts;
    }





    private static DataSource instantiateDatasource(Class<? extends DataSource> dataSourceType, String filename) {
        try {
            return dataSourceType.getConstructor(String.class).newInstance(filename);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Could not instantiate \"" + dataSourceType.getSimpleName() + "\".");
        }

    }





    private static void splitFileSpecifierIntoFiletypeAndFilename(String fileSpecifier, StringBuffer prefix,
            StringBuffer filename) {
        String[] parts = fileSpecifier.split(":");
        prefix.append(parts[0]);
        filename.append(parts[1]);
    }





    private static Class<? extends DataSource> detectDataSourceType(String prefix) {
        if (!PREFIX_2_DATA_SOURCE_LOOKUP.containsKey(prefix)) {
            throw new RuntimeException("Unknown prefix \"" + prefix + "\".");
        }
        return PREFIX_2_DATA_SOURCE_LOOKUP.get(prefix);
    }

}