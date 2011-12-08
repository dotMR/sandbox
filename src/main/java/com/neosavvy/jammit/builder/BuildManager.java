package com.neosavvy.jammit.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import com.neosavvy.jammit.util.Config;
import com.neosavvy.jammit.util.Log;

public class BuildManager {

	private List<Builder> builders = new ArrayList<Builder>();

	public void addBuilder(Builder builder) {
		builders.add(builder);
	}

	public void compile() throws Exception {
		copy();
		
		for (Builder builder : builders) {
			builder.compile();
		}
	}

	/*
	 * Removes all asset types from the existing publish directory
	 */
	public void clean() throws Exception {
		Log.info("Removing the existing publish directory...");
		FileUtils.deleteDirectory(new File(Config.PUBLISH_PATH));
	}

	/*
	 * Increase the build number by 1 and set a date
	 */
	public void revision() throws Exception {
		//TODO
	}

	/*
	 * This will copy everything from the source folder to the publish folder that
	 * haven't been excluded.
	 */
	public void copy() throws Exception {
		Log.info("Copying over all non processed files...");
		IOFileFilter noJsFilesFilter = FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".js"));
		IOFileFilter noCssFilesFilter = FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".css"));
		IOFileFilter noHtmlFilesFilter = FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".html"));
		IOFileFilter filter = FileFilterUtils.and(noJsFilesFilter, noCssFilesFilter, noHtmlFilesFilter);
		System.out.println((new File(Config.PUBLISH_PATH).getCanonicalPath().startsWith(new File(Config.SOURCE_PATH).getCanonicalPath())));
		FileUtils.copyDirectory(new File(Config.SOURCE_PATH), new File(Config.PUBLISH_PATH), filter);
	}


}
