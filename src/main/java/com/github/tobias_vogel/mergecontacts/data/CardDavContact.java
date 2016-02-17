package com.github.tobias_vogel.mergecontacts.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.tobias_vogel.mergecontacts.exceptions.IllegalAttributeException;
import com.github.tobias_vogel.mergecontacts.merging.Merger;
import com.github.tobias_vogel.mergecontacts.normalizers.Normalizer;

public class CardDavContact implements Cloneable {

    private static final char REPLACEMENT_CHARACTER = '-';

    public enum CardDavContactAttributes {
        GIVEN_NAME, FAMILY_NAME, NICKNAME, MAIL, WORK_PHONE, HOME_PHONE, FAX, PAGER, MOBILE_PHONE, STREET, STATE, ZIP, COUNTRY, TITLE, ORGANIZATIONAL_UNIT, ORGANIZATION, YEAR, MONTH, DAY, NOTES, SPECIAL_ORG_AND_ORG_UNIT, SPECIAL_POSTAL_ADDRESS
    }

    protected static Set<CardDavContactAttributes> ATTRIBUTES_NOT_TO_SET_DIRECTLY = new HashSet<>(Arrays.asList(
            CardDavContactAttributes.SPECIAL_ORG_AND_ORG_UNIT, CardDavContactAttributes.SPECIAL_POSTAL_ADDRESS));

    private Map<CardDavContactAttributes, String> attributes;

    private List<AdditionalData> additionalData;





    private CardDavContact(Map<CardDavContactAttributes, String> providedAttributes,
            List<AdditionalData> providedAdditionalData) {
        if (providedAttributes == null) {
            throw new RuntimeException("The provided attributes map was null.");
        }

        if (providedAttributes.containsKey(null)) {
            throw new RuntimeException("The provided attributes map contained a null key.");
        }

        attributes = new HashMap<>(providedAttributes);

        String pureNotes = AdditionalData.parseNotesAndMergeWithAdditionalDataAndEnrichAdditionalData(
                attributes.get(CardDavContactAttributes.NOTES), providedAdditionalData);

        additionalData = providedAdditionalData;

        attributes.put(CardDavContactAttributes.NOTES, pureNotes);

        normalize();
    }





    public void mergeInOtherContact(CardDavContact otherContact) {
        Merger.enrichContactWithOtherContact(this, otherContact);
        normalize();
    }





    public void normalize() {
        Normalizer.normalize(this);
    }





    public String getAttributeValue(CardDavContactAttributes attributeKey) {
        if (ATTRIBUTES_NOT_TO_SET_DIRECTLY.contains(attributeKey)) {
            throw new IllegalAttributeException(attributeKey + " must not be requested directly.");
        }

        if (attributeKey == CardDavContactAttributes.NOTES) {
            return AdditionalData.generateNotesFieldContent(additionalData,
                    attributes.get(CardDavContactAttributes.NOTES));
        }

        return attributes.get(attributeKey);
    }





    public void setAttributeValue(CardDavContactAttributes attributeName, String attributeValue) {
        if (ATTRIBUTES_NOT_TO_SET_DIRECTLY.contains(attributeName)) {
            throw new IllegalAttributeException(attributeName + " must not be set directly.");
        }
        attributes.put(attributeName, attributeValue);
    }





    public boolean hasAttribute(CardDavContactAttributes attribute) {
        if (ATTRIBUTES_NOT_TO_SET_DIRECTLY.contains(attribute)) {
            throw new IllegalAttributeException(attribute + " must not be queried directly.");
        }
        return attributes.containsKey(attribute);
    }





    @Deprecated
    public CardDavContact clone() {
        // TODO remove this method? (and the clone interface implementation?)
        return null;
    }





    /**
     * Calculates a key for a given string value. The key is the first two
     * characters. If the provided string is shorter or null, replacement
     * characters are used.
     * 
     * @param attributeValue
     *            the string to generate the key for
     * @return the key for the provided string
     */
    private String generateKeyPart(String attributeValue) {
        String key;
        if (attributeValue == null) {
            key = "";
        } else {
            key = attributeValue;
        }

        attributeValue += REPLACEMENT_CHARACTER;
        attributeValue += REPLACEMENT_CHARACTER;

        key = key.substring(0, 2);

        return key;
    }





    public String generateKey() {
        // We need a simple key that does not create false positives and false
        // negatives. Thus, the key may neither be too relaxed (only country[0])
        // nor too strict (all attributes). We assume the data to be relatively
        // clean and to contain mainly person data.
        // Therefore, we take the first two characters from the given, family
        // name and the city, hoping that all attributes are usually set
        // correctly and that this is unique enough.

        String key = "";
        key += generateKeyPart(getAttributeValue(CardDavContactAttributes.GIVEN_NAME));
        key += generateKeyPart(getAttributeValue(CardDavContactAttributes.FAMILY_NAME));
        // TODO add city?
        return key;
    }





    /**
     * returns a copy of all alternative data items for the specified attribute
     * 
     * @param attribute
     *            the {@link CardDavContactAttributes} to return
     * @return a copy of the values of alternative data for the specified
     *         attribute
     */
    public List<String> getAlternativeData(CardDavContactAttributes attribute) {
        List<String> result = AdditionalData.getFilteredCopyOfAdditionalData(attribute, AlternativeData.class,
                additionalData);
        return result;
    }





    /**
     * returns a copy of all old data items for the specified attribute
     * 
     * @param attribute
     *            the {@link CardDavContactAttributes} to return
     * @return a copy of the values of old data for the specified attribute
     */
    public List<String> getOldData(CardDavContactAttributes attribute) {
        List<String> result = AdditionalData.getFilteredCopyOfAdditionalData(attribute, OldData.class, additionalData);
        return result;
    }





    public void updateAdditionalData(/*
                                      * Function filter, Function updateFunction
                                      */) {
        // TODO use java 8 magic to implement this
        // this updates the attributes in place, no need to entirely remove and
        // replace them
        // and this keeps the attributes in order
    }

    // /**
    // * Deletes the existing old data values for the specified attribute and
    // * replaces them with the new values.
    // *
    // * @param attribute
    // * the attribute to replace all values for
    // * @param replacementValues
    // * the list of values to use instead of the old values
    // */
    // public void replaceOldData(CardDavContactAttributes attribute,
    // List<String> replacementValues) {
    // // use java 8 magic here (filter and then replace)
    // additionalData
    // }

    public static class Builder {
        private Map<CardDavContactAttributes, String> params = new HashMap<>(CardDavContactAttributes.values().length);
        private List<AdditionalData> additionalData = new ArrayList<>();





        public CardDavContact build() {
            return new CardDavContact(params, additionalData);
        }





        public Builder gname(String newGivenName) {
            params.put(CardDavContactAttributes.GIVEN_NAME, newGivenName);
            return this;
        }





        public Builder fname(String newFamilyName) {
            params.put(CardDavContactAttributes.FAMILY_NAME, newFamilyName);
            return this;
        }





        public Builder nname(String newNickname) {
            params.put(CardDavContactAttributes.NICKNAME, newNickname);
            return this;
        }





        public Builder mail(String newMail) {
            params.put(CardDavContactAttributes.MAIL, newMail);
            return this;
        }





        public Builder wphone(String newWorkPhone) {
            params.put(CardDavContactAttributes.WORK_PHONE, newWorkPhone);
            return this;
        }





        public Builder hphone(String newHomePhone) {
            params.put(CardDavContactAttributes.HOME_PHONE, newHomePhone);
            return this;
        }





        public Builder fax(String newFax) {
            params.put(CardDavContactAttributes.FAX, newFax);
            return this;
        }





        public Builder pager(String newPager) {
            params.put(CardDavContactAttributes.PAGER, newPager);
            return this;
        }





        public Builder mphone(String newMobilePhone) {
            params.put(CardDavContactAttributes.MOBILE_PHONE, newMobilePhone);
            return this;
        }





        public Builder street(String newStreet) {
            params.put(CardDavContactAttributes.STREET, newStreet);
            return this;
        }





        public Builder state(String newState) {
            params.put(CardDavContactAttributes.STATE, newState);
            return this;
        }





        public Builder zip(String newZipCode) {
            params.put(CardDavContactAttributes.ZIP, newZipCode);
            return this;
        }





        public Builder country(String newCountry) {
            params.put(CardDavContactAttributes.COUNTRY, newCountry);
            return this;
        }





        public Builder title(String newTitle) {
            params.put(CardDavContactAttributes.TITLE, newTitle);
            return this;
        }





        public Builder orgUnit(String newOrganizationalUnit) {
            params.put(CardDavContactAttributes.ORGANIZATIONAL_UNIT, newOrganizationalUnit);
            return this;
        }





        public Builder org(String newOrganization) {
            params.put(CardDavContactAttributes.ORGANIZATION, newOrganization);
            return this;
        }





        public Builder year(String newYear) {
            params.put(CardDavContactAttributes.YEAR, newYear);
            return this;
        }





        public Builder month(String newMonth) {
            params.put(CardDavContactAttributes.MONTH, newMonth);
            return this;
        }





        public Builder day(String newDay) {
            params.put(CardDavContactAttributes.DAY, newDay);
            return this;
        }





        public Builder notes(String newNotes) {
            params.put(CardDavContactAttributes.NOTES, newNotes);
            return this;
        }





        public Builder addAlternativeData(CardDavContactAttributes attribute, String value) {
            additionalData.add(new AlternativeData(attribute, value));
            return this;
        }





        public Builder addOldData(CardDavContactAttributes attribute, String value) {
            additionalData.add(new OldData(attribute, value));
            return this;
        }
    }

}