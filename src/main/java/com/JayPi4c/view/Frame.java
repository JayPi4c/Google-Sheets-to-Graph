package com.JayPi4c.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;

import com.JayPi4c.utils.ILanguageChangeListener;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.utils.Utils;

public class Frame extends JFrame implements ILanguageChangeListener {

	private static final long serialVersionUID = 1L;

	JMenu file, options;
	JMenuItem save, saveAs, settings;

	public Frame(File chartSVG) {
		super(Messages.getString("Frame.title"));

		String url = chartSVG.toURI().toString();
		// System.out.println(url);
		JSVGCanvas canvas = new JSVGCanvas();

		canvas.setEnableImageZoomInteractor(true);
		canvas.setEnableZoomInteractor(true);
		canvas.setFocusable(true);

		// Set the JSVGCanvas listeners.
		canvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {

			@Override
			public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
				System.out.println("Document Loading...");
			}

			@Override
			public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
				System.out.println("Document Loaded.");
			}
		});
		canvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
			@Override
			public void gvtBuildStarted(GVTTreeBuilderEvent e) {
				System.out.println("Build Started...");
			}

			@Override
			public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
				System.out.println("Build Done.");
				GraphicsNode root = canvas.getGraphicsNode();
				Rectangle2D rec = root.getPrimitiveBounds();
				canvas.setPreferredSize(new Dimension((int) rec.getWidth(), (int) rec.getHeight()));
				pack();
			}
		});
		canvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
			@Override
			public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
				System.out.println("Rendering Started...");
			}

			@Override
			public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
				System.out.println("Rendering Done.");
				setLocationRelativeTo(null);

			}
		});

		canvas.setURI(url);

		getContentPane().add(canvas);
		setIcon("com/JayPi4c/assets/icon.png");

		JMenuBar menuBar = new JMenuBar();
		file = new JMenu(Messages.getString("Frame.file"));
		save = new JMenuItem(Messages.getString("Frame.save"));
		save.addActionListener(e -> Utils.saveImage(chartSVG, "."));
		saveAs = new JMenuItem(Messages.getString("Frame.saveAs"));
		saveAs.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("choose save location");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				Utils.saveImage(chartSVG, chooser.getCurrentDirectory().toString());

		});

		file.add(save);
		file.add(saveAs);
		menuBar.add(file);
		options = new JMenu(Messages.getString("Frame.options"));
		settings = new JMenuItem(Messages.getString("Frame.settings"));
		settings.addActionListener(e -> {
			if (!SettingsFrame.isActive)
				new SettingsFrame(this);
		});
		options.add(settings);
		menuBar.add(options);

		setJMenuBar(menuBar);

		setVisible(true);

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Messages.registerListener(this);
	}

	void setIcon(String path) {
		List<Image> icons = new ArrayList<Image>();
		try {
			icons.add(ImageIO.read(ClassLoader.getSystemResource(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setIconImages(icons);
	}

	@Override
	public void onLanguageChanged() {
		setTitle(Messages.getString("Frame.title"));
		file.setText(Messages.getString("Frame.file"));
		save.setText(Messages.getString("Frame.save"));
		saveAs.setText(Messages.getString("Frame.saveAs"));
		options.setText(Messages.getString("Frame.options"));
		settings.setText(Messages.getString("Frame.settings"));
	}

}
