package com.github.tobias_vogel.mergecontacts.datasources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.google.common.io.Resources;

import junit.framework.Assert;

public class TabularDataSourceTest {

    @Test
    public void testLoadResource() {

        String x = Resources.getResource("test.tsv").getFile();
        File f = new File(x);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("fertig");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("kaputt");
        }
    }





    @Test
    public void testReadContacts() {
        URL resource = Resources.getResource("test.tsv");

        TsvDataSource tsvDataSource = new TsvDataSource();
        Set<CardDavContact> contacts = tsvDataSource.readContacts(resource.getPath());

        Set<CardDavContact> expectedContacts = new HashSet<>(Arrays.asList(
                new CardDavContact.Builder().gname("Peter").fname("Jones").hphone("11111").notes("blabla").build(),
                new CardDavContact.Builder().gname("Nora").fname("Smith").hphone("22222").notes("").build(),
                new CardDavContact.Builder().gname("Trudy").fname("Truda").hphone("")
                        .notes("a note over multiple lines").build(),
                new CardDavContact.Builder().gname("Mike").fname("Smith").hphone("3333333").notes("").build(),
                new CardDavContact.Builder().gname("Brad").fname("Johnsen").hphone("").notes("").build(),
                new CardDavContact.Builder().gname("Richard").fname("Parton").hphone("44444")
                        .notes("a note with some trailing newlines").build(),
                new CardDavContact.Builder().gname("Olivia").fname("Nitz").hphone("55555").notes("").build()));

        for (CardDavContact contact : contacts) {
            Assert.assertTrue(expectedContacts.remove(contact));
        }
        Assert.assertTrue(expectedContacts.isEmpty());
    }
}