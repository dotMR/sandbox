package com.neosavvy.jammit.watcher;

import java.io.File;

public class JstFileAlterationListener extends AbstractFileAlterationListener {

	@Override
	public void onFileDelete(File arg0) {
		System.out.println("Deleted...");

	}

	public void onFileCreate(File arg0) {
		System.out.println("File Created...");

	}

	public void onFileChange(File arg0) {
		System.out.println("File Changed...");

	}

}
