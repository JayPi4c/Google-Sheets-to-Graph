package com.JayPi4c.view;

public class GoogleSettings extends SettingsPanel {

	private static final long serialVersionUID = 1L;

	@Override
	void loadOptions() {
		SettingsElement settingsID = new SettingsElement("google.spreadsheet.ID", "google.spreadsheet.ID");
		addSettingsElement(settingsID, 0);
		SettingsElement settingsName = new SettingsElement("google.spreadsheet.name", "google.spreadsheet.name");
		addSettingsElement(settingsName, 1);
		SettingsElement settingsRange = new SettingsElement("google.spreadsheet.range", "google.spreadsheet.range");
		addSettingsElement(settingsRange, 2);
	}

}
