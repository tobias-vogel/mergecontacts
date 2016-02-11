package com.github.tobias_vogel.mergecontacts;

import java.util.Set;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.merging.MergeCoordinator;

class MergeContacts {

    private static String mainContactFilename;

    private static Set<CardDavContact> mainContacts;

    private static String[] additionalContactsFilenames;

    private static Set<CardDavContact> additionalContacts;





    public static void main(String[] args) {
        parseCommandLineArguments(args);

        // #TODO for conflict resolution during merging, it is good to define a
        // main contact file and merge in all other information
        loadAndClenseContactsFromFiles();

        mergeContacts();

        exportContactsToTsv();

        System.out.println("Finished");
    }





    private static void parseCommandLineArguments(String[] filenames) {
        // TODO use jcommander to parse command line arguments

        mainContactFilename = "data/blabla.csv";
        additionalContactsFilenames = new String[] { "data/huhu.tsv", "data/haha.vcard" };
        /*
         * @targetFilename = filenames.shift() if @targetFilename.nil? raise
         * "No target file was provided. Exiting" elsif
         * File.exists?(@targetFilename) raise
         * "Target file #{@targetFilename} exists already. Exiting." end XXX
         * 
         * @filenames = filenames
         */
    }





    private static void loadAndClenseContactsFromFiles() {
        /*
         * mainContactFileSpecifier = @fileSpecifiers.shift()
         * 
         * @mainContacts = loadFileAndCleanseContacts(mainContactFileSpecifier)
         * 
         * @otherContactSets = []
         * 
         * @fileSpecifiers.each() do |fileSpecifier|
         * 
         * @otherContactSets << [loadFileAndCleanseContacts(fileSpecifier)] end
         */
    }





    private void loadFileAndClenseContacts(String fileSpecifier) {
        /*
         * prefix, filename = detectType(fileSpecifier)
         * 
         * contacts = case prefix when "csv" then
         * CsvDataSource.new(filename).loadAndClenseContacts() when "tsv" then
         * TsvDataSource.new(filename).loadAndClenseContacts() when "vcard" then
         * VcardDataSource.new(filename).loadAndClenseContacts() else raise
         * "Unknown prefix #{prefix}. Exiting." end return contacts
         */
    }





    private void detectType(String fileSpecifier) {
        /*
         * prefix, filename = fileSpecifier.split(":", 2) return prefix,
         * filename
         */
    }





    private static void mergeContacts() {
        mainContacts = MergeCoordinator.coordinateMerge(mainContacts, additionalContacts);
    }





    private static void exportContactsToTsv() {
        // todo
    }
}