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
    private JList displayedList;
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
        datalogs = new ArrayList();
        MainWindow self = this;

        // TODO: Add all datalogs from libray before loading.
        datalogs.add(new Datalog("Test1"));
        displayedList.setListData(datalogs.toArray());
        displayedList.setCellRenderer(new ListRenderer());
        NewDatalogMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Make new ask for datalog name window, which then adds it.
                nameFrame = new JFrame("Datalog Name");
                nameFrame.setContentPane(new DatalogName(self).panel);
                nameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nameFrame.pack();
                nameFrame.setVisible(true);
            }
        });
    }

    protected void addDatalog(Datalog log) {
        datalogs.add(log);
        displayedList.setListData(datalogs.toArray());
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}
