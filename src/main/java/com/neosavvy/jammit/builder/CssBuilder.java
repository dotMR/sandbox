package com.neosavvy.jammit.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.neosavvy.jammit.util.Config;
import com.neosavvy.jammit.util.Log;
import com.neosavvy.jammit.util.StringUtils;
import com.yahoo.platform.yui.compressor.CssCompressor;

public class CssBuilder extends AbstractBuilder {

	@Override
	public void compile() {
		try {
			String source = concat();

			write(StringUtils.joinPath(Config.CSS_FOLDER, Config.CSS_DESTINATION), source);

		} catch (Throwable ex) {
			Log.info("Unable to compile file: %s", ex);
		}
	}

	@Override
	public String fileExtension() {
		return ".css";
	}

	public void compress() {

		Log.info("Minifying CSS...");

		try {
			IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".css"));

			for (File file : FileUtils.listFiles(new File(Config.SOURCE_PATH), files, TrueFileFilter.INSTANCE)) {
				Reader in = null;
				Writer out = null;
				try {
					in = new InputStreamReader(new FileInputStream(file));
					out = new OutputStreamWriter(FileUtils.openOutputStream(new File(StringUtils.joinPath("publish", file.getPath()))), "UTF-8");
					CssCompressor compressor = new CssCompressor(in);
					compressor.compress(out, 4000);
				} finally {
					if (in != null) {
						in.close();
					}

					if (out != null) {
						out.close();
					}
				}
			}
		} catch (Throwable ex) {
			Log.info("Error compressing: %s", ex);
		}
	}

}
