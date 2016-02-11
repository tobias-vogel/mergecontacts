package com.github.tobias_vogel.mergecontacts.data;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class AlternativeData extends AdditionalData {

    protected final static String IDENTIFIER = "alternative";





    public AlternativeData(CardDavContactAttributes attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }
}
