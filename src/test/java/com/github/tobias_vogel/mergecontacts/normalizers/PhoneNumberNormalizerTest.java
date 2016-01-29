package com.github.tobias_vogel.mergecontacts.normalizers;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class PhoneNumberNormalizerTest {

    @Test
    public void testDoNothing() {
        Map<CardDavContactAttributes, String> params = new HashMap<>();
        params.put(CardDavContactAttributes.WORK_PHONE, "001234567");
        CardDavContact contact = new CardDavContact(params);
        Assert.assertEquals("001234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testAddCountryCodeToSimpleNumber() {
        Map<CardDavContactAttributes, String> params = new HashMap<>();
        params.put(CardDavContactAttributes.WORK_PHONE, "01234567");
        CardDavContact contact = new CardDavContact(params);
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testAddCountryCodeToPlusNumber() {
        Map<CardDavContactAttributes, String> params = new HashMap<>();
        params.put(CardDavContactAttributes.WORK_PHONE, "+491234567");
        CardDavContact contact = new CardDavContact(params);
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testDoNotAddCountryCodeToStrangeNumber() {
        Map<CardDavContactAttributes, String> params = new HashMap<>();
        params.put(CardDavContactAttributes.WORK_PHONE, "1234567");
        CardDavContact contact = new CardDavContact(params);
        Assert.assertEquals("1234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testRemoveSpecialCharacters() {
        Map<CardDavContactAttributes, String> params = new HashMap<>();
        params.put(CardDavContactAttributes.WORK_PHONE, "(012) 34-56/78");
        CardDavContact contact = new CardDavContact(params);
        Assert.assertEquals("004912345678", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testNormalizeAllNumbers() {
        Map<CardDavContactAttributes, String> params = new HashMap<>();
        params.put(CardDavContactAttributes.WORK_PHONE, "01234567");
        params.put(CardDavContactAttributes.HOME_PHONE, "01234567");
        params.put(CardDavContactAttributes.MOBILE_PHONE, "01234567");
        params.put(CardDavContactAttributes.FAX, "01234567");
        params.put(CardDavContactAttributes.PAGER, "01234567");
        CardDavContact contact = new CardDavContact(params);
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.HOME_PHONE));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.MOBILE_PHONE));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.FAX));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.PAGER));
    }
}