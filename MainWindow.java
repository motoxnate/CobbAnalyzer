/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

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
    private JButton AddData;
    private JLabel NameLabel;
    private JLabel DateLabel;
    private JTextField LogNameLabel;
    private JLabel LogDateLabel;
    private JTable DataTable;
    private JButton UpdateNameButton;

    protected JFrame nameFrame;

    private List datalogs;
    private final JFileChooser fileChooser = new JFileChooser();
    private Datalog currentLog = null;

    //TODO Should Change Dataog List into a Tree to support folders

    public MainWindow() {
        // Main Window Globals
        datalogs = new ArrayList();
        MainWindow self = this;

        // Load Datalogs
        // TODO: Add all datalogs from libray before loading.
        datalogs.add(new Datalog("Test1"));



        updateDatalogs();
        DatalogList.setCellRenderer(new ListRenderer());

        //Setup Buttons

        //Dynamic List Selection Listener
        DatalogList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) {
                    currentLog = (Datalog)DatalogList.getSelectedValue();
                    updateInfoTab();
                }
            }
        });
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

        //Import CSV Button
        AddData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == AddData) {
                    int returnVal = fileChooser.showOpenDialog(MainPanel);

                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        importData(file);
                    }
                }
            }
        });

    }

    protected void updateInfoTab() {
        try {
            LogNameLabel.setText(currentLog.getName());
            LogDateLabel.setText(currentLog.getTimestamp().toString());
        } catch(NullPointerException E) {
            LogNameLabel.setText("None");
            LogDateLabel.setText("None");
        }
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

    protected void importData(File file) {
        try {
            Datalog log = (Datalog) DatalogList.getSelectedValue();
            boolean error = log.importCSV(file.getPath());
            if(error) System.err.println("Error importing data");
        } catch(Exception E) {
            System.err.println("Error importing data\n" + E);
        }
        updateInfoTab();
    }

    private void updateDatalogs() {
        DatalogList.setListData(datalogs.toArray());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    //Interaction Commands
    public List<Datalog> getDatalogs() {
        List<Datalog> logs = datalogs;
        return logs;
    }
}
