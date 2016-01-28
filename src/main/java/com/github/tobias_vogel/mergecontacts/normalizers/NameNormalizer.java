package com.github.tobias_vogel.mergecontacts.normalizers;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;

public class NameNormalizer {

    public static void normalize(CardDavContact contact) {
        // TODO Auto-generated method stub

    }
    // def Normalizer.normalizeNames(contact)
    // [contact.givenName, contact.familyName, contact.nickname].each() do
    // |name|
    // if !name.nil?()
    // parts = name.split(" ")
    // name = removeSubstringNames(parts)
    // end
    // end
    // end
    //
    // def Normalizer.removeSubstringNames(names)
    // # iterate over each name
    // # if it is a substring of another, remove it
    // # if another name is a substring of the current name, remove the other
    // name
    //
    // survivingNames = []
    //
    // until names.empty?() do
    // current = names.shift()
    //
    // # remove all names that are substrings of the current one
    // names.delete_if() {|name| isSubname(name, current)}
    //
    // # keep the current unless it is a substring of the remaining names
    // matches = names.collect() {|name| isSubname(current, name)}
    // if !matches.member?(true)
    // survivingNames << current
    // end
    // end
    //
    // return survivingNames
    // end
    // end
}
