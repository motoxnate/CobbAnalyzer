/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DataTableModel extends AbstractTableModel {
    private List<String[]> data;

    public DataTableModel() {

    }

    public int getRowCount() {
        return 0;
    }
    public int getColumnCount() {
        return 0;
    }
    public Object getValueAt(int row, int col) {
        return null;
    }
}
