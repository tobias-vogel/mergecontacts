package com.github.tobias_vogel.mergecontacts.normalizers;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;

public class PhoneNumberNormalizer {

    public static void normalize(CardDavContact contact) {

        if (contact == null) {
            return;
        }

        CardDavContactAttributes[] numberAttributes = { CardDavContactAttributes.WORK_PHONE,
                CardDavContactAttributes.HOME_PHONE, CardDavContactAttributes.FAX, CardDavContactAttributes.PAGER,
                CardDavContactAttributes.MOBILE_PHONE };
        // TODO use java8 lamda magic here (?)
        for (CardDavContactAttributes numberAttribute : numberAttributes) {
            if (contact.hasAttribute(numberAttribute)) {
                String number = contact.getAttributeValue(numberAttribute);
                String normalizedNumber = normalizePhoneNumber(number);
                contact.setAttributeValue(numberAttribute, normalizedNumber);
            }
        }

    }





    private static String normalizePhoneNumber(String number) {
        if (number == null) {
            return number;
        }

        number = removeNoise(number);

        number = ensure00CountryCode(number);

        return number;
    }





    private static String ensure00CountryCode(String number) {
        if (has00CountryCode(number)) {
            return number;
        }

        if (hasPlusCountryCode(number)) {
            return replacePlusCountryCodeBy00(number);
        }

        if (hasRegularNumber(number)) {
            String defaultCountryCode = "0049"; // TODO take from defaults
            return replaceRegularCountryCodeBy00(number, defaultCountryCode);
        }

        return number;
    }





    private static String replaceRegularCountryCodeBy00(String number, String defaultCountryCode) {
        return number.replaceAll("^0", defaultCountryCode);
    }





    private static String replacePlusCountryCodeBy00(String number) {
        return number.replaceAll("^\\+", "00");
    }





    private static boolean hasRegularNumber(String number) {
        return number.matches("^0.*");
    }





    private static boolean hasPlusCountryCode(String number) {
        return number.matches("^\\+.*");
    }





    private static boolean has00CountryCode(String number) {
        return number.matches("^00.*");
    }





    private static String removeNoise(String number) {
        number = number.replaceAll("\\(", "");
        number = number.replaceAll("\\)", "");
        number = number.replaceAll("-", "");
        number = number.replaceAll("/", "");
        number = number.replaceAll(" ", "");
        return number;
    }
}