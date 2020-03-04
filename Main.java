/*
 * Copyright Nathaniel Fanning (c) 2020. All rights reserved.
 */
import javax.swing.*;
import java.awt.*;
import java.lang.*;

/**
 * @author Nathaniel Fanning | motoxnate788@gmail.com
 * @version 0.1
 * @since 0.1
 */

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
    /**
     * Get the X dimension of the display
     */
    private static int getScreenX(int mult) {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screensize.getWidth() * mult;
    }
    /**
     * Get they Y dimension of the display
     */
    private static int getScreenY(int mult) {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) screensize.getHeight() * mult;
    }
}

