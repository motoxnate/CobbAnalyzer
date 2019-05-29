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

    private ArrayList datalogs;

    public MainWindow() {
        NewDatalogMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Prompt for user input
                String name = JOptionPane.showInputDialog("New Datalog Name");

                //Add the datalog
                if(name != null) {
                    System.out.println("Hi");
                    datalogs.add(new Datalog(name));
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}
