package com.github.tobias_vogel.mergecontacts.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Resources;

public class ConfigurationTest {

    @Test
    public void testDefaultSettings() {
        try {
            Configuration.getDefaultValue("defaultCountry");
        } catch (Exception e) {
            // expected
        }

        try {
            Configuration.getDefaultValue("defaultCountryCode");
        } catch (Exception e) {
            // expected
        }

        Configuration.loadDefaultProperties();

        Assert.assertNotNull(Configuration.getDefaultValue("defaultCountry"));
        Assert.assertNotNull(Configuration.getDefaultValue("defaultCountryCode"));
        Assert.assertNotNull(Configuration.getAlias("company"));
    }





    @Test
    public void testSettingsWithNonexistingConfigurationFile() {
        try {
            Configuration.loadProperties("some-nonexisting-file.properties");
            Assert.fail();
        } catch (Exception e) {
            // expected
        }
    }





    @Test
    public void testSettingsWithMissingDefaults() {
        try {
            Configuration.loadProperties(Resources.getResource("settings-with-missing-defaults.properties").getPath());
            Assert.fail();
        } catch (Exception e) {
            // expected
        }

        try {
            Configuration.getDefaultValue("defaultCountryCode");
            Assert.fail();
        } catch (Exception e) {
            // expected
        }
    }





    @Test
    public void testSettingsWithContradictoryCardDavContactAttributes() {
        try {
            Configuration.loadProperties(
                    Resources.getResource("settings-with-contradictory-carddavcontactattributes.properties").getPath());
            Assert.fail();
        } catch (Exception e) {
            // expected
        }
    }





    @Test
    public void testSettingsWithNonexistingCardDavContactAttributes() {
        try {
            Configuration.loadProperties(
                    Resources.getResource("settings-with-non-existing-carddavcontactattributes.properties").getPath());
            Assert.fail();
        } catch (Exception e) {
            // expected
        }
    }





    @Before
    public void setUp() throws Exception {
        Configuration.initializeMaps();
    }
}