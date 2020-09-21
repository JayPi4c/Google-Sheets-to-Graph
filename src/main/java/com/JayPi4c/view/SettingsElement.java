package com.JayPi4c.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.JayPi4c.utils.ILanguageChangeListener;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.utils.PropertyHelper;

public class SettingsElement extends JPanel implements ILanguageChangeListener {

	private static final long serialVersionUID = 1L;
	private TitledBorder titledBorder;
	private JButton reset;
	private JTextField inputField;
	private String initialValue;
	private String propertyKey;
	private String titleKey;

	public SettingsElement(String title, String propertyKey) {
		this.initialValue = PropertyHelper.getProperty(this.propertyKey = propertyKey);
		setBorder(titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
				Messages.getString(titleKey = title)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		inputField = new JTextField(initialValue.toString(), 20);
		// https://stackoverflow.com/a/18407186
		inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getMinimumSize().height));

		add(inputField);
		add(reset = new JButton(Messages.getString("Settings.reset")));

		Messages.registerListener(this);

	}

	public String getInput() {
		return inputField.getText();

	}

	public void setInput(String input) {
		inputField.setText(input);
	}

	public String getResetValue() {
		return initialValue;
	}

	public TitledBorder getTitledBorder() {
		return titledBorder;
	}

	public JButton getResetButton() {
		return reset;
	}

	public void setResetController(ActionListener controller) {
		reset.addActionListener(controller);
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	@Override
	public void onLanguageChanged() {
		titledBorder.setTitle(Messages.getString(titleKey));
		reset.setText(Messages.getString("Settings.reset"));

	}
}
