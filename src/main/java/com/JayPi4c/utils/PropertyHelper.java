package com.JayPi4c.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyHelper {

	static Properties properties;

	static String path = "./main.properties";
	// there is the option to put the init functionality in the getProperty
	// function. It would allow to edit the properties file at runtime but can also
	// slow down the program.

	public static void init() {
		properties = new Properties();

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

	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public static void saveProperties() {
		try (FileOutputStream fos = new FileOutputStream(path)) {
			properties.store(fos, null);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

}
