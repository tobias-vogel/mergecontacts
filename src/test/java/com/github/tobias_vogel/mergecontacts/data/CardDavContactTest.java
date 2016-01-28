package com.github.tobias_vogel.mergecontacts.data;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

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
    @Ignore
    public void testCardDavContactWithEmptyMap() {
        CardDavContact contact = new CardDavContact(new HashMap<CardDavContactAttributes, String>());
        // TODO actually test it
        // contact = CardDavContact.new()
        // assertEquals("zip code is filled", contact.zip, nil)
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
    @Ignore
    public void testCardDavContactWithSparseMap() {
        Map<CardDavContactAttributes, String> params = new HashMap<>();
        params.put(CardDavContactAttributes.DAY, "23");
        CardDavContact contact = new CardDavContact(params);
        Assert.assertEquals("Deutschland", contact.getAttributeValue(CardDavContactAttributes.COUNTRY));
        Assert.assertEquals("23", contact.getAttributeValue(CardDavContactAttributes.DAY));
    }

    // def testNormalizePhoneNumber()
    // test(
    // "Phone normalization did something when nothing was expected.",
    // CardDavContact.new({:mobilephone => "0049123456"}),
    // CardDavContact.new({:mobilephone => "0049123456", :country =>
    // "Deutschland"})
    // )
    //
    // test(
    // "Prepending international pre-dialing did not work.",
    // CardDavContact.new({:mobilephone => "0123456"}),
    // CardDavContact.new({:mobilephone => "0049123456", :country =>
    // "Deutschland"})
    // )
    //
    // test(
    // "Special characters were not removed.",
    // CardDavContact.new({:mobilephone => "(012) 34-56"}),
    // CardDavContact.new({:mobilephone => "0049123456", :country =>
    // "Deutschland"})
    // )
    //
    // test(
    // "+ in pre-dialing was not removed.",
    // CardDavContact.new({:mobilephone => "+49 123 456"}),
    // CardDavContact.new({:mobilephone => "0049123456", :country =>
    // "Deutschland"})
    // )
    //
    // test(
    // "Not all phone numbers were normalized.",
    // CardDavContact.new({:workphone => "0123", :homephone => "0123", :fax =>
    // "0123", :pager => "0123", :mobilephone => "0123"}),
    // CardDavContact.new({:workphone => "0049123", :homephone => "0049123",
    // :fax => "0049123", :pager => "0049123", :mobilephone => "0049123",
    // :country => "Deutschland"})
    // )
    //
    // test(
    // "Strange phone numbers were touched.",
    // CardDavContact.new({:workphone => "123"}),
    // CardDavContact.new({:workphone => "123", :country => "Deutschland"})
    // )
    // end
    //
    // def testNormalizeEMailAddress()
    // test(
    // "E-mail normalization did not work.",
    // CardDavContact.new({:mail => "Ab.Cd@mail.com"}),
    // CardDavContact.new({:mail => "ab.cd@mail.com", :country =>
    // "Deutschland"})
    // )
    // end
    //
    // def testNormalizeCountry()
    // test(
    // "Country normalization from nil did not work.",
    // CardDavContact.new({:country => nil}),
    // CardDavContact.new({:country => "Deutschland"})
    // )
    //
    // test(
    // "Country normalization from """" did not work.",
    // CardDavContact.new({:country => ""}),
    // CardDavContact.new({:country => "Deutschland"})
    // )
    // end
    //
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
    //
    // private
    // def test(message = "", original, expectedResult)
    // actualResult = Normalizer.normalize(original)
    // assertEquals(message, actualResult, expectedResult)
    //
    // # test idempotence, too
    // repeatedResult = Normalizer.normalize(actualResult)
    // assertEquals(message, repeatedResult, expectedResult)
    // end
    // end
}