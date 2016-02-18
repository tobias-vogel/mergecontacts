package com.github.tobias_vogel.mergecontacts;

import java.util.Set;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.datasources.FileLoaderAndClenser;
import com.github.tobias_vogel.mergecontacts.merging.MergeCoordinator;

class MergeContacts {

    private static String mainContactFileSpecifier;

    private static Set<CardDavContact> mainContacts;

    private static String[] additionalContactsFileSpecifiers;

    private static Set<CardDavContact> additionalContacts;

    private static String targetFilename;





    public static void main(String[] args) {
        parseFileSpecifiersFromCommandLineArguments(args);

        loadAndClenseContactsFromFiles();

        mergeContacts();

        exportContactsToTsv();

        System.out.println("Finished");
    }





    private static void parseFileSpecifiersFromCommandLineArguments(String[] filenames) {
        // TODO use jcommander to parse command line arguments

        // TODO respect an overwrite flag that allows to overwrite the possibly
        // existing target file

        // fake result
        mainContactFileSpecifier = "CSV:data/blabla.csv";
        additionalContactsFileSpecifiers = new String[] { "TSV:data/huhu.tsv", "VCARD:data/haha.vcard" };
    }





    private static void loadAndClenseContactsFromFiles() {
        mainContacts = FileLoaderAndClenser.loadFileAndClenseContacts(mainContactFileSpecifier);

        for (String additionalFileSpecifier : additionalContactsFileSpecifiers) {
            additionalContacts.addAll(FileLoaderAndClenser.loadFileAndClenseContacts(additionalFileSpecifier));
        }
    }





    private static void mergeContacts() {
        mainContacts = MergeCoordinator.coordinateMerge(mainContacts, additionalContacts);
    }





    private static void exportContactsToTsv() {
        // TODO implement
        System.out.println("would (perhaps) write to " + targetFilename);
    }
}