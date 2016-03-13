package com.github.tobias_vogel.mergecontacts.utils;

import java.util.List;

import com.beust.jcommander.Parameter;

public class CommandlineParser {

	@Parameter(names = { "--main, -m" }, description = "Main contact file specifier (wins on merge ties)")
	private String mainFileSpecifier;

	@Parameter(names = { "--target, -t" }, description = "The target filename")
	private String targetFilename;

	@Parameter(names = { "--overwrite, -o" }, description = "Whether or not to overwrite the target file if it exists")
	private boolean overwriteTargetFile = false;

	@Parameter(names = { "--additional, -a" }, description = "Additional contact file specifier")
	private List<String> additionalFileSpecifiers;

	public String getMainFileSpecifier() {
		return mainFileSpecifier;
	}

	public String getTargetFilename() {
		return targetFilename;
	}

	public boolean overwriteTargetFile() {
		return overwriteTargetFile;
	}

	public List<String> getAdditionalFileSpecifiers() {
		return additionalFileSpecifiers;
	}
}