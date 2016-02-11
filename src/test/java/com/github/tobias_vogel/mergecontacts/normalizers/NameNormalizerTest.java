package com.github.tobias_vogel.mergecontacts.normalizers;

import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

import junit.framework.Assert;

public class NameNormalizerTest {

    @Test
    public void testSubstringRemoval() {
        CardDavContact contact = new CardDavContact.Builder()
                .gname("Johann Gregor Johann Fritz Jo Greg Johannes Johann James").build();
        Assert.assertEquals("Gregor Fritz Johannes James",
                contact.getAttributeValue(CardDavContactAttributes.GIVEN_NAME));
    }
}