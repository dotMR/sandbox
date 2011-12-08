package com.neosavvy.jammit.builder;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.neosavvy.jammit.util.Log;

public class JstBuilder extends AbstractBuilder {

	@Override
	public void compile(File file) {
		try {
			String compiled = FileUtils.readFileToString(file);

			write("", compiled);
		} catch (Throwable ex) {
			Log.info("Unable to compile file: %s", ex);
		}
	}

	@Override
	public String fileExtension() {
		return ".jst";
	}
}
