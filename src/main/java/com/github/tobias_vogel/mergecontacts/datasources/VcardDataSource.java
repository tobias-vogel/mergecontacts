package com.github.tobias_vogel.mergecontacts.datasources;

import java.util.Set;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;

public class VcardDataSource extends DataSource {

    @Override
    public String getPrefix() {
        return "VCARD";
    }





    public VcardDataSource() {
        // required default constructor for getPrefix() method
    }





    @Override
    public Set<CardDavContact> readContacts(String filename) {
        // TODO Auto-generated method stub
        return null;
    }

}