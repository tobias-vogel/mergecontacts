package com.github.tobias_vogel.mergecontacts.normalizers;

import org.junit.Assert;
import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class EmailNormalizerTest {

    @Test
    public void testDoNothing() {
        CardDavContact contact = new CardDavContact.Builder().mail("abc@xyz.com").build();
        Assert.assertEquals("abc@xyz.com", contact.getAttributeValue(CardDavContactAttributes.MAIL));
    }





    @Test
    public void testNormalizeMail() {
        CardDavContact contact = new CardDavContact.Builder().mail("Abc@Xyz.com").build();
        Assert.assertEquals("abc@xyz.com", contact.getAttributeValue(CardDavContactAttributes.MAIL));
    }
}