/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class NewDatalogName {
    public JPanel panel;
    private JTextField datalogName;
    private JLabel TextLabel;
    private JButton cancelButton;
    private JButton acceptButton;
    private JLabel name_available_label;
    private JLabel name_available;
    private MainWindow mainWindow;

    //TODO check once a second and let the user know if the datalog name is available.

    public NewDatalogName(MainWindow m) {
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
                        Datalog log = new Datalog(name);
                        mainWindow.addDatalog(log);
                        System.out.println("Added " + log);
                        //TODO check if file exists, if so link it. If not, ask to import data.

                        mainWindow.nameFrame.dispose();
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
