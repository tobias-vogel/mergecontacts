package com.github.tobias_vogel.mergecontacts.normalizers;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

import junit.framework.Assert;

public class NameNormalizerTest {

    @Test
    public void testSubstringRemoval() {
        Map<CardDavContactAttributes, String> params = new HashMap<>();
        params.put(CardDavContactAttributes.GIVEN_NAME, "Johann Gregor Johann Fritz Jo Greg Johannes Johann James");
        CardDavContact contact = new CardDavContact(params);
        Assert.assertEquals("Gregor Fritz Johannes James",
                contact.getAttributeValue(CardDavContactAttributes.GIVEN_NAME));
    }
}