package com.github.tobias_vogel.mergecontacts;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.beust.jcommander.JCommander;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.datasources.FileLoaderAndClenser;
import com.github.tobias_vogel.mergecontacts.merging.MergeCoordinator;
import com.github.tobias_vogel.mergecontacts.utils.CommandlineParser;

class MergeContacts {

	private static String mainContactFileSpecifier;

	private static Set<CardDavContact> mainContacts;

	private static List<String> additionalContactsFileSpecifiers;

	private static Set<CardDavContact> additionalContacts;

	private static String targetFilename;

	private static boolean overwriteTargetFile;

	public static void main(String[] args) {
		parseFileSpecifiersFromCommandLineArguments(args);

		loadAndClenseContactsFromFiles();

		mergeContacts();

		// TODO respect an overwrite flag that allows to overwrite the possibly
		// existing target file
		exportContactsToTsv();

		System.out.println("Finished");
	}

	private static void parseFileSpecifiersFromCommandLineArguments(String[] commandlineArguments) {
		CommandlineParser commandlineParser = new CommandlineParser();
		new JCommander(commandlineParser, commandlineArguments);

		mainContactFileSpecifier = commandlineParser.getMainFileSpecifier();
		additionalContactsFileSpecifiers = commandlineParser.getAdditionalFileSpecifiers();
		targetFilename = commandlineParser.getTargetFilename();
		overwriteTargetFile = commandlineParser.overwriteTargetFile();

		// fake result
		// TODO remove fake results
		mainContactFileSpecifier = "CSV:data/blabla.csv";
		additionalContactsFileSpecifiers = Arrays.asList(new String[] { "TSV:data/huhu.tsv", "VCARD:data/haha.vcard" });
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