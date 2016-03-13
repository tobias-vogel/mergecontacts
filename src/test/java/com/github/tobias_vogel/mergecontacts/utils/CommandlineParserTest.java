package com.github.tobias_vogel.mergecontacts.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.beust.jcommander.JCommander;

import junit.framework.Assert;

public class CommandlineParserTest {

	@Test
	public void testEverything() {
		CommandlineParser commandlineParser = new CommandlineParser();
		String commandlineArguments = "--main csv:abc.csv --additional csv:data1.dat --additional tsv:data2.dat --target output.tsv --overwrite";
		new JCommander(commandlineParser, commandlineArguments);

		Assert.assertEquals("csv:avc.csv", commandlineParser.getMainFileSpecifier());
		Assert.assertEquals(new ArrayList<String>(Arrays.asList("csv:data1.dat", "tsv:data2.dat")),
				commandlineParser.getAdditionalFileSpecifiers());
		Assert.assertEquals("output.csv", commandlineParser.getTargetFilename());
		Assert.assertEquals(true, commandlineParser.overwriteTargetFile());
	}
}