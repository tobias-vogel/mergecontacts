package com.github.tobias_vogel.mergecontacts.data;

import java.util.HashMap;
import java.util.Map;

class CardDavContact {

    enum CardDavContactAttributes {
        GIVEN_NAME, FAMILY_NAME, NICKNAME, MAIL, WORK_PHONE, HOME_PHONE, FAX, PAGER, MOBILE_PHONE, STREET, STATE, ZIP, COUNTRY, TITLE, ORGANIZATIONAL_UNIT, ORGANIZATION, YEAR, MONTH, DAY, NOTES
    }

    // public static Set<String> ATTRIBUTES = new
    // HashSet<>(Arrays.asList("givenName", "familyName", "nickname", "mail",
    // "workphone", "homephone", "fax", "pager", "mobilephone", "street",
    // "state", "zip", "country", "title",
    // "organizationalUnit", "organization", "year", "month", "day", "notes"));

    // TODO transform attributes into separate static final fields that are
    // visible to the outside world
    // then add a method that uses reflection to verify that a provided
    // attribute key is acutually among these attributes

    // maybe create an (internally, public) enum that contains these fields
    // (without values)

    private Map<CardDavContactAttributes, String> attributes;





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
        // Normalizer.normalize(this);
    }





    public String getAttributeValue(CardDavContactAttributes attributeKey) {
        return attributes.get(attributeKey);
    }

    // TODO belongs into normalizer
    // def removeSubstringNames(names)
    // # iterate over each name
    // # if it is a substring of another, remove it
    // # if another name is a substring of the current name, remove the other
    // name
    //
    // survivingNames = []
    //
    // until names.empty?() do
    // current = names.shift()
    //
    // # remove all names that are substrings of the current one
    // names.
    //
    //
    //
    //
    //
    // delete_if() {|name| isSubname(name, current)}
    //
    // #
    //
    // keep the
    // current unless
    // it is
    // a substring
    // of the
    // remaining names matches=names.
    //
    //
    // collect() {|name| isSubname(current,
    // name)}if!matches.member?(true)survivingNames<<
    // current
    // end end

    // return
    // survivingNames
    // end

    // def isSubname(subname, name)
    // match = name[subname]
    // return match.nil?()
    // end
    //

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
}