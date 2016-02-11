package com.github.tobias_vogel.mergecontacts.normalizers;

import java.util.Properties;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;

public class Normalizer {

    private static Properties defaults = null;





    public static void normalize(CardDavContact contact) {
        ensureDefaultsAreLoaded();

        // TODO normalize should normalize additional data, too

        EncodingNormalizer.normalize(contact);
        PhoneNumberNormalizer.normalize(contact);
        EmailNormalizer.normalize(contact);
        CountryNormalizer.normalize(contact);
        NameNormalizer.normalize(contact);
    }





    private static void ensureDefaultsAreLoaded() {
        if (defaults == null) {
            // loadDefaults();
            // #TODO read config.ini into configHash
            // @@configuration = {}
            // @@configuration["defaultCountryCode"] = "0049"
            // @@configuration["defaultCountry"] = "Deutschland"
            // end
        }
    }
}