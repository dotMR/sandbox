package com.neosavvy.assets.util;

import org.junit.Test;

import static org.junit.Assert.*;

import com.neosavvy.jammit.util.Config;
import com.neosavvy.jammit.util.StringUtils;

public class ConfigTest {

	@Test
	public void testFindFiles() throws Exception {

		new Config(StringUtils.getClasspathFile("sample.conf"));

		//assertEquals(Config.SOURCE_PATH, "src");
		//assertEquals(Config.PUBLISH_PATH, "publish");

	}

}
