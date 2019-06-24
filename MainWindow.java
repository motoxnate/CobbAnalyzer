/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MainWindow {
    public  JPanel MainPanel;
    private JTabbedPane Tabs;
    private JList DatalogList;
    private JButton newFolderButton;
    private JComboBox SortingMethod;
    private JButton NewDatalog;
    private JButton RemoveDatalog;
    private JPanel info;
    private JPanel raw_data;
    private JPanel graph;
    private JPanel analysis;
    private JToolBar MainToolbar;
    private JButton HideSidebar;
    private JButton Settings;
    private JButton NewDatalogMain;
    private JButton AnalysisSettings;
    private JButton GraphSettings;
    private JSplitPane SplitPane;
    private JPanel SidebarPanel;
    private JScrollPane SidebarScrollPane;
    private JToolBar SidebarToolbar;

    protected JFrame nameFrame;

    private List datalogs;

    public MainWindow() {
        // Main Window Globals
        datalogs = new ArrayList();
        MainWindow self = this;

        //Setup Buttons

        // TODO: Add all datalogs from libray before loading.
        datalogs.add(new Datalog("Test1"));
        DatalogList.setListData(datalogs.toArray());
        DatalogList.setCellRenderer(new ListRenderer());

        //New Datalog Button
        NewDatalogMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newDatalogAction(self);
            }

        });

        //New Datalog "+" Button
        NewDatalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newDatalogAction(self);
            }
        });

        //Remove Datalog "–" Button
        RemoveDatalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeDatalog();
            }
        });

    }

    protected void newDatalogAction(MainWindow self) {
            //Make new ask for datalog name window, which then adds it.
            nameFrame = new JFrame("Datalog Name");
            nameFrame.setContentPane(new DatalogName(self).panel);
            nameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nameFrame.pack();
            nameFrame.setVisible(true);
    }

    protected void addDatalog(Datalog log) {
        datalogs.add(log);
        updateDatalogs();
    }

    protected void removeDatalog() {
    //TODO Add Error Checking
        try {
            int index = DatalogList.getSelectedIndex();
            datalogs.remove(index);
            updateDatalogs();
        } catch(Exception E) {
            System.err.println("No Datalog to Remove!");
        }
    }

    private void updateDatalogs() {
        DatalogList.setListData(datalogs.toArray());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}
