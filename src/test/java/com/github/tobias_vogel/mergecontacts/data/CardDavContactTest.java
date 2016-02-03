package com.github.tobias_vogel.mergecontacts.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact.Builder;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class CardDavContactTest {

    @Test
    public void testCardDavContactWithNull() {
        try {
            new CardDavContact(null);
            Assert.fail();
        } catch (RuntimeException e) {
            // expected
        }
    }





    @Test
    public void testCardDavContactWithEmptyMap() {
        CardDavContact contact = new CardDavContact(new HashMap<CardDavContactAttributes, String>());
        Assert.assertNotNull(contact);
    }





    @Test
    public void testCardDavContactWithMapContainingNullKey() {
        Map<CardDavContactAttributes, String> params = new HashMap<>();
        params.put(null, null);
        try {
            new CardDavContact(params);
            Assert.fail();
        } catch (RuntimeException e) {
            // expected
        }
    }





    @Test
    public void testCardDavContactWithSparseMap() {
        Map<CardDavContactAttributes, String> params = new HashMap<>();
        params.put(CardDavContactAttributes.DAY, "23");
        CardDavContact contact = new CardDavContact(params);
        Assert.assertEquals("Deutschland", contact.getAttributeValue(CardDavContactAttributes.COUNTRY));
        Assert.assertEquals("23", contact.getAttributeValue(CardDavContactAttributes.DAY));
    }

    // def testClone()
    // original = CardDavContact.new({:givenName => "Hans"})
    // clone = original.clone()
    // assertEquals("Clone should have the same name.", clone.givenName, "Hans")
    //
    // original.givenName = "Franz"
    // assertEquals("Original should have changed.", original.givenName,
    // "Franz")
    // assertEquals("Clone should have its own objects.", clone.givenName,
    // "Hans")
    // end





    @Test
    public void testBuilder() {
        CardDavContact contact = new CardDavContact.Builder().gname("Harry").fname("Potter").country("Great Britain")
                .day("16").month("10").year("2001").fax("12").wphone("34").hphone("56").mail("hp@hp.com").mphone("78")
                .nname("Potti").notes("With owl").org("Hogwarts").orgUnit("Gryffindor").pager("90").state("Surrey")
                .street("4 Privet Drive").title("His Wizardryness").zip("666").build();
        Assert.assertEquals("Harry", contact.getAttributeValue(CardDavContactAttributes.GIVEN_NAME));
        Assert.assertEquals("Potter", contact.getAttributeValue(CardDavContactAttributes.FAMILY_NAME));
        Assert.assertEquals("Great Britain", contact.getAttributeValue(CardDavContactAttributes.COUNTRY));
        Assert.assertEquals("16", contact.getAttributeValue(CardDavContactAttributes.DAY));
        Assert.assertEquals("10", contact.getAttributeValue(CardDavContactAttributes.MONTH));
        Assert.assertEquals("2001", contact.getAttributeValue(CardDavContactAttributes.YEAR));
        Assert.assertEquals("12", contact.getAttributeValue(CardDavContactAttributes.FAX));
        Assert.assertEquals("34", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
        Assert.assertEquals("56", contact.getAttributeValue(CardDavContactAttributes.HOME_PHONE));
        Assert.assertEquals("hp@hp.com", contact.getAttributeValue(CardDavContactAttributes.MAIL));
        Assert.assertEquals("78", contact.getAttributeValue(CardDavContactAttributes.MOBILE_PHONE));
        Assert.assertEquals("Potti", contact.getAttributeValue(CardDavContactAttributes.NICKNAME));
        Assert.assertEquals("With owl", contact.getAttributeValue(CardDavContactAttributes.NOTES));
        Assert.assertEquals("Hogwarts", contact.getAttributeValue(CardDavContactAttributes.ORGANIZATION));
        Assert.assertEquals("Gryffindor", contact.getAttributeValue(CardDavContactAttributes.ORGANIZATIONAL_UNIT));
        Assert.assertEquals("90", contact.getAttributeValue(CardDavContactAttributes.PAGER));
        Assert.assertEquals("Surrey", contact.getAttributeValue(CardDavContactAttributes.STATE));
        Assert.assertEquals("4 Privet Drive", contact.getAttributeValue(CardDavContactAttributes.STREET));
        Assert.assertEquals("His Wizardryness", contact.getAttributeValue(CardDavContactAttributes.TITLE));
        Assert.assertEquals("666", contact.getAttributeValue(CardDavContactAttributes.ZIP));
    }





    @Test
    public void testBuilderOverwrite() {
        CardDavContact contact = new CardDavContact.Builder().fname("Schneider").fname("Kowalski").build();
        Assert.assertEquals("Kowalski", contact.getAttributeValue(CardDavContactAttributes.FAMILY_NAME));
    }





    @Test
    public void testBuilderWithNull() {
        CardDavContact contact = new CardDavContact.Builder().fname(null).build();
        Assert.assertNull(contact.getAttributeValue(CardDavContactAttributes.FAMILY_NAME));
    }





    @Test
    public void testBuilderEmpty() {
        CardDavContact contact = new CardDavContact.Builder().build();
        Assert.assertNotNull(contact);
    }





    @Test
    public void testBuilderCompleteness() {
        try {
            Builder builder = new CardDavContact.Builder();
            Class<? extends Builder> builderClass = builder.getClass();
            Set<Method> methods = new HashSet<>(Arrays.asList(builder.getClass().getMethods()));
            // methods = methods.stream().filter(m ->
            // m.getReturnType().equals(builderClass)).collect(Collectors.toSet());

            Set<String> someValues = new HashSet<>(methods.size());

            for (Method method : methods) {
                if (method.getReturnType().equals(builderClass)) {
                    // use something that is not changed during any
                    // normalization
                    String randomValue = method.getName();
                    someValues.add(randomValue);
                    Object result = method.invoke(builder, randomValue);
                    Assert.assertEquals(builder, result);
                }
            }
            CardDavContact contact = builder.build();
            for (CardDavContactAttributes attribute : CardDavContactAttributes.values()) {
                Assert.assertTrue("The attribute \"" + attribute.toString() + "\" seems to have not been set.",
                        someValues.remove(contact.getAttributeValue(attribute)));
            }
            Assert.assertTrue(someValues.isEmpty());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail();
        }
    }
}