/*
 * Copyright Nathaniel Fanning (c) 2020. All rights reserved.
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

/**
 * @author Nathaniel Fanning
 * @version 0.1
 * @since 0.1
 */

/**
 * Class MainWindow: The main window of the application
 */
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

    /**
     * Constructor: Initialize the MainWindow
     */
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

    /**
     * Update labels on the Info Tab
     */
    private void updateInfoTab() {
        try {
            LogNameLabel.setText(currentLog.getName());
            LogDateLabel.setText(currentLog.getTimestamp().toString());
        } catch(NullPointerException E) {
            LogNameLabel.setText("None");
            LogDateLabel.setText("None");
        }
    }

    /**
     * Create a new Datalog object in the application.
     * <p>
     *     Opens a file chooser to select the CSV file of the log. Copies the file to the data directory of the application.
     *     Adds a new Datalog object and links it to the copied file.
     * </p>
     */
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
                if(!availableName(basename)) {  //If the name is not available, add a number to it.
                    int i = 1;
                    while(!availableName(test_name)) {
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

    /**
     * Checks if a name is available for a new Datalog object
     * @param name: The name to check for availability.
     * @return bool: True if the name is available, false otherwise.
     */
    private boolean availableName(String name) {
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
        return available;
    }


    protected void newDatalogName(MainWindow self, File data) {
            //Make new ask for datalog name window, which then adds it.
            nameFrame = new JFrame("New Datalog");
            nameFrame.setContentPane(new NewDatalogWindow(self).panel);
            nameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nameFrame.pack();
            nameFrame.setVisible(true);
    }

    /**
     * Remove a datalog from the index. Refers to the selected log at the time
     */
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

    /** Update the datalogs in the visual list. */
    private void updateDatalogs() {
        DatalogList.setListData(datalogs.toArray());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /* Interaction Functions: Allow access to the MainWindow */

    /**
     * Add a datalog to the index of logs.
     * @param log: The datalog object
     */
    void addDatalog(Datalog log) {
        datalogs.add(log);
        updateDatalogs();
    }

    /**
     * Returns a List of the datalogs
     * @return List<Datalog> logs
     */
    List<Datalog> getDatalogs() {
        List<Datalog> logs = datalogs;
        return logs;
    }

    /** Update the visual data table. */
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

    /** On macOS, create the application folder for storing datalogs. */
    static void CreateApplicationSupportFolder() {
        File ApplicationSupportFolder = new File(CONSTANTS.ApplicationSupportPath);
        if(!ApplicationSupportFolder.exists()) {
            boolean status = ApplicationSupportFolder.mkdir();
            if(!status) System.err.println("Application Support folder could not be created");
        }
    }
}
