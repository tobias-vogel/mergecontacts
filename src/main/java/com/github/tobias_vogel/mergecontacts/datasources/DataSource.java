package com.github.tobias_vogel.mergecontacts.datasources;

import java.util.Set;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;

public abstract class DataSource {

    protected String filename;





    public abstract String getPrefix();





    public abstract Set<CardDavContact> readContacts(String filename);

    // def normalizeContacts()
    // #TODO invoke a chain of normalizers (always on the whole contact)
    // @contacts.each do |contact|
    // Normalizer.normalize(contact)
    // end
    // end
    //
    // def mergeIn(otherSource)
    // # todo
    // end
    // end
}