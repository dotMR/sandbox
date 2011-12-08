package com.neosavvy.jammit.util;

import java.io.File;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

public class Config {

	private static Map<String, Object> properties;
	
	public static String SOURCE_PATH = "./";
	public static String PUBLISH_PATH = "publish";
	public static String HTTP_PATH = "./";
	public static String EXCLUDE_FILES = "";

	public static String CSS_FOLDER = "css";
	public static String CSS_DESTINATION = "test.css";
	public static boolean CSS_COMPRESS = true;

	public static String JS_FOLDER = "js";
	public static String JS_DESTINATION = "test.js";
	public static boolean JS_COMPRESS = false;

	public static boolean HTML_COMPRESS = true;

	public static int MONITOR_INTERVAL = 1000;

	public Config(String filePath) {
		this(new File(filePath));
		Log.info("Properties were loaded.");
	}

	public Config(File file) {
		Log.info("Loading properties: %s", file);
		init(file);
	}

	public void validate() {
		if (StringUtils.isAnyEquivalent(Config.PUBLISH_PATH, ".", "..", "/", "./", "../")) {
			Log.info("The publish_dir property is set to: %s.  This will potentially overwrite all of your assets.  Please set it to something like 'publish'.", Config.PUBLISH_PATH);
			System.exit(1000);
		}
	}

	@SuppressWarnings("unchecked")
	protected void init(File file) {
		try {
			String s = FileUtils.readFileToString(file);

			Properties p = new Properties();
			p.load(new StringReader(s));

			Log.info("Properties: %s", p);

			Yaml yaml = new Yaml();
			String s1 = FileUtils.readFileToString(StringUtils.getClasspathFile("yaml.conf"));
			
			properties = (Map<String, Object>) yaml.load(s1);
			System.out.println(s1);
			System.out.println(properties);
			System.out.println(properties.get("source_dir"));
			System.out.println(properties.get("exclude_files"));
			
			Map<String, Object> json = (Map<String, Object>)properties.get("test");
			System.out.println(json);
			System.out.println(json.get("inner"));
			System.out.println(((List<String>)properties.get("files")).size());

		} catch (Throwable ex) {
			Log.info("Error: %s", ex);
		}
	}

	public static String getString(String fieldName, String def) {
		return def;
	}

	public static int getMonitorInterval() {
		return 1;
	}
	
	public static Map<String, Object> getProperties() {
		return properties;
	}

	public static boolean hasKey(String key) {
		return getProperties().get(key) != null;
	}

	public static String getString(String key) {
		if (!hasKey(key))
			return "";

		return (String)getProperties().get(key);
	}

}
