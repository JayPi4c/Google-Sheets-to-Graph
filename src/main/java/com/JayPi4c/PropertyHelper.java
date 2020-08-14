package com.JayPi4c;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyHelper {

	static Properties properties;

	// there is the option to put the init functionality in the getProperty
	// function. It would allow to edit the properties file at runtime but can also
	// slow down the program.

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
