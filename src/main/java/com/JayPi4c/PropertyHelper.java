package com.JayPi4c;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyHelper {

	static Properties properties;

	public static void init() {
		properties = new Properties();

		String path = "./main.properties";

		try (FileInputStream FIS = new FileInputStream(path)) {
			properties.load(FIS);
		} catch (Exception e) {
			System.err.println("could not load properties");
			System.err.println(e);
			System.err.println("\nexiting...");
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

}
