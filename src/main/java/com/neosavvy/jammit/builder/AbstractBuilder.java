package com.neosavvy.jammit.builder;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.neosavvy.jammit.util.Config;
import com.neosavvy.jammit.util.StringUtils;

public abstract class AbstractBuilder implements Builder {

	public void compile() {
		for (File file : getFiles()) {
			compile(file);
		}
	}

	public Collection<File> getFiles() {
		IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(fileExtension()));

		return FileUtils.listFiles(new File(Config.SOURCE_PATH), files, TrueFileFilter.INSTANCE);
	}

	public String concat() throws Exception {
		StringBuilder destination = new StringBuilder();
		for (File file : getFiles()) {
			destination.append(FileUtils.readFileToString(file));
		}

		return destination.toString();
	}

	public void write(String destinationFile, String compiled) throws Exception {
		FileUtils.writeStringToFile(new File(StringUtils.joinPath(Config.PUBLISH_PATH, destinationFile)), compiled, "UTF-8");
	}

	public abstract String fileExtension();

	public void compile(File file) {

	}

	public String getDestinationPath(File file) throws Exception {
		return StringUtils.substringAfter(FilenameUtils.separatorsToUnix(file.getCanonicalPath()), FilenameUtils.separatorsToUnix(Config.SOURCE_PATH));
	}
}
