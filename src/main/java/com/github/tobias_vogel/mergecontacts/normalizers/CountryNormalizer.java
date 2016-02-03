package com.github.tobias_vogel.mergecontacts.normalizers;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class CountryNormalizer {

    public static void normalize(CardDavContact contact) {
        String country = contact.getAttributeValue(CardDavContactAttributes.COUNTRY);
        if (country == null || country.length() == 0) {
            String defaultCountry = "Deutschland"; // TODO properly assign this
                                                   // default value
            contact.setAttributeValue(CardDavContactAttributes.COUNTRY, defaultCountry);
        }
    }
}
