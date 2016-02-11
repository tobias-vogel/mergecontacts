package com.github.tobias_vogel.mergecontacts.merging;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class Merger {

    private static void mergeThroughReplacement(CardDavContactAttributes familyName, CardDavContact otherContact) {
        // TODO Auto-generated method stub

    }





    private static void mergeThroughAppending(CardDavContactAttributes attribute, CardDavContact otherContact) {

        // TODO merge through appending
    }





    private static boolean mergeAttributeSimpleIfPossible(CardDavContactAttributes attribute,
            CardDavContact otherContact) {
        // TODO Auto-generated method stub
        return false;
    }





    public static void enrichContactWithOtherContact(CardDavContact contact, CardDavContact otherContact) {
        enrichNames(contact, otherContact);
        enrichEMails(contact, otherContact);
        enrichPhoneNumbers(contact, otherContact);
        // TODO and so on...
    }





    private static void enrichPhoneNumbers(CardDavContact contact, CardDavContact otherContact) {
        // TODO Auto-generated method stub
        // respect that phone numbers can be mixed
        // around several fields
    }





    private static void enrichEMails(CardDavContact contact, CardDavContact otherContact) {
        // TODO Auto-generated method stub

    }





    private static void enrichNames(CardDavContact contact, CardDavContact otherContact) {
        mergeThroughAppending(contact, otherContact, CardDavContactAttributes.GIVEN_NAME);
        mergeThroughAppending(contact, otherContact, CardDavContactAttributes.NICKNAME);
        mergeThroughReplacement(contact, otherContact, CardDavContactAttributes.FAMILY_NAME);

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

    }





    private static void mergeThroughReplacement(CardDavContact contact, CardDavContact otherContact,
            CardDavContactAttributes attribute) {
        if (mergeAttributeSimpleIfPossible(contact, otherContact, attribute)) {
            return;
        }

        // check in the notes section, whether one of the values is known for
        // being old
        // contactNotes

        // TODO Auto-generated method stub

    }





    private static void mergeThroughAppending(CardDavContact contact, CardDavContact otherContact,
            CardDavContactAttributes attribute) {
        if (mergeAttributeSimpleIfPossible(contact, otherContact, attribute)) {
            return;
        }

        String contactValue = contact.getAttributeValue(attribute);
        String otherContactValue = otherContact.getAttributeValue(attribute);

        String mergedValue = contactValue + " " + otherContactValue;

        contact.setAttributeValue(attribute, mergedValue);
    }





    /**
     * Merges the corresponding attribute values of the provided contacts unless
     * both are not <code>null</code>.
     * 
     * @param contact
     *            the contact to update
     * @param otherContact
     *            the contact to get additional data for
     * @param attribute
     *            the contact attribute to get the values for
     * @return <code>true</code> if a simple merge has been done (one or both
     *         attribute values are <code>null</code>), else <code>false</code>
     */
    private static boolean mergeAttributeSimpleIfPossible(CardDavContact contact, CardDavContact otherContact,
            CardDavContactAttributes attribute) {
        if (otherContact.hasAttribute(attribute)) {
            if (contact.hasAttribute(attribute)) {
                return false;
            } else {
                contact.setAttributeValue(attribute, otherContact.getAttributeValue(attribute));
                return true;
            }
        } else {
            return true;
        }
    }
}