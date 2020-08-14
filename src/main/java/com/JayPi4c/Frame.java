package com.JayPi4c;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	public Frame(File chartSVG) {
		super("Sheets to Graph");

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

			}
		});

		canvas.setURI(url);

		getContentPane().add(canvas);
		setIcon("com/JayPi4c/assets/icon.png");
		setVisible(true);

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

}
