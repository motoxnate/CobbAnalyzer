/*
 * Copyright Nathaniel Fanning (c) 2020. All rights reserved.
 */

import javax.swing.*;
import java.awt.*;

public class ListRenderer extends JLabel implements ListCellRenderer<Datalog> {
    public ListRenderer() {
        setOpaque(true);
    }
    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Datalog log,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {

        String name = log.getName();
        String time = "";
        if(log.getTimestamp() != null) time = log.getTimestamp().toString();
        StringBuilder buff = new StringBuilder();
        buff.append("<html><table>");
        buff.append(String.format("<tr style=\"font-size:1em;\"><td align='left'>%s</td></tr>", name));
        buff.append(String.format("<tr style=\"font-size:0.92em;\"><td align='left'>%s</td></tr>", time));
        buff.append("</table></html>");

        setText(buff.toString());

        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;

            // check if this cell is selected
        } else if (isSelected) {
            background = Color.BLUE;
            foreground = Color.WHITE;

            // unselected, and not the DnD drop location
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        }

        setBackground(background);
        setForeground(foreground);

        //ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Resources/Images..."));
        //setIcon(imageIcon);

        return this;
    }
}
