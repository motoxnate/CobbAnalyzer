/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class DatalogName {
    public JPanel panel;
    private JTextField datalogName;
    private JLabel TextLabel;
    private JButton cancelButton;
    private JButton acceptButton;
    private MainWindow mainWindow;


    public DatalogName(MainWindow m) {
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
                    } if(availableName) {
                        Datalog log = new Datalog(name);
                        mainWindow.addDatalog(log);
                        System.out.println("Added " + log);
                        mainWindow.nameFrame.dispose();
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
