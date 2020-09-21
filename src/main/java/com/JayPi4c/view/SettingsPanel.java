package com.JayPi4c.view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.JayPi4c.utils.ILanguageChangeListener;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.utils.PropertyHelper;

public abstract class SettingsPanel extends JPanel implements ILanguageChangeListener {

	private static final long serialVersionUID = 1L;

	protected JButton apply;
	protected JPanel contentPanel;

	private List<SettingsElement> SettingsElements;

	public SettingsPanel() {
		setPreferredSize(new Dimension(SettingsFrame.SETTINGS_OPTION_WIDTH, SettingsFrame.SETTINGS_HEIGHT));

		apply = new JButton(Messages.getString("Settings.apply"));
		apply.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel applyPane = new JPanel();
		applyPane.setLayout(new BoxLayout(applyPane, BoxLayout.Y_AXIS));
		applyPane.setPreferredSize(
				new Dimension(SettingsFrame.SETTINGS_OPTION_WIDTH, (int) apply.getPreferredSize().getHeight()));
		applyPane.add(apply);
		apply.addActionListener(e -> {
			for (SettingsElement se : SettingsElements)
				PropertyHelper.setProperty(se.getPropertyKey(), se.getInput());

			PropertyHelper.saveProperties();
		});
		JScrollPane contentPane = new JScrollPane();
		contentPane.setPreferredSize(
				new Dimension(SettingsFrame.SETTINGS_OPTION_WIDTH, (int) apply.getPreferredSize().getHeight()));
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		SettingsElements = new ArrayList<SettingsElement>();
		loadOptions();

		contentPane.setViewportView(contentPanel);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(contentPane);
		add(applyPane);

	}

	abstract void loadOptions();

	void addSettingsElement(SettingsElement se, int index) {
		contentPanel.add(se, index);
		SettingsElements.add(index, se);
	}

	@Override
	public void onLanguageChanged() {
		apply.setText(Messages.getString("Settings.apply"));
	}

}
