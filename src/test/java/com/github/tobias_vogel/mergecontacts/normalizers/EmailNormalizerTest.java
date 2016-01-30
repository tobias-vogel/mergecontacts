package com.github.tobias_vogel.mergecontacts.normalizers;

import org.junit.Assert;
import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;
import com.google.common.collect.ImmutableMap;

public class EmailNormalizerTest {

    @Test
    public void testDoNothing() {
        CardDavContact contact = new CardDavContact(
                new ImmutableMap.Builder<CardDavContact.CardDavContactAttributes, String>()
                        .put(CardDavContactAttributes.MAIL, "abc@xyz.com").build());
        Assert.assertEquals("abc@xyz.com", contact.getAttributeValue(CardDavContactAttributes.MAIL));
    }





    @Test
    public void testNormalizeMail() {
        CardDavContact contact = new CardDavContact(
                new ImmutableMap.Builder<CardDavContact.CardDavContactAttributes, String>()
                        .put(CardDavContactAttributes.MAIL, "Abc@Xyz.com").build());
        Assert.assertEquals("abc@xyz.com", contact.getAttributeValue(CardDavContactAttributes.MAIL));
    }
}