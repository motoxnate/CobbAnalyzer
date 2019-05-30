/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */
import javax.swing.*;
import java.awt.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
//        System.out.println(getX(1));

        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setMinimumSize(new Dimension(580, 200));
//        frame.setMaximumSize(new Dimension(getX(1), getY(1)));
//        frame.setPreferredSize(new Dimension(getX(2/3), getY(2/3)));
        frame.pack();
        frame.setVisible(true);
    }

    private static int getX(int mult) {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screensize.getWidth() * mult;
    }

    private static int getY(int mult) {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screensize.getHeight() * mult;
    }
}

