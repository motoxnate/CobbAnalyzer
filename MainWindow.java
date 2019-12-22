/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.*;
import java.io.*;
import org.apache.commons.io.FilenameUtils;
import static java.nio.file.Paths.get;

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
    private JLabel NameLabel;
    private JLabel DateLabel;
    private JTextField LogNameLabel;
    private JLabel LogDateLabel;
    private JTable DataTable;
    private TableModel dataTableModel;
    private JButton UpdateNameButton;
    private JScrollPane tableScrollPane;

    JFrame nameFrame;

    private List<Datalog> datalogs;
    private final JFileChooser fileChooser = new JFileChooser();
    private Datalog currentLog = null;
//    private DefaultTableModel

    //TODO Should Change Dataog List into a Tree to support folders

    MainWindow() {
        // Main Window Globals
        datalogs = new ArrayList();
        MainWindow self = this;

        // Load Existing Datalogs
        File logFolder = new File(CONSTANTS.datalogPath);
        File[] prelimList = logFolder.listFiles();
        if (prelimList != null) {
            for (File datalog : prelimList) {
                if (datalog.getName().endsWith("dat")) {
                    datalogs.add(new Datalog(FilenameUtils.getBaseName(datalog.getName()), null));
                }
            }
        }
        updateDatalogs();
        DatalogList.setCellRenderer(new ListRenderer());

            //Setup Buttons Here
        //Dynamic List Selection Listener - Each time a new log is selected
        DatalogList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) {
                    currentLog = (Datalog)DatalogList.getSelectedValue();
                    updateInfoTab();
                    updateDataTable();
                }
            }
        });
        //New Datalog Button
        NewDatalogMain.addActionListener(e -> newDatalog());

        //New Datalog "+" Button
        NewDatalog.addActionListener(e -> newDatalog());

        //Remove Datalog "–" Button
        RemoveDatalog.addActionListener(e -> removeDatalog());
    }   //End Main Window


    private void updateInfoTab() {
        try {
            LogNameLabel.setText(currentLog.getName());
            LogDateLabel.setText(currentLog.getTimestamp().toString());
        } catch(NullPointerException E) {
            LogNameLabel.setText("None");
            LogDateLabel.setText("None");
        }
    }


    private void newDatalog() {
     /*   nameFrame = new JFrame("New Datalog");
        nameFrame.setContentPane(new NewDatalogWindow(this).panel);
        nameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nameFrame.pack();
        nameFrame.setVisible(true);*/

        int returnVal = fileChooser.showOpenDialog(MainPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            //Check file extension is CSV
            if (file.getPath().endsWith("CSV") || file.getPath().endsWith("csv")) {
                String basename = FilenameUtils.getBaseName(file.getPath());
                String test_name = basename;
                if(unavailableNameCheck(basename)) {
                    int i = 1;
                    while(unavailableNameCheck(test_name)) {
                        test_name = basename + i;
                    }
                }
                Datalog log = new Datalog(test_name, get(file.getPath()));
                addDatalog(log);
            } else {
                System.err.println("File is not a CSV!");
            }
        }
    }


    private boolean unavailableNameCheck(String name) {
        boolean available = false;
        if (!name.equals("")) {
            available = true;
            for (Datalog test : datalogs) {
                if (name.equals(test.getName())) {
                    available = false;
                    break;
                }
            }
            //If the name is available, create new datalog
            if (available) {
                System.out.println("Name is Available");
            }
        }
        return !available;
    }


    protected void newDatalogName(MainWindow self, File data) {
            //Make new ask for datalog name window, which then adds it.
            nameFrame = new JFrame("New Datalog");
            nameFrame.setContentPane(new NewDatalogWindow(self).panel);
            nameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nameFrame.pack();
            nameFrame.setVisible(true);
    }


    void addDatalog(Datalog log) {
        datalogs.add(log);
        updateDatalogs();
    }


    private void removeDatalog() {
        //TODO Add Error Checking
        //TODO Add option to keep or remove the datafile
        try {
            int index = DatalogList.getSelectedIndex();
            datalogs.remove(index);
            updateDatalogs();
        } catch(Exception E) {
            System.err.println("No Datalog to Remove!");
        }
    }

    private void importData(File file) {
        try {
            Datalog log = (Datalog) DatalogList.getSelectedValue();
            boolean error = log.importCSV(file.getPath());
            if(error) System.err.println("Error importing data");
            else currentLog.loadData();
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
    List<Datalog> getDatalogs() {
        List<Datalog> logs = datalogs;
        return logs;
    }

    //Update Data in Table
    void updateDataTable() {
        Object[][][] tempData = currentLog.loadData();
        Object[] columnNames = tempData[0][0];
        Object[][] data = tempData[1];
        DefaultTableModel dataTableModel = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        DataTable.setModel(dataTableModel);
    }

    static void CreateApplicationSupportFolder() {
        File ApplicationSupportFolder = new File(CONSTANTS.ApplicationSupportPath);
        if(!ApplicationSupportFolder.exists()) {
            boolean status = ApplicationSupportFolder.mkdir();
            if(!status) System.err.println("Application Support folder could not be created");
        }
    }

}
