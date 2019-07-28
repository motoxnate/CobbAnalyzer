/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.io.*;

public class NewDatalogWindow {
    public JPanel panel;
    private JTextField datalogName;
    private JLabel TextLabel;
    private JButton cancelButton;
    private JButton acceptButton;
    private JLabel name_available_label;
    private JLabel name_available;
    private MainWindow mainWindow;

    private final JFileChooser fileChooser = new JFileChooser();

    //TODO check once a second and let the user know if the datalog name is available.

    public NewDatalogWindow(MainWindow m) {
        mainWindow = m;
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Close window and don't create a datalog
                exit();
            }
        });
        //Accept Button
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = datalogName.getText();
                if(!name.equals("")) {
                    boolean availableName = true;
                    List<Datalog> datalogs = mainWindow.getDatalogs();
                    for(Datalog test : datalogs) {
                        if(name.equals(test.getName())) availableName = false;
                        System.out.println(test);
                    } if(availableName) {               //If the name is available
                        //Create new datalog
                        System.out.println("Name is Available");
                        Datalog log = new Datalog(name);

                        //TODO check if file exists, if so link it. If not, ask to import data.
                        String dataPath = CONSTANTS.datalogPath + name + ".dat";
                        File dataFile = new File(dataPath);

                        if(dataFile.exists()) {         //If data exists
                            System.out.println("File already exists");
                            log.getAttributes();
                        } else {                        //If data does not exist
                            System.out.println("File DNE");
                            int returnVal = fileChooser.showOpenDialog(mainWindow.MainPanel);
                            if (returnVal == JFileChooser.APPROVE_OPTION) {
                                File file = fileChooser.getSelectedFile();
                                //Check file extension is CSV
                                if (file.getPath().endsWith("CSV") || file.getPath().endsWith("csv")) {
                                    log.importCSV(file.getPath());
                                } else {
                                    System.err.println("File is not a CSV!");
                                }
                            }
                        }
                        mainWindow.nameFrame.dispose();

                        mainWindow.addDatalog(log);
                        System.out.println("Added " + log);
                    } else {                            //If the name is taken
                        // Show Label, name is taken, do not overwrite.
                        name_available.setText("Name Taken");
                    }
                }
            }
        });
    }

    public void exit() {
//        main.nameFrame.setVisible(false);
        mainWindow.nameFrame.dispose();
    }

}
