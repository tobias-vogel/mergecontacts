package com.github.tobias_vogel.mergecontacts.data;

import java.util.HashMap;
import java.util.Map;

import com.github.tobias_vogel.mergecontacts.normalizers.Normalizer;

public class CardDavContact implements Cloneable {

    public enum CardDavContactAttributes {
        GIVEN_NAME, FAMILY_NAME, NICKNAME, MAIL, WORK_PHONE, HOME_PHONE, FAX, PAGER, MOBILE_PHONE, STREET, STATE, ZIP, COUNTRY, TITLE, ORGANIZATIONAL_UNIT, ORGANIZATION, YEAR, MONTH, DAY, NOTES
    }

    private Map<CardDavContactAttributes, String> attributes;

    private static final char REPLACEMENT_CHARACTER = '-';





    public CardDavContact(Map<CardDavContactAttributes, String> contactAttributes) {
        if (contactAttributes == null) {
            throw new RuntimeException("The provided attributes map was null.");
        }

        if (contactAttributes.containsKey(null)) {
            throw new RuntimeException("The provided attributes map contained a null key.");
        }

        attributes = new HashMap<>(contactAttributes);

        normalize();
    }

    // def clone()
    // clonedAttributes = {}
    // @@attributes.each do |attribute|
    // clonedAttributes[attribute] = instance_variable_get("@#{attribute}")
    // end
    //
    // return CardDavContact.new(clonedAttributes)
    // end

    // private boolean allAttributesAreSet() {
    // return attributes.size() == ATTRIBUTES.size();
    // }

    // def eql?(other)@
    //
    //
    //
    //
    //
    // @attributes.each() do |attribute|
    // thisValue = instance_variable_get("@#{attribute}")
    // otherValue = other.instance_variable_get("@#{attribute}")
    // return false unless thisValue.eql?(otherValue)
    // end
    // return true
    // end





    public void mergeInOtherContact(CardDavContact otherContact) {
        // mergeNames(other);
        // mergeEMails(other);
        // #todo implement for other attributes
        // # go over all attributes, but respect that phone numbers can be mixed
        // around several fields
        // # @@attributes.each() do |attribute| ...

        // TODO do this in another class

        // private
        // def mergeNamesSmart(main, other)
        // # tokenize both names by blanks
        // # do a nested loop and delete all names that have longer counterparts
        // in the other contact
        // # join both lists back to a string
        //
        // # tokenize both names
        // mainNames = other.to_s().split(" ")
        // otherNames = other.to_s().split(" ")
        //
        // # merge all names
        // names = mainNames + otherNames
        //
        // # remove exact duplicates
        // names.uniq!()
        //
        // # remove all short names that exist in a
        //
        //
        //
        //
        //
        // longer form (that is, are part of another name)
        // names = removeSubstringNames(names)
        //
        // # append all remaining parts
        // main = names.join(" ")
        //
        // return main
        // end
        //

    }





    public void normalize() {
        Normalizer.normalize(this);
    }





    public String getAttributeValue(CardDavContactAttributes attributeKey) {
        return attributes.get(attributeKey);
    }





    public void setAttributeValue(CardDavContactAttributes attributeName, String attributeValue) {
        attributes.put(attributeName, attributeValue);
    }





    public boolean hasAttribute(CardDavContactAttributes numberAttribute) {
        return attributes.containsKey(numberAttribute);
    }

    // def mergeNames(other)
    // puts @givenName
    // puts other.givenName
    // if @givenName.nil?()
    // @givenName = other.givenName
    // elsif @givenName[other.givenName].nil?()
    // @givenName << " " + other.givenName
    // end
    //
    // @givenName = mergeNamesSmart(@givenName, other.givenName)
    // # das soll er machen:
    // # die namen des zweiten
    // tokenisieren (an leerzeichen) und die neuen teile an den hauptnamen
    // anhängen
    //
    // # if @familyName.nil?()
    // # @familyName = other.familyName
    // # elsif @familyName[other.familyName].nil?()
    // # @givenName << " " + other.givenName
    // # end
    //
    //
    // end
    //





    //
    //
    // def mergeEMails(other)
    // # todo
    // end

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

    static class Builder {
        private Map<CardDavContactAttributes, String> params = new HashMap<>(CardDavContactAttributes.values().length);

        //
        // public Builder() {
        // }





        public CardDavContact build() {
            return new CardDavContact(params);
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
    }
}