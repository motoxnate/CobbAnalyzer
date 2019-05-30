/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatalogName {
    public JPanel panel;
    private JTextField datalogName;
    private JLabel TextLabel;
    private JButton cancelButton;
    private JButton acceptButton;
    private MainWindow main;


    public DatalogName(MainWindow m) {
        main = m;
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
                    Datalog log = new Datalog(name);
                    System.out.println(name);
                    main.addDatalog(log);
                    System.out.println("Added " + log);
                    main.nameFrame.dispose();
                }
            }
        });
    }

    public void exit() {
//        main.nameFrame.setVisible(false);
        main.nameFrame.dispose();
    }

}
