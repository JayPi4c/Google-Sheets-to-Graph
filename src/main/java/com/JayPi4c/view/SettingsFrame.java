package com.JayPi4c.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.JayPi4c.utils.ILanguageChangeListener;
import com.JayPi4c.utils.Messages;

public class SettingsFrame extends JFrame implements ILanguageChangeListener {

	private static final long serialVersionUID = 1L;
	public static boolean isActive = false;

	public static final int SETTINGS_CHOOSE_WIDTH = 150;
	public static final int SETTINGS_HEIGHT = 400;
	public static final int SETTINGS_OPTION_WIDTH = 400;

	private JScrollPane choosePane;
	private JPanel settingsChoosePanel;
	private JPanel settingsPanel;
	JButton graph, google, language;

	public SettingsFrame(Component parent) {
		super(Messages.getString("Settings.title"));
		isActive = true;

		setLayout(new BorderLayout());

		settingsPanel = new GraphSettings();
		add(settingsPanel, BorderLayout.EAST);

		settingsChoosePanel = new JPanel();
		settingsChoosePanel.setLayout(new BoxLayout(settingsChoosePanel, BoxLayout.Y_AXIS));

		graph = new JButton(Messages.getString("Settings.graph"));
		graph.setMaximumSize(new Dimension(SETTINGS_CHOOSE_WIDTH, (int) graph.getPreferredSize().getHeight()));
		graph.addActionListener(e -> {
			invalidate();
			remove(settingsPanel);
			add(settingsPanel = new GraphSettings(), BorderLayout.EAST);
			validate();
		});

		google = new JButton(Messages.getString("Settings.google"));
		google.setMaximumSize(new Dimension(SETTINGS_CHOOSE_WIDTH, (int) google.getPreferredSize().getHeight()));
		google.addActionListener(e -> {
			invalidate();
			remove(settingsPanel);
			add(settingsPanel = new GoogleSettings(), BorderLayout.EAST);
			validate();
		});

		language = new JButton(Messages.getString("Settings.language"));
		language.setMaximumSize(new Dimension(SETTINGS_CHOOSE_WIDTH, (int) language.getPreferredSize().getHeight()));
		language.addActionListener(e -> {
			invalidate();
			remove(settingsPanel);
			add(settingsPanel = new LanguageSettings(), BorderLayout.EAST);
			validate();
		});
		settingsChoosePanel.add(graph);
		settingsChoosePanel.add(google);
		settingsChoosePanel.add(language);

		choosePane = new JScrollPane();
		choosePane.setPreferredSize(new Dimension(SETTINGS_CHOOSE_WIDTH, SETTINGS_HEIGHT));
		choosePane.setViewportView(settingsChoosePanel);

		add(choosePane, BorderLayout.WEST);

		pack();
		setVisible(true);
		setAlwaysOnTop(true);
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		Messages.registerListener(this);

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {

			}

			@Override
			public void windowClosed(WindowEvent e) {
				isActive = false;
			}

			@Override
			public void windowActivated(WindowEvent e) {

			}
		});
	}

	@Override
	public void onLanguageChanged() {
		this.setTitle(Messages.getString("Settings.title"));
		google.setText(Messages.getString("Settings.google"));
		graph.setText(Messages.getString("Settings.graph"));
		language.setText(Messages.getString("Settings.language"));
	}

}
