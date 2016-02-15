package com.github.tobias_vogel.mergecontacts.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact.Builder;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class CardDavContactTest {

    // @Test
    // public void testCardDavContactWithNull() {
    // try {
    // new CardDavContact(null, null);
    // Assert.fail();
    // } catch (RuntimeException e) {
    // // expected
    // }
    //
    // try {
    // new CardDavContact(null, Collections.emptyList());
    // Assert.fail();
    // } catch (RuntimeException e) {
    // // expected
    // }
    // }

    // @Test
    // public void testCardDavContactWithEmptyMap() {
    // CardDavContact contact = new CardDavContact(Collections.emptyMap(),
    // Collections.emptyList());
    // Assert.assertNotNull(contact);
    // }

    // @Test
    // public void testCardDavContactWithMapContainingNullKey() {
    // Map<CardDavContactAttributes, String> params = new HashMap<>();
    // params.put(null, null);
    // try {
    // new CardDavContact(params, Collections.emptyList());
    // Assert.fail();
    // } catch (RuntimeException e) {
    // // expected
    // }
    // }

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
    public void testBuilderSparseAndSimple() {
        CardDavContact contact = new CardDavContact.Builder().gname("Michael").fname("Jackson").country("USA")
                .wphone("0012 345-6").build();
        Assert.assertEquals("Michael", contact.getAttributeValue(CardDavContactAttributes.GIVEN_NAME));
        Assert.assertEquals("Jackson", contact.getAttributeValue(CardDavContactAttributes.FAMILY_NAME));
        Assert.assertEquals("USA", contact.getAttributeValue(CardDavContactAttributes.COUNTRY));
        Assert.assertEquals("00123456", contact.getAttributeValue(CardDavContactAttributes.WORK_PHONE));
        Assert.assertFalse(contact.hasAttribute(CardDavContactAttributes.PAGER));
        Assert.assertNull(contact.getAttributeValue(CardDavContactAttributes.PAGER));
    }





    @Test
    public void testBuilderWithAllAttributes() {
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
    public void testBuilderWithAdditionalData() {
        CardDavContact contact = new CardDavContact.Builder().fname("Smith")
                .addOldData(CardDavContactAttributes.FAMILY_NAME, "Lennon").notes("bla bla")
                .addAlternativeData(CardDavContactAttributes.MAIL, "ABC@def.Com")
                .notes("Something completely different").build();

        Assert.assertEquals("Smith", contact.getAttributeValue(CardDavContactAttributes.FAMILY_NAME));
        Assert.assertEquals("old-familyname=Lennon<|>Something completely different<|>alternative-mail=abc@def.com",
                contact.getAttributeValue(CardDavContactAttributes.NOTES));
    }





    @Test
    public void testBuilderOverwrite() {
        CardDavContact contact = new CardDavContact.Builder().fname("Schneider").fname("Kowalski").build();
        Assert.assertEquals("Kowalski", contact.getAttributeValue(CardDavContactAttributes.FAMILY_NAME));
    }





    @Test
    public void testBuilderWithComplicatedSerializedNotesInput() {
        CardDavContact contact = new CardDavContact.Builder()
                .notes("school friend, children: hans and franz<|>alternative-mail=james@mail.com<|>alternative-mail=james@mail.org<|>old-mail=james@hotmail.com<|>old-mail=james@yahoo.com<|>alternative-familyname=Smith<|>favorite color is red<|>old-SPECIAL_ORG_AND_ORG_UNIT=Sales, ACME corporation<|><|>some final remark")
                .build();

        Assert.assertEquals("school friend, children: hans and franz, favorite color is red, some final remark",
                contact.getAttributeValue(CardDavContactAttributes.NOTES));
        Assert.assertEquals(Arrays.asList("james@mail.com", "james@mail.org"),
                contact.getAlternativeData(CardDavContactAttributes.MAIL));
        Assert.assertEquals(Arrays.asList("james@hotmail.com", "james@yahoo.org"),
                contact.getAlternativeData(CardDavContactAttributes.MAIL));
        Assert.assertEquals(Arrays.asList("Smith"), contact.getAlternativeData(CardDavContactAttributes.FAMILY_NAME));
        Assert.assertEquals(Arrays.asList("Sales, ACME corporation"),
                contact.getAlternativeData(CardDavContactAttributes.SPECIAL_ORG_AND_ORG_UNIT));
    }





    @Test
    public void testBuilderWithMixedNotesAndDirectAdditionalDataInput() {
        CardDavContact contact = new CardDavContact.Builder()
                .notes("some note<|>alternative-year=2001<|>year=2002<|>day=10<|>old-day=11").year("1999").day("12")
                .addAlternativeData(CardDavContactAttributes.YEAR, "2000")
                .addOldData(CardDavContactAttributes.DAY, "13").build();

        Assert.assertEquals("some note, year=2002, day=10", contact.getAttributeValue(CardDavContactAttributes.NOTES));
        Assert.assertEquals(Arrays.asList("2001", "2000"), contact.getAlternativeData(CardDavContactAttributes.YEAR));
        Assert.assertEquals("1999", contact.getAttributeValue(CardDavContactAttributes.YEAR));
        Assert.assertEquals(Arrays.asList("11", "13"), contact.getOldData(CardDavContactAttributes.DAY));
        Assert.assertEquals("12", contact.getAttributeValue(CardDavContactAttributes.DAY));
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
            CardDavContactAttributes attributeForAdditionalData = CardDavContactAttributes.PAGER;
            Builder builder = new CardDavContact.Builder();
            Class<? extends Builder> builderClass = builder.getClass();
            Set<Method> methods = new HashSet<>(Arrays.asList(builder.getClass().getMethods()));
            // todo use java8 magic?
            // methods = methods.stream().filter(m ->
            // m.getReturnType().equals(builderClass)).collect(Collectors.toSet());

            Set<String> someValues = new HashSet<>(methods.size());

            for (Method method : methods) {
                if (method.getReturnType().equals(builderClass)) {
                    // use something individual that is not changed during any
                    // normalization
                    String randomValue = method.getName();
                    someValues.add(randomValue);

                    Object invocationResult;
                    switch (method.getParameterCount()) {
                    case 1:
                        invocationResult = method.invoke(builder, randomValue);
                        break;
                    case 2:
                        invocationResult = method.invoke(builder, attributeForAdditionalData, randomValue);
                        break;

                    default:
                        continue;
                    }
                    Assert.assertEquals(builder, invocationResult);
                }
            }
            CardDavContact contact = builder.build();
            for (CardDavContactAttributes attribute : CardDavContactAttributes.values()) {
                Assert.assertTrue("The attribute \"" + attribute.toString() + "\" seems to have not been set.",
                        someValues.remove(contact.getAttributeValue(attribute)));
            }

            Assert.assertTrue(
                    "The alternative attribute \"" + attributeForAdditionalData.toString()
                            + "\" seems to have not been set.",
                    someValues.remove(contact.getAlternativeData(attributeForAdditionalData)));
            Assert.assertTrue(
                    "The old attribute \"" + attributeForAdditionalData.toString() + "\" seems to have not been set.",
                    someValues.remove(contact.getOldData(attributeForAdditionalData)));

            Assert.assertTrue(someValues.isEmpty());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Assert.fail();
        }
    }
}