package com.neosavvy.jammit.watcher;

import java.io.File;

import com.neosavvy.jammit.builder.HtmlBuilder;
import com.neosavvy.jammit.util.Log;

public class HtmlFileAlterationListener extends AbstractFileAlterationListener {

	HtmlBuilder builder = new HtmlBuilder();
	
	@Override
	public void onFileDelete(File arg0) {
		Log.info("Html Deleted...");
	}

	public void onFileCreate(File arg0) {
		Log.info("Html File Created...");
	}

	public void onFileChange(File file) {
		Log.info("Html File Changed...");
	}

}
