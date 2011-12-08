package com.neosavvy.jammit;

import java.io.File;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.neosavvy.jammit.builder.BuildManager;
import com.neosavvy.jammit.builder.CssBuilder;
import com.neosavvy.jammit.builder.HtmlBuilder;
import com.neosavvy.jammit.builder.JavascriptBuilder;
import com.neosavvy.jammit.util.Config;
import com.neosavvy.jammit.util.Log;
import com.neosavvy.jammit.util.StopWatch;
import com.neosavvy.jammit.util.StringUtils;
import com.neosavvy.jammit.watcher.CssFileAlterationListener;
import com.neosavvy.jammit.watcher.HtmlFileAlterationListener;
import com.neosavvy.jammit.watcher.JavascriptFileAlterationListener;
import com.neosavvy.jammit.watcher.JstFileAlterationListener;

public class AssetManager {

	BuildManager buildManager = new BuildManager();

	public static void main(String[] args) {
		try {
			if (StringUtils.isAnyEquivalent("--watch", args)) {
				new AssetManager().init().watch();
			} else {
				new AssetManager().init().compile();
			}
		} catch (Throwable ex) {
			Log.info("Error within Jammit...");
		}
	}

	public AssetManager init() {
		new Config("./jammit.conf").validate();
		buildManager.addBuilder(new CssBuilder());
		buildManager.addBuilder(new JavascriptBuilder());
		buildManager.addBuilder(new HtmlBuilder());

		return this;
	}

	public AssetManager watch() throws Exception {
		Log.info("Interval: ", Config.getMonitorInterval());
		FileAlterationMonitor monitor = new FileAlterationMonitor(Config.getMonitorInterval());
		monitor.addObserver(createObserver(".css", new CssFileAlterationListener()));
		monitor.addObserver(createObserver(".js", new JavascriptFileAlterationListener()));
		monitor.addObserver(createObserver(".html", new HtmlFileAlterationListener()));
		monitor.addObserver(createObserver(".jst", new JstFileAlterationListener()));
		monitor.start();

		return this;
	}

	public AssetManager compile() throws Exception {
		StopWatch monitor = new StopWatch();
		monitor.start();
		try {
			buildManager.compile();

			return this;
		} finally {
			monitor.stop();
			Log.info("Total Time: %s", monitor.getTotalTimeMillis());
		}
	}

	protected FileAlterationObserver createObserver(String suffix, FileAlterationListener listener) {
		IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
		IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(suffix));
		IOFileFilter filter = FileFilterUtils.or(directories, files);

		// Create the File system observer and register File Listeners
		File f = new File(Config.SOURCE_PATH);
		FileAlterationObserver observer = new FileAlterationObserver(f, filter);
		observer.addListener(listener);

		return observer;
	}

}
