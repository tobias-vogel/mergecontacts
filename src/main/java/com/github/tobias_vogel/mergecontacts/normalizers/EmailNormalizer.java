package com.github.tobias_vogel.mergecontacts.normalizers;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class EmailNormalizer {

    public static void normalize(CardDavContact contact) {
        if (contact == null) {
            return;
        }

        if (contact.hasAttribute(CardDavContactAttributes.MAIL)) {
            String mail = contact.getAttributeValue(CardDavContactAttributes.MAIL);
            mail = mail.toLowerCase();
            contact.setAttributeValue(CardDavContactAttributes.MAIL, mail);
        }
    }
}