package com.JayPi4c.view;

public class GraphSettings extends SettingsPanel {

	private static final long serialVersionUID = 1L;

	@Override
	void loadOptions() {
		SettingsElement SettingsYMin = new SettingsElement("graph.y.min", "graph.y.min");
		addSettingsElement(SettingsYMin, 0);
		SettingsElement SettingsYMax = new SettingsElement("graph.y.max", "graph.y.max");
		addSettingsElement(SettingsYMax, 1);
		SettingsElement SettingStartdate = new SettingsElement("graph.startdate", "graph.startdate");
		addSettingsElement(SettingStartdate, 2);
		SettingsElement Settingenddate = new SettingsElement("graph.enddate", "graph.enddate");
		addSettingsElement(Settingenddate, 3);
	}

}
