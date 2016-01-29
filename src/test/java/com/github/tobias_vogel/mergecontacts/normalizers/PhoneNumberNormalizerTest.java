package com.github.tobias_vogel.mergecontacts.normalizers;

import org.junit.Assert;
import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;
import com.google.common.collect.ImmutableMap;

public class PhoneNumberNormalizerTest {

    @Test
    public void testDoNothing() {
        CardDavContact contact = new CardDavContact(
                new ImmutableMap.Builder<CardDavContact.CardDavContactAttributes, String>()
                        .put(CardDavContactAttributes.WORK_PHONE, "001234567").build());
        Assert.assertEquals("001234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testAddCountryCodeToSimpleNumber() {
        CardDavContact contact = new CardDavContact(
                new ImmutableMap.Builder<CardDavContact.CardDavContactAttributes, String>()
                        .put(CardDavContactAttributes.WORK_PHONE, "01234567").build());
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testAddCountryCodeToPlusNumber() {
        CardDavContact contact = new CardDavContact(
                new ImmutableMap.Builder<CardDavContact.CardDavContactAttributes, String>()
                        .put(CardDavContactAttributes.WORK_PHONE, "+491234567").build());
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testDoNotAddCountryCodeToStrangeNumber() {
        CardDavContact contact = new CardDavContact(
                new ImmutableMap.Builder<CardDavContact.CardDavContactAttributes, String>()
                        .put(CardDavContactAttributes.WORK_PHONE, "1234567").build());
        Assert.assertEquals("1234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testRemoveSpecialCharacters() {
        CardDavContact contact = new CardDavContact(
                new ImmutableMap.Builder<CardDavContact.CardDavContactAttributes, String>()
                        .put(CardDavContactAttributes.WORK_PHONE, "(012) 34-56/78").build());
        Assert.assertEquals("004912345678", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testNormalizeAllNumbers() {
        CardDavContact contact = new CardDavContact(
                new ImmutableMap.Builder<CardDavContact.CardDavContactAttributes, String>()
                        .put(CardDavContactAttributes.WORK_PHONE, "01234567")
                        .put(CardDavContactAttributes.HOME_PHONE, "01234567")
                        .put(CardDavContactAttributes.MOBILE_PHONE, "01234567")
                        .put(CardDavContactAttributes.FAX, "01234567").put(CardDavContactAttributes.PAGER, "01234567")
                        .build());

        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.HOME_PHONE));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.MOBILE_PHONE));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.FAX));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.PAGER));
    }
}