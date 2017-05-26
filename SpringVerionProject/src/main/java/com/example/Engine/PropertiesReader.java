package com.example.Engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertiesReader {

	private static Map<String, String> properties = new HashMap<>();

	public static void readProperties() throws IOException {
		properties.put("REPOSITORY_PATH", "/Repo");
	}

	public static Map<String, String> getProperties() {
		return properties;
	}

}
