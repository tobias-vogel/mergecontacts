package com.github.tobias_vogel.mergecontacts.normalizers;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;

public class PhoneNumberNormalizer {

    public static void normalize(CardDavContact contact) {
        // TODO Auto-generated method stub
        // normalizePhoneNumber(contact.workphone)
        // normalizePhoneNumber(contact.homephone)
        // normalizePhoneNumber(contact.fax)
        // normalizePhoneNumber(contact.pager)
        // normalizePhoneNumber(contact.mobilephone)

    }

    // def Normalizer.normalizePhoneNumber(phoneNumber)
    // return if phoneNumber.nil?()
    //
    // phoneNumber.delete!("(")
    // phoneNumber.delete!(")")
    // phoneNumber.delete!("-")
    // phoneNumber.delete!(" ")
    //
    // # +49...
    // # add default country code unless a country code exists already
    // hasPlusCountryCode = phoneNumber[/^\+/] != nil
    // has00CountryCode = phoneNumber[/^00/] != nil
    // if hasPlusCountryCode
    // phoneNumber.gsub!(/^\+/, "00")
    // elsif has00CountryCode
    // # do nothing
    // else
    // # add 00 country code
    // #TODO read out country code from country
    // phoneNumber.gsub!(/^0/, @@configuration["defaultCountryCode"])
    // # don't touch numbers that do not start with a 0
    // end
    // end

}