package com.github.tobias_vogel.mergecontacts.normalizers;

import org.junit.Assert;
import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class PhoneNumberNormalizerTest {

    @Test
    public void testDoNothing() {
        CardDavContact contact = new CardDavContact.Builder().wphone("001234567").build();
        Assert.assertEquals("001234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testWithNull() {
        CardDavContact contact = null;
        PhoneNumberNormalizer.normalize(contact);
        Assert.assertNull(contact);
    }





    @Test
    public void testWithNullNumber() {
        CardDavContact contact = new CardDavContact.Builder().wphone(null).build();
        Assert.assertNull(contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testAddCountryCodeToSimpleNumber() {
        CardDavContact contact = new CardDavContact.Builder().wphone("01234567").build();
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testAddCountryCodeToPlusNumber() {
        CardDavContact contact = new CardDavContact.Builder().wphone("+491234567").build();
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testDoNotAddCountryCodeToStrangeNumber() {
        CardDavContact contact = new CardDavContact.Builder().wphone("1234567").build();
        Assert.assertEquals("1234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testRemoveSpecialCharacters() {
        CardDavContact contact = new CardDavContact.Builder().wphone("(012) 34-56/78").build();
        Assert.assertEquals("004912345678", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
    }





    @Test
    public void testNormalizeAllNumbers() {
        CardDavContact contact = new CardDavContact.Builder().wphone("01234567").hphone("01234567").mphone("01234567")
                .fax("01234567").pager("01234567").build();

        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.HOME_PHONE));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.MOBILE_PHONE));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.FAX));
        Assert.assertEquals("00491234567", contact.getAttributeValue(CardDavContactAttributes.PAGER));
    }
}