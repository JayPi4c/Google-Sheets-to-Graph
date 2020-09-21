package com.JayPi4c.view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.JayPi4c.utils.Messages;

public class LanguageSettings extends JPanel {

	private static final long serialVersionUID = 1L;

	JButton english, german;

	public LanguageSettings() {
		setPreferredSize(new Dimension(SettingsFrame.SETTINGS_OPTION_WIDTH, SettingsFrame.SETTINGS_HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		english = new JButton(Messages.getString("language.english"));
		english.addActionListener(e -> Messages.changeBundle("com.JayPi4c.lang.messages_en"));
		add(english);
		german = new JButton(Messages.getString("language.german"));
		german.addActionListener(e -> Messages.changeBundle("com.JayPi4c.lang.messages_de"));
		add(german);
	}
}
