package com.github.tobias_vogel.mergecontacts.data;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class OldData extends AdditionalData {

    protected static final String IDENTIFIER = "old";





    public OldData(CardDavContactAttributes attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }





    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }
}