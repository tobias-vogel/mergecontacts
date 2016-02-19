package com.github.tobias_vogel.mergecontacts.normalizers;

import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

import junit.framework.Assert;

public class CountryNormalizerTest {

    @Test
    public void testDoNothing() {
        CardDavContact contact = new CardDavContact.Builder().country("Iceland").build();
        Assert.assertEquals("Iceland", contact.getAttributeValue(CardDavContactAttributes.COUNTRY));
    }





    @Test
    public void testAddDefaultCountry() {
        CardDavContact contact = new CardDavContact.Builder().build();
        Assert.assertEquals("Deutschland", contact.getAttributeValue(CardDavContactAttributes.COUNTRY));
    }
}