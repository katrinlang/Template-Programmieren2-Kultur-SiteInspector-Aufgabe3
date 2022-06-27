package org.htw.prog2.aufgabe2.gui;

import org.htw.prog2.aufgabe2.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ImageDetailPanel extends JPanel {
    private MainFrame mainFrame;
    private JPopupMenu rightClickMenu;
    private Frame frame;
    private int markSize = 10;
    private boolean showTracing;

    public ImageDetailPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        rightClickMenu = new JPopupMenu();
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                if(mouseWheelEvent.getWheelRotation() < 0) {
                    mainFrame.setPreviousDetailFrame();
                }
                else {
                    mainFrame.setNextDetailFrame();
                }
            }
        });
    }

    public void paintComponent(Graphics g) {

        if(frame == null) {
            g.setColor(Color.BLACK);
            g.drawString("Bitte ein Bild laden", getWidth()/2, getHeight()/2);
        }
        else {
            BufferedImage image = frame.getCombinedImage(showTracing);
            int width = image.getWidth();
            int height = image.getHeight();
            float aspect = (float) width / (float) height;

            if ((float)getWidth() / (float)getHeight() < aspect) {
                width = getWidth();
                height = (int) (width / aspect);
            } else {
                height = getHeight();
                width = (int) (height * aspect);
            }
            g.drawImage(image, getWidth() / 2 - width / 2, getHeight() / 2 - height / 2, getWidth() / 2 + width / 2, getHeight() / 2 + height / 2, 0, 0, image.getWidth(), image.getHeight(), null);
        }
    }

    public void setDetailFrame(Frame frame, boolean showTracing) {
        this.showTracing = showTracing;
        this.frame = frame;
        setPreferredSize(new Dimension(frame.getImage().getWidth(), frame.getImage().getHeight()));
    }
}
