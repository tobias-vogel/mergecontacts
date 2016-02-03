package com.github.tobias_vogel.mergecontacts.normalizers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;
import com.google.common.base.Joiner;

public class NameNormalizer {

    public static void normalize(CardDavContact contact) {
        if (contact == null) {
            return;
        }

        Collection<CardDavContactAttributes> nameFields = new HashSet<>(
                Arrays.asList(CardDavContactAttributes.GIVEN_NAME, CardDavContactAttributes.FAMILY_NAME,
                        CardDavContactAttributes.NICKNAME));

        for (CardDavContactAttributes nameField : nameFields) {
            if (contact.hasAttribute(nameField)) {
                String name = contact.getAttributeValue(nameField);
                String normalizedName = normalizeName(name);
                contact.setAttributeValue(nameField, normalizedName);
            }
        }
    }





    private static String normalizeName(String name) {
        if (name == null) {
            return name;
        }
        List<String> names = tokenizeName(name);
        discardSubstringNames(names);
        String normalizedName = joinTokens(names);
        return normalizedName;
    }





    private static String joinTokens(List<String> tokens) {
        return Joiner.on(' ').join(tokens);
    }





    /**
     * This method iterates through the names and removes names that are
     * prefixes of other names. The order is preserved.
     * 
     * @param names
     *            a list of names
     */
    private static void discardSubstringNames(List<String> names) {
        List<String> survivingNames = new ArrayList<>();

        do {
            String currentName = names.remove(0);

            if (currentNameIsRealPrefixOfAnyOtherName(currentName, names)) {
                // ignore this name, don't move it to the surviving names
                continue;
            }

            removeNamesThatArePrefixesOrEqualToCurrentName(currentName, names);

            survivingNames.add(currentName);
        } while (!names.isEmpty());

        names.clear();
        names.addAll(survivingNames);
    }





    private static void removeNamesThatArePrefixesOrEqualToCurrentName(String currentName, List<String> names) {
        for (int index = names.size() - 1; index >= 0; index--) {
            String prefixCandidate = names.get(index);
            if (currentName.startsWith(prefixCandidate)) {
                names.remove(index);
            }
        }
    }





    private static boolean currentNameIsRealPrefixOfAnyOtherName(String currentName, List<String> otherNames) {
        for (String otherName : otherNames) {
            if (otherName.equals(currentName)) {
                continue;
            }

            if (otherName.startsWith(currentName)) {
                return true;
            }
        }
        return false;
    }





    private static List<String> tokenizeName(String name) {
        List<String> tokens = new ArrayList<>(Arrays.asList(name.split(" ")));
        return tokens;
    }
}