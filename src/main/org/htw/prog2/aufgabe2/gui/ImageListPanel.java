package org.htw.prog2.aufgabe2.gui;

import org.htw.prog2.aufgabe2.Frame;
import org.htw.prog2.aufgabe2.ImageSeries;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class ImageListPanel extends JPanel {
    private ImageSeries image;
    private JPanel thumbnailPanel;
    private LinkedList<FrameThumbnail> markedFrames;
    private MainFrame mainFrame;
    private FrameThumbnail selectedThumbnail = null;

    public ImageListPanel(int width, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        JLabel name = new JLabel("Bilder");
        thumbnailPanel = new JPanel();
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(name);
        add(Box.createVerticalStrut(10));
        name.setAlignmentX(0);
        setPreferredSize(new Dimension(width, 400));
    }

    public void setSelectedFrame(Frame frame) {
    }

    public void setImage(ImageSeries newImage) {
    }

    private void updateSize() {
    }

    public void addMarkedFrame(Frame frame) {
    }

    public void removeMarkedFrame(Frame frame) {
    }
}
