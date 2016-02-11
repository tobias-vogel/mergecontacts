package com.github.tobias_vogel.mergecontacts.merging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.utils.Utils;
import com.google.common.collect.Sets;

public class MergeCoordinator {

    public static Set<CardDavContact> coordinateMerge(Set<CardDavContact> mainContacts,
            Set<CardDavContact> additionalContacts) {

        Map<String, Set<CardDavContact>> mainContactsIndex = createName2ContactsIndex(mainContacts);
        Map<String, Set<CardDavContact>> additionalContactsIndex = createName2ContactsIndex(additionalContacts);

        Set<List<CardDavContact>> contactGroups = mergeIndexes(mainContactsIndex, additionalContactsIndex);

        Set<CardDavContact> mergedContacts = mergeContactGroups(contactGroups);

        return mergedContacts;
    }





    private static Set<CardDavContact> mergeContactGroups(Set<List<CardDavContact>> contactGroups) {
        // TODO use java8 magic
        Set<CardDavContact> mergedContacts = new HashSet<CardDavContact>(contactGroups.size());

        for (List<CardDavContact> contactGroup : contactGroups) {
            Iterator<CardDavContact> contactIterator = contactGroup.iterator();

            // take the first contact
            CardDavContact mergedContact = contactIterator.next();

            // merge in all further contacts
            while (contactIterator.hasNext()) {
                CardDavContact additionalContact = contactIterator.next();
                mergedContact.mergeInOtherContact(additionalContact);
            }

            mergedContacts.add(mergedContact);
        }

        return mergedContacts;
    }





    private static Set<List<CardDavContact>> mergeIndexes(Map<String, Set<CardDavContact>> mainContactsIndex,
            Map<String, Set<CardDavContact>> additionalContactsIndex) {

        // collect keys
        Collection<String> allKeys = Sets.union(mainContactsIndex.keySet(), additionalContactsIndex.keySet());

        Set<List<CardDavContact>> result = new HashSet<>(allKeys.size());

        for (String key : allKeys) {
            List<CardDavContact> contacts = new ArrayList<>();

            if (mainContactsIndex.containsKey(key)) {
                contacts.addAll(mainContactsIndex.get(key));
            }

            if (additionalContactsIndex.containsKey(key)) {
                contacts.addAll(additionalContactsIndex.get(key));
            }

            result.add(contacts);
        }
        return result;
    }





    private static Map<String, Set<CardDavContact>> createName2ContactsIndex(Set<CardDavContact> contacts) {
        Map<String, Set<CardDavContact>> index = new HashMap<>(contacts.size());

        // TODO use java 8 lambda magic?
        // contacts.parallelStream().map(mapper)stream().map
        for (CardDavContact contact : contacts) {
            String key = contact.generateKey();

            Utils.failsafeMapAppend(index, key, contact);
        }

        return index;
    }
}