package com.github.tobias_vogel.mergecontacts;

import java.util.Set;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.datasources.FileLoaderAndClenser;
import com.github.tobias_vogel.mergecontacts.merging.MergeCoordinator;

class MergeContacts {

    private static String mainContactFilenSpecifier;

    private static Set<CardDavContact> mainContacts;

    private static String[] additionalContactsFileSpecifiers;

    private static Set<CardDavContact> additionalContacts;





    public static void main(String[] args) {
        parseFileSpecifiersFromCommandLineArguments(args);

        loadAndClenseContactsFromFiles();

        mergeContacts();

        exportContactsToTsv();

        System.out.println("Finished");
    }





    private static void parseFileSpecifiersFromCommandLineArguments(String[] filenames) {
        // TODO use jcommander to parse command line arguments

        /*
         * @targetFilename = filenames.shift() if @targetFilename.nil? raise
         * "No target file was provided. Exiting" elsif
         * File.exists?(@targetFilename) raise
         * "Target file #{@targetFilename} exists already. Exiting." end XXX
         * 
         * @filenames = filenames
         */

        // fake result
        mainContactFilenSpecifier = "CSV:data/blabla.csv";
        additionalContactsFileSpecifiers = new String[] { "TSV:data/huhu.tsv", "VCARD:data/haha.vcard" };
    }





    private static void loadAndClenseContactsFromFiles() {
        mainContacts = FileLoaderAndClenser.loadFileAndClenseContacts(mainContactFilenSpecifier);

        for (String additionalFileSpecifier : additionalContactsFileSpecifiers) {
            additionalContacts.addAll(FileLoaderAndClenser.loadFileAndClenseContacts(additionalFileSpecifier));
        }
    }





    private static void mergeContacts() {
        mainContacts = MergeCoordinator.coordinateMerge(mainContacts, additionalContacts);
    }





    private static void exportContactsToTsv() {
        // todo
    }
}