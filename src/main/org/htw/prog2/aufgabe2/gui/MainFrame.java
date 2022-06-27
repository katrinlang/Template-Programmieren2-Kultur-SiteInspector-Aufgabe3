package org.htw.prog2.aufgabe2.gui;

import org.htw.prog2.aufgabe2.Frame;
import org.htw.prog2.aufgabe2.ImageSeries;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {

    private ImageListPanel imageList;
    private ImageDetailPanel detailPanel;
    private JSlider seriesSlider;
    private JLabel frameNumberLabel;
    private ImageSeries images;
    private JCheckBox edgeCheckBox;

    public MainFrame() {

        super("Analyse-Tool für archäologische Artefakte");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        initMenu();
        initPanels();
        setSize(630, 550);
    }

    private void initMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu fileMenu = new JMenu("Datei");
        JMenuItem loadFiles = new JMenuItem("Öffnen");
        JMenuItem saveFiles = new JMenuItem("Speichern");
        JMenuItem exit = new JMenuItem("Beenden");
        bar.add(fileMenu);
        fileMenu.add(loadFiles);
        fileMenu.add(saveFiles);
        fileMenu.add(exit);
        setJMenuBar(bar);
        loadFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                chooser.setFileFilter(new FileNameExtensionFilter("PNG image", "png"));
                chooser.setMultiSelectionEnabled(true);
                if(chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    File files[] = chooser.getSelectedFiles();
                    images = new ImageSeries(files);
                    imageList.setImageSeries(images);
                    revalidate();
                }
            }
        });
        saveFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                images.writeFrames(0, images.getNumFrames(), true, true, true);
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }

    private void initPanels() {
        imageList = new ImageListPanel(80, this);
        JScrollPane listPane = new JScrollPane(imageList);
        listPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(listPane, BorderLayout.WEST);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JLabel detailLabel= new JLabel("Detailansicht");
        centerPanel.add(detailLabel, BorderLayout.NORTH);
        detailPanel = new ImageDetailPanel(this);
        centerPanel.add(detailPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        seriesSlider = new JSlider();
        seriesSlider.setEnabled(false);
        seriesSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                updateDetailView();
            }
        });
        frameNumberLabel = new JLabel("Bild:");
        controlPanel.add(frameNumberLabel);
        controlPanel.add(seriesSlider);
        add(controlPanel, BorderLayout.SOUTH);
        controlPanel.add(new JLabel("Kanten anzeigen "));
        edgeCheckBox = new JCheckBox();
        edgeCheckBox.setEnabled(false);
        edgeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateDetailView();
            }
        });
        controlPanel.add(edgeCheckBox);
    }

    public void setDetailSeries(ImageSeries image) {
        seriesSlider.setEnabled(true);
        seriesSlider.setMinimum(0);
        seriesSlider.setMaximum(image.getNumFrames()-1);
        seriesSlider.setValue(0);
        frameNumberLabel.setText("Bild 1/"+(image.getNumFrames()));
        edgeCheckBox.setEnabled(true);
        setDetailFrame(image.getFrame(0), edgeCheckBox.isSelected());
    }

    public void setDetailFrame(Frame frame) {
        setDetailFrame(frame, edgeCheckBox.isSelected());
    }

    public void setDetailFrame(Frame frame, boolean showTracing) {
        detailPanel.setDetailFrame(frame, showTracing);
        revalidate();
        repaint();
    }

    public void setPreviousDetailFrame() {
        seriesSlider.setValue(seriesSlider.getValue()-1);
    }

    public void setNextDetailFrame() {
        seriesSlider.setValue(seriesSlider.getValue()+1);
    }

    public void updateDetailView() {
        int frameNumber = seriesSlider.getValue();
        setDetailFrame(images.getFrame(frameNumber), edgeCheckBox.isSelected());
        frameNumberLabel.setText("Bild " + (frameNumber+1) + "/"+(images.getNumFrames()));
    }

    public void addMarkedFrame(Frame frame) {
    }

    public void removeMarkedFrame(Frame frame) {
    }
}
